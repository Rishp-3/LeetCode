#!/usr/bin/env python3
"""
Sync the latest accepted Java submission for every problem in the authenticated
LeetCode account into Easy/, Medium/, and Hard/.

Important:
- Solution.java is written verbatim from LeetCode's submissionDetails.code.
- Main.java is never opened for writing, created, or deleted.
- A problem folder stores one canonical solution: the newest accepted Java
  submission for that problem. This is not an archive of every resubmission.
"""
from __future__ import annotations

import html
import os
import re
import sys
import time
from pathlib import Path
from typing import Any

import requests

ROOT = Path(__file__).resolve().parents[1]
GRAPHQL_URL = "https://leetcode.com/graphql/"
PAGE_SIZE = 20
REQUEST_DELAY_SECONDS = 0.25
DIFFICULTY_DIR = {"Easy": "Easy", "Medium": "Medium", "Hard": "Hard"}

SUBMISSION_LIST_QUERY = """
query submissionList($offset: Int!, $limit: Int!, $lastKey: String, $questionSlug: String) {
  submissionList(offset: $offset, limit: $limit, lastKey: $lastKey, questionSlug: $questionSlug) {
    lastKey
    hasNext
    submissions {
      id
      title
      titleSlug
      timestamp
      statusDisplay
      lang
      url
    }
  }
}
"""

SUBMISSION_DETAILS_QUERY = """
query submissionDetails($submissionId: Int!) {
  submissionDetails(submissionId: $submissionId) {
    code
    lang
    question {
      questionId
      title
      titleSlug
      difficulty
    }
  }
}
"""

QUESTION_DATA_QUERY = """
query questionData($titleSlug: String!) {
  questionData(titleSlug: $titleSlug) {
    questionId
    questionFrontendId
    title
    titleSlug
    difficulty
    content
    exampleTestcases
    topicTags { name }
  }
}
"""


def make_session() -> requests.Session:
    session_cookie = os.environ.get("LEETCODE_SESSION", "").strip()
    csrf_token = os.environ.get("LEETCODE_CSRF_TOKEN", "").strip()
    if not session_cookie:
        raise RuntimeError(
            "Missing LEETCODE_SESSION secret. Add it under repository Settings "
            "→ Secrets and variables → Actions."
        )

    session = requests.Session()
    session.headers.update({
        "Content-Type": "application/json",
        "Referer": "https://leetcode.com/",
        "Origin": "https://leetcode.com",
        "User-Agent": (
            "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 "
            "(KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36"
        ),
    })
    session.cookies.set("LEETCODE_SESSION", session_cookie, domain=".leetcode.com")
    if csrf_token:
        session.cookies.set("csrftoken", csrf_token, domain=".leetcode.com")
        session.headers["x-csrftoken"] = csrf_token
    return session


def graphql(session: requests.Session, query: str, variables: dict[str, Any]) -> dict[str, Any]:
    last_error: Exception | None = None
    for attempt in range(5):
        try:
            response = session.post(
                GRAPHQL_URL,
                json={"query": query, "variables": variables},
                timeout=45,
            )
            if response.status_code in (429, 500, 502, 503, 504):
                time.sleep(min(2 ** attempt, 20))
                continue
            if response.status_code in (401, 403):
                raise RuntimeError(
                    f"LeetCode returned HTTP {response.status_code}. "
                    "The session may be expired, invalid, or blocked."
                )
            response.raise_for_status()
            payload = response.json()
            if payload.get("errors"):
                messages = "; ".join(
                    str(error.get("message", "GraphQL error"))
                    for error in payload["errors"]
                )
                raise RuntimeError(f"LeetCode GraphQL error: {messages}")
            if "data" not in payload or payload["data"] is None:
                raise RuntimeError(f"Unexpected LeetCode response: {payload}")
            return payload["data"]
        except (requests.RequestException, ValueError) as exc:
            last_error = exc
            time.sleep(min(2 ** attempt, 20))
    raise RuntimeError(f"LeetCode request failed after retries: {last_error}")


def fetch_all_submissions(session: requests.Session) -> list[dict[str, Any]]:
    """Paginate the signed-in user's submission history."""
    submissions: list[dict[str, Any]] = []
    offset = 0
    last_key = ""
    seen_page_markers: set[tuple[int, str]] = set()

    while True:
        marker = (offset, last_key)
        if marker in seen_page_markers:
            raise RuntimeError("LeetCode pagination repeated a page; stopping safely.")
        seen_page_markers.add(marker)

        data = graphql(session, SUBMISSION_LIST_QUERY, {
            "offset": offset,
            "limit": PAGE_SIZE,
            "lastKey": last_key or None,
            "questionSlug": "",
        })
        page = data.get("submissionList")
        if not page:
            raise RuntimeError(
                "LeetCode returned no submissionList. Check the GraphQL schema, "
                "session cookie, and account login."
            )

        rows = page.get("submissions") or []
        submissions.extend(rows)
        print(f"Fetched {len(submissions)} submissions so far...")

        if not page.get("hasNext") or not rows:
            break

        new_last_key = page.get("lastKey") or ""
        offset += len(rows)
        # Some LeetCode deployments use offset pagination; others also return a
        # cursor. Keep the cursor when available, while advancing the offset.
        last_key = new_last_key
        time.sleep(REQUEST_DELAY_SECONDS)

    return submissions


def safe_folder_title(title: str) -> str:
    title = re.sub(r"[^\w-]+", "", title.replace(" ", ""))
    return title or "Problem"


def get_question(session: requests.Session, slug: str) -> dict[str, Any]:
    data = graphql(session, QUESTION_DATA_QUERY, {"titleSlug": slug})
    question = data.get("questionData")
    if not question:
        raise RuntimeError(f"Could not load problem metadata for slug {slug!r}.")
    return question


def get_submission_code(session: requests.Session, submission_id: str) -> dict[str, Any]:
    data = graphql(session, SUBMISSION_DETAILS_QUERY, {"submissionId": int(submission_id)})
    details = data.get("submissionDetails")
    if not details or details.get("code") is None:
        raise RuntimeError(
            f"LeetCode did not return source code for submission {submission_id}. "
            "The session may not have access to this submission."
        )
    return details


def html_to_readable_text(markup: str) -> str:
    """Make a readable Markdown fallback from LeetCode's HTML statement."""
    text_value = re.sub(r"(?i)<br\s*/?>", "\n", markup or "")
    text_value = re.sub(r"(?i)</(p|div|h[1-6]|li|pre|blockquote)>", "\n\n", text_value)
    text_value = re.sub(r"(?i)<li[^>]*>", "- ", text_value)
    text_value = re.sub(r"<[^>]+>", "", text_value)
    text_value = html.unescape(text_value)
    text_value = re.sub(r"\n[ \t]+\n", "\n\n", text_value)
    text_value = re.sub(r"\n{3,}", "\n\n", text_value)
    return text_value.strip()


def markdown_escape_cell(value: str) -> str:
    return str(value or "").replace("|", r"\|").replace("\n", " ")


def build_readme(question: dict[str, Any], submission: dict[str, Any]) -> str:
    title = question.get("title") or submission.get("title") or "LeetCode Problem"
    frontend_id = str(question.get("questionFrontendId") or "").strip()
    difficulty = question.get("difficulty") or "Unknown"
    slug = question.get("titleSlug") or submission.get("titleSlug") or ""
    problem_url = f"https://leetcode.com/problems/{slug}/" if slug else "https://leetcode.com/"
    content = html_to_readable_text(question.get("content") or "")
    tags = [tag.get("name", "") for tag in (question.get("topicTags") or []) if tag.get("name")]
    sample = (question.get("exampleTestcases") or "").strip()
    submission_url = submission.get("url") or f"https://leetcode.com/submissions/detail/{submission.get('id')}/"

    parts = [
        f"# {frontend_id + '. ' if frontend_id else ''}{title}",
        "",
        f"- **Difficulty:** {difficulty}",
        f"- **Problem:** [{problem_url}]({problem_url})",
        f"- **Accepted submission:** [{submission_url}]({submission_url})",
    ]
    if tags:
        parts.extend(["- **Topics:** " + ", ".join(tags)])
    parts.extend(["", "## Problem Description", "", content or "See the problem statement on LeetCode.", ""])
    if sample:
        parts.extend([
            "## Example Test Data",
            "",
            "LeetCode's example test data (as returned by the public problem metadata query):",
            "",
            "```text",
            sample,
            "```",
            "",
        ])
    parts.extend([
        "## Solution",
        "",
        "The Java source in [`Solution.java`](./Solution.java) is copied verbatim from the accepted submission linked above.",
        "",
        "## Complexity",
        "",
        "Complexity is intentionally not inferred automatically. Add time and space complexity here after verifying the submitted implementation.",
        "",
    ])
    return "\n".join(parts)


def main() -> int:
    username = os.environ.get("LEETCODE_USERNAME", "rishp_3")
    print(f"Starting authenticated LeetCode sync for {username}...")
    session = make_session()
    all_submissions = fetch_all_submissions(session)
    print(f"Total submissions visible to this account: {len(all_submissions)}")

    # Keep accepted Java submissions only; for each problem, select the newest
    # accepted Java submission. Sorting is explicit because API ordering can vary.
    accepted_java: dict[str, dict[str, Any]] = {}
    for row in all_submissions:
        if str(row.get("statusDisplay", "")).lower() != "accepted":
            continue
        language = str(row.get("lang", "")).lower()
        if not language.startswith("java"):
            continue
        slug = row.get("titleSlug")
        if not slug:
            continue
        previous = accepted_java.get(slug)
        if previous is None or int(row.get("timestamp") or 0) > int(previous.get("timestamp") or 0):
            accepted_java[slug] = row

    if not accepted_java:
        raise RuntimeError(
            "No accepted Java submissions were found. No repository files were changed. "
            "Check that the account has Java submissions and that LeetCode returned history."
        )

    print(f"Unique problems with accepted Java submissions: {len(accepted_java)}")
    synced = 0
    skipped = 0
    failures: list[str] = []

    for index, (slug, row) in enumerate(
        sorted(accepted_java.items(), key=lambda item: int(item[1].get("timestamp") or 0))
    , start=1):
        try:
            details = get_submission_code(session, str(row["id"]))
            question = get_question(session, slug)
            difficulty = str(question.get("difficulty") or details.get("question", {}).get("difficulty") or "")
            folder = DIFFICULTY_DIR.get(difficulty)
            if not folder:
                print(f"[skip] {slug}: unknown difficulty {difficulty!r}")
                skipped += 1
                continue

            frontend_id = str(question.get("questionFrontendId") or "").strip()
            if not frontend_id.isdigit():
                print(f"[skip] {slug}: missing numeric questionFrontendId")
                skipped += 1
                continue

            title = question.get("title") or row.get("title") or slug
            problem_dir = ROOT / folder / f"{int(frontend_id):04d}-{safe_folder_title(title)}"
            problem_dir.mkdir(parents=True, exist_ok=True)

            # Do not normalize, format, or otherwise edit the source code string.
            source = details["code"]
            (problem_dir / "Solution.java").write_text(source, encoding="utf-8", newline="")
            # Intentionally never touch Main.java.
            (problem_dir / "README.md").write_text(
                build_readme(question, row), encoding="utf-8", newline=""
            )
            synced += 1
            print(f"[{index}/{len(accepted_java)}] synced {folder}/{problem_dir.name}")
            time.sleep(REQUEST_DELAY_SECONDS)
        except Exception as exc:
            message = f"{slug}: {exc}"
            failures.append(message)
            print(f"[error] {message}", file=sys.stderr)
            # Continue so one inaccessible submission does not block all others.
            time.sleep(REQUEST_DELAY_SECONDS)

    print(f"Sync complete: {synced} synced, {skipped} skipped, {len(failures)} failed.")
    if failures:
        print("Some submissions could not be synced:")
        for failure in failures:
            print(f" - {failure}")
        # Fail the action so the run is visibly marked as incomplete.
        return 1
    return 0


if __name__ == "__main__":
    try:
        raise SystemExit(main())
    except Exception as error:
        print(f"FATAL: {error}", file=sys.stderr)
        raise SystemExit(1)
