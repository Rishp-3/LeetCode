#!/usr/bin/env python3
"""
LeetCode -> GitHub sync (Java, accepted submissions only). Standard library only.

What it does
  * Pulls your full accepted-submission history from LeetCode (newest first).
  * Writes the submitted Java source byte-for-byte to <Difficulty>/<id>.<Title>/Solution.java
  * Optionally archives every distinct accepted Java submission under submissions/.
  * Creates a per-problem README.md only if one does not exist yet.
  * Creates Main.java ONLY if it does not exist (exclusive-create). An existing
    Main.java is never opened for writing.
  * Regenerates the stats/tables block of the root README.md between markers.

Environment
  LEETCODE_SESSION, LEETCODE_CSRF_TOKEN   required (browser cookies)
  FULL_SYNC=true            re-scan entire history (default: stop at last synced id)
  ARCHIVE_ALL_ACCEPTED=true archive every distinct accepted Java submission (default true)
  CREATE_MAIN_STUB=true     create a minimal Main.java when missing (default true)
  REQUEST_DELAY=1.5         seconds between paginated requests
  REPO_ROOT=.               repository root
"""
import hashlib
import json
import os
import re
import sys
import time
import urllib.error
import urllib.parse
import urllib.request
from datetime import datetime, timezone
from html.parser import HTMLParser
from pathlib import Path

BASE = "https://leetcode.com"
DIFFICULTIES = ("Easy", "Medium", "Hard")
README_START = "<!-- LEETCODE-SYNC:START -->"
README_END = "<!-- LEETCODE-SYNC:END -->"


def env_bool(name, default):
    return os.environ.get(name, str(default)).strip().lower() in ("1", "true", "yes")


ROOT = Path(os.environ.get("REPO_ROOT", ".")).resolve()
STATE_PATH = ROOT / ".leetcode-sync" / "state.json"
SESSION = os.environ.get("LEETCODE_SESSION", "").strip()
CSRF = os.environ.get("LEETCODE_CSRF_TOKEN", "").strip()
FULL_SYNC = env_bool("FULL_SYNC", False)
ARCHIVE_ALL = env_bool("ARCHIVE_ALL_ACCEPTED", True)
CREATE_MAIN_STUB = env_bool("CREATE_MAIN_STUB", True)
DELAY = float(os.environ.get("REQUEST_DELAY", "1.5"))


class AuthError(RuntimeError):
    pass


# --------------------------------------------------------------------------- HTTP
def http_json(url, payload=None, retries=6):
    headers = {
        "User-Agent": "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124 Safari/537.36",
        "Referer": BASE + "/",
        "Origin": BASE,
        "Cookie": f"LEETCODE_SESSION={SESSION}; csrftoken={CSRF}",
        "x-csrftoken": CSRF,
        "Accept": "application/json",
    }
    data = None
    if payload is not None:
        data = json.dumps(payload).encode("utf-8")
        headers["Content-Type"] = "application/json"
    for attempt in range(retries):
        try:
            req = urllib.request.Request(url, data=data, headers=headers)
            with urllib.request.urlopen(req, timeout=40) as resp:
                return json.loads(resp.read().decode("utf-8"))
        except urllib.error.HTTPError as e:
            if e.code in (401, 403):
                raise AuthError(
                    f"HTTP {e.code} from LeetCode. Your LEETCODE_SESSION / LEETCODE_CSRF_TOKEN "
                    "are probably expired or invalid (or LeetCode is blocking this runner's IP)."
                )
            if e.code in (429, 500, 502, 503, 504):
                wait = min(60, 5 * (attempt + 1))
                print(f"  HTTP {e.code}; retrying in {wait}s", file=sys.stderr)
                time.sleep(wait)
                continue
            raise
        except (urllib.error.URLError, TimeoutError, json.JSONDecodeError) as e:
            wait = min(60, 5 * (attempt + 1))
            print(f"  network/parse error ({e}); retrying in {wait}s", file=sys.stderr)
            time.sleep(wait)
    raise RuntimeError(f"Giving up on {url}")


def graphql(query, variables):
    d = http_json(f"{BASE}/graphql", {"query": query, "variables": variables})
    if d.get("errors"):
        raise RuntimeError(f"GraphQL error: {d['errors']}")
    return d["data"]


Q_QUESTION = """
query questionData($titleSlug: String!) {
  question(titleSlug: $titleSlug) {
    questionFrontendId title titleSlug difficulty content isPaidOnly
    topicTags { name }
  }
}"""
Q_SUBMISSION = """
query submissionDetails($submissionId: Int!) {
  submissionDetails(submissionId: $submissionId) { code }
}"""


# --------------------------------------------------------------- fetch submissions
def fetch_accepted_java(last_known_id):
    """Return (accepted_java_submissions_newest_first, newest_id_seen, total_seen)."""
    out, offset, last_key = [], 0, ""
    newest_id, total_seen = 0, 0
    while True:
        url = (f"{BASE}/api/submissions/?offset={offset}&limit=20"
               f"&lastkey={urllib.parse.quote(last_key)}")
        d = http_json(url)
        dump = d.get("submissions_dump") or []
        stop = False
        for s in dump:
            sid = int(s["id"])
            total_seen += 1
            newest_id = max(newest_id, sid)
            if not FULL_SYNC and sid <= last_known_id:
                stop = True
                break
            if s.get("status_display") == "Accepted" and s.get("lang") == "java":
                out.append(s)
        print(f"  page offset={offset}: {len(dump)} submissions, {len(out)} accepted Java so far")
        if stop or not d.get("has_next") or not dump:
            break
        last_key = d.get("last_key") or ""
        offset += 20
        time.sleep(DELAY)
    return out, newest_id, total_seen


def get_code(sub):
    code = sub.get("code")
    if code is None:
        d = graphql(Q_SUBMISSION, {"submissionId": int(sub["id"])})
        code = (d.get("submissionDetails") or {}).get("code")
        time.sleep(DELAY)
    if code is None:
        raise RuntimeError(f"No source code returned for submission {sub['id']}")
    return code


# ------------------------------------------------------------------ html -> markdown
class _MD(HTMLParser):
    def __init__(self):
        super().__init__(convert_charrefs=True)
        self.out, self.pre, self.depth = [], False, 0

    def handle_starttag(self, t, a):
        if self.pre:
            if t == "br":
                self.out.append("\n")
            return
        if t in ("p", "div"):
            self.out.append("\n\n")
        elif t == "br":
            self.out.append("\n")
        elif t in ("strong", "b"):
            self.out.append("**")
        elif t in ("em", "i"):
            self.out.append("*")
        elif t == "code":
            self.out.append("`")
        elif t == "pre":
            self.pre = True
            self.out.append("\n\n```text\n")
        elif t in ("ul", "ol"):
            self.depth += 1
            self.out.append("\n")
        elif t == "li":
            self.out.append("\n" + "  " * max(0, self.depth - 1) + "- ")
        elif t == "sup":
            self.out.append("^")
        elif t == "img":
            src = dict(a).get("src")
            if src:
                self.out.append(f"\n\n![]({src})\n\n")

    def handle_endtag(self, t):
        if t == "pre":
            self.pre = False
            self.out.append("\n```\n\n")
            return
        if self.pre:
            return
        if t in ("strong", "b"):
            self.out.append("**")
        elif t in ("em", "i"):
            self.out.append("*")
        elif t == "code":
            self.out.append("`")
        elif t in ("ul", "ol"):
            self.depth = max(0, self.depth - 1)
            self.out.append("\n")
        elif t in ("p", "div"):
            self.out.append("\n\n")

    def handle_data(self, d):
        d = d.replace("\xa0", " ")
        self.out.append(d if self.pre else re.sub(r"\s+", " ", d))


def html_to_md(html_text):
    if not html_text:
        return "_Description unavailable (premium problem or not returned by LeetCode)._"
    p = _MD()
    p.feed(html_text)
    text = "".join(p.out)
    text = re.sub(r"[ \t]+\n", "\n", text)
    text = re.sub(r"\n{3,}", "\n\n", text)
    return text.strip()


# ------------------------------------------------------------------------- helpers
def load_state():
    if STATE_PATH.exists():
        raw = STATE_PATH.read_text(encoding="utf-8").strip()
        if raw:
            return json.loads(raw)
    return {"last_submission_id": 0, "slugs": {}, "problems": {}}


def save_state(state):
    STATE_PATH.parent.mkdir(parents=True, exist_ok=True)
    STATE_PATH.write_text(json.dumps(state, indent=2, sort_keys=True) + "\n", encoding="utf-8")


def folder_title(title):
    return re.sub(r"[^A-Za-z0-9]", "", title.title().replace(" ", ""))


def find_folder(pid):
    for diff in DIFFICULTIES:
        base = ROOT / diff
        if base.is_dir():
            for p in sorted(base.iterdir()):
                if p.is_dir() and p.name.startswith(f"{pid}."):
                    return p
    return None


def write_bytes_if_changed(path, data):
    """Write raw bytes (no newline translation). Never used for Main.java."""
    assert path.name != "Main.java", "Main.java must never be written through this path"
    if path.exists() and path.read_bytes() == data:
        return False
    path.parent.mkdir(parents=True, exist_ok=True)
    with open(path, "wb") as f:
        f.write(data)
    return True


def create_if_missing(path, text):
    """Exclusive create: raises FileExistsError instead of ever overwriting."""
    path.parent.mkdir(parents=True, exist_ok=True)
    try:
        with open(path, "xb") as f:
            f.write(text.encode("utf-8"))
        return True
    except FileExistsError:
        return False


def ts_to_dt(ts):
    return datetime.fromtimestamp(int(ts), tz=timezone.utc)


MAIN_STUB = """\
public class Main {
    public static void main(String[] args) {
        Solution solution = new Solution();
        // TODO: add test cases here. The sync automation never modifies this file.
    }
}
"""


def problem_readme(meta):
    topics = ", ".join(meta["topics"]) or "-"
    return (
        f"# {meta['id']}. {meta['title']}\n\n"
        f"- **LeetCode:** https://leetcode.com/problems/{meta['slug']}/\n"
        f"- **Difficulty:** {meta['difficulty']}\n"
        f"- **Topics:** {topics}\n\n"
        f"## Description\n\n{meta['description']}\n"
    )


def get_meta(state, slug):
    pid = state["slugs"].get(slug)
    if pid and pid in state["problems"] and state["problems"][pid].get("description") is not None:
        return state["problems"][pid]
    q = graphql(Q_QUESTION, {"titleSlug": slug})["question"]
    time.sleep(DELAY)
    meta = {
        "id": int(q["questionFrontendId"]),
        "title": q["title"],
        "slug": q["titleSlug"],
        "difficulty": q["difficulty"],
        "topics": [t["name"] for t in q.get("topicTags") or []],
        "description": html_to_md(q.get("content")),
    }
    old = state["problems"].get(str(meta["id"]), {})
    meta["first_solved"] = old.get("first_solved")
    state["slugs"][slug] = str(meta["id"])
    state["problems"][str(meta["id"])] = meta
    return meta


# ------------------------------------------------------------------ root README
def collect_entries(state):
    entries = []
    for diff in DIFFICULTIES:
        base = ROOT / diff
        if not base.is_dir():
            continue
        for p in sorted(base.iterdir()):
            m = re.match(r"^(\d+)\.(.+)$", p.name)
            if not (p.is_dir() and m):
                continue
            pid = int(m.group(1))
            meta = state["problems"].get(str(pid), {})
            title = meta.get("title") or re.sub(r"(?<=[a-z0-9])(?=[A-Z])", " ", m.group(2))
            entries.append({
                "id": pid, "title": title, "difficulty": diff, "dir": p,
                "topics": meta.get("topics", []),
                "date": meta.get("first_solved") or "",
                "has_solution": (p / "Solution.java").exists(),
            })
    return sorted(entries, key=lambda e: e["id"])


def rel(path):
    return urllib.parse.quote(path.relative_to(ROOT).as_posix())


def build_readme_block(state):
    entries = collect_entries(state)
    counts = {d: sum(1 for e in entries if e["difficulty"] == d) for d in DIFFICULTIES}
    lines = [
        README_START,
        "## Progress",
        "",
        "| Easy | Medium | Hard | Total |",
        "| :---: | :---: | :---: | :---: |",
        f"| {counts['Easy']} | {counts['Medium']} | {counts['Hard']} | {len(entries)} |",
        "",
        "_Auto-generated by `scripts/leetcode_sync.py` via GitHub Actions. "
        "Edit outside the markers; this block is overwritten on every sync._",
        "",
    ]
    for diff in DIFFICULTIES:
        group = [e for e in entries if e["difficulty"] == diff]
        if not group:
            continue
        lines += [f"## {diff} ({len(group)})", "",
                  "| # | Title | Topics | Solution | First solved |",
                  "| ---: | --- | --- | :---: | :---: |"]
        for e in group:
            sol = f"[Java]({rel(e['dir'] / 'Solution.java')})" if e["has_solution"] else "-"
            lines.append(
                f"| {e['id']} | [{e['title']}]({rel(e['dir'])}) | "
                f"{', '.join(e['topics']) or '-'} | {sol} | {e['date'] or '-'} |"
            )
        lines.append("")
    lines.append(README_END)
    return "\n".join(lines)


def update_root_readme(state):
    block = build_readme_block(state)
    path = ROOT / "README.md"
    if not path.exists():
        content = "# LeetCode Solutions\n\nMy accepted LeetCode solutions in Java.\n\n" + block + "\n"
    else:
        content = path.read_text(encoding="utf-8")
        if README_START in content and README_END in content:
            pre = content.split(README_START)[0]
            post = content.split(README_END, 1)[1]
            content = pre + block + post
        else:
            content = content.rstrip("\n") + "\n\n" + block + "\n"
    if not path.exists() or path.read_text(encoding="utf-8") != content:
        path.write_text(content, encoding="utf-8", newline="\n")
        return True
    return False


# -------------------------------------------------------------------------- main
def main():
    if not SESSION or not CSRF:
        sys.exit("ERROR: set LEETCODE_SESSION and LEETCODE_CSRF_TOKEN (see setup instructions).")

    state = load_state()
    last_id = 0 if FULL_SYNC else int(state.get("last_submission_id", 0))
    print(f"Mode: {'FULL' if FULL_SYNC or not last_id else 'incremental'} (last synced id: {last_id})")

    try:
        subs, newest_id, total_seen = fetch_accepted_java(last_id)
    except AuthError as e:
        sys.exit(f"ERROR: {e}")

    if total_seen == 0 and not state["problems"]:
        sys.exit("ERROR: LeetCode returned zero submissions on a first run; check your cookies.")

    by_slug = {}
    for s in subs:  # newest first
        by_slug.setdefault(s["title_slug"], []).append(s)

    changed = {"solutions": 0, "archived": 0, "readmes": 0, "mains": 0}
    for slug, group in by_slug.items():
        meta = get_meta(state, slug)
        folder = find_folder(meta["id"]) or (
            ROOT / meta["difficulty"] / f"{meta['id']}.{folder_title(meta['title'])}")
        folder.mkdir(parents=True, exist_ok=True)

        # Latest accepted Java submission -> Solution.java, byte-exact.
        latest_code = get_code(group[0])
        if write_bytes_if_changed(folder / "Solution.java", latest_code.encode("utf-8")):
            changed["solutions"] += 1

        # Every distinct accepted Java submission -> submissions/
        if ARCHIVE_ALL:
            seen = set()
            for s in group:
                code = get_code(s)
                digest = hashlib.sha256(code.encode("utf-8")).hexdigest()
                if digest in seen:
                    continue
                seen.add(digest)
                stamp = ts_to_dt(s["timestamp"]).strftime("%Y%m%d-%H%M%S")
                if write_bytes_if_changed(folder / "submissions" / f"{stamp}_{s['id']}.java",
                                          code.encode("utf-8")):
                    changed["archived"] += 1

        if create_if_missing(folder / "README.md", problem_readme(meta)):
            changed["readmes"] += 1
        if CREATE_MAIN_STUB and create_if_missing(folder / "Main.java", MAIN_STUB):
            changed["mains"] += 1

        earliest = ts_to_dt(min(int(s["timestamp"]) for s in group)).strftime("%Y-%m-%d")
        prev = meta.get("first_solved")
        meta["first_solved"] = min(prev, earliest) if prev else earliest
        state["problems"][str(meta["id"])] = meta
        print(f"  synced {meta['id']}. {meta['title']} ({meta['difficulty']})")

    if newest_id:
        state["last_submission_id"] = max(int(state.get("last_submission_id", 0)), newest_id)
    save_state(state)
    readme_changed = update_root_readme(state)

    print(f"Done: {changed}, root README updated: {readme_changed}")


if __name__ == "__main__":
    main()
