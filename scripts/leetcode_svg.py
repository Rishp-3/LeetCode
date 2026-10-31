"""Generate an animated LeetCode progress card (pure SVG + SMIL, works in GitHub READMEs).

Usage inside scripts/leetcode_sync.py:
    from leetcode_svg import write_svg
    write_svg(easy=3, medium=0, hard=0, path="assets/leetcode-progress.svg")
"""
from pathlib import Path

# Goals decide how full each bar gets. Change them freely.
GOALS = {"Easy": 50, "Medium": 100, "Hard": 30}
COLORS = {"Easy": "#00b8a3", "Medium": "#ffc01e", "Hard": "#ff375f"}

BAR_X, BAR_W, BAR_H = 250, 470, 14
RING_R = 52
RING_C = 2 * 3.14159265 * RING_R


def build_svg(easy: int, medium: int, hard: int) -> str:
    counts = {"Easy": easy, "Medium": medium, "Hard": hard}
    total = sum(counts.values())
    goal_total = sum(GOALS.values())
    ring_off = RING_C * (1 - min(total / goal_total, 1))

    rows = []
    for i, name in enumerate(["Easy", "Medium", "Hard"]):
        y = 78 + i * 52
        c, g, col = counts[name], GOALS[name], COLORS[name]
        w = round(BAR_W * min(c / g, 1), 1)
        begin = 0.3 + i * 0.25
        rows.append(f"""
  <g opacity="0">
    <animate attributeName="opacity" from="0" to="1" dur="0.5s" begin="{begin}s" fill="freeze"/>
    <circle cx="{BAR_X - 14}" cy="{y - 5}" r="4" fill="{col}">
      <animate attributeName="r" values="3;5.5;3" dur="2s" repeatCount="indefinite"/>
    </circle>
    <text x="{BAR_X}" y="{y - 14}" class="lbl">{name}</text>
    <text x="{BAR_X + BAR_W}" y="{y - 14}" class="cnt" text-anchor="end">{c} / {g}</text>
    <rect x="{BAR_X}" y="{y - 6}" width="{BAR_W}" height="{BAR_H}" rx="7" fill="#21262d"/>
    <rect x="{BAR_X}" y="{y - 6}" width="0" height="{BAR_H}" rx="7" fill="{col}" filter="url(#glow)">
      <animate attributeName="width" from="0" to="{w}" dur="1.4s" begin="{begin}s" fill="freeze"
               calcMode="spline" keySplines="0.2 0.8 0.2 1" keyTimes="0;1"/>
    </rect>
  </g>""")

    return f"""<svg xmlns="http://www.w3.org/2000/svg" width="780" height="250" viewBox="0 0 780 250" role="img"
     aria-label="LeetCode progress: {total} solved">
  <defs>
    <linearGradient id="ring" x1="0" y1="0" x2="1" y2="1">
      <stop offset="0" stop-color="#00b8a3"/><stop offset="0.5" stop-color="#ffc01e"/><stop offset="1" stop-color="#ff375f"/>
    </linearGradient>
    <linearGradient id="bg" x1="0" y1="0" x2="1" y2="1">
      <stop offset="0" stop-color="#0d1117"/><stop offset="1" stop-color="#161b22"/>
    </linearGradient>
    <filter id="glow" x="-20%" y="-50%" width="140%" height="200%">
      <feGaussianBlur stdDeviation="2.2" result="b"/>
      <feMerge><feMergeNode in="b"/><feMergeNode in="SourceGraphic"/></feMerge>
    </filter>
    <style>
      text {{ font-family: 'Segoe UI', Ubuntu, 'Helvetica Neue', Arial, sans-serif; }}
      .title {{ font-size: 18px; font-weight: 700; fill: #e6edf3; }}
      .lbl   {{ font-size: 14px; font-weight: 600; fill: #c9d1d9; }}
      .cnt   {{ font-size: 13px; fill: #8b949e; }}
      .big   {{ font-size: 34px; font-weight: 800; fill: #e6edf3; }}
      .sub   {{ font-size: 12px; fill: #8b949e; letter-spacing: 1.5px; }}
    </style>
  </defs>

  <rect x="1" y="1" width="778" height="248" rx="16" fill="url(#bg)" stroke="#30363d"/>

  <!-- floating background dots -->
  <circle cx="700" cy="40" r="2" fill="#00b8a3" opacity="0.5">
    <animate attributeName="cy" values="40;28;40" dur="5s" repeatCount="indefinite"/>
  </circle>
  <circle cx="740" cy="210" r="2.5" fill="#ffc01e" opacity="0.4">
    <animate attributeName="cy" values="210;198;210" dur="6s" repeatCount="indefinite"/>
  </circle>
  <circle cx="30" cy="30" r="2" fill="#ff375f" opacity="0.4">
    <animate attributeName="cy" values="30;42;30" dur="4.5s" repeatCount="indefinite"/>
  </circle>

  <text x="28" y="40" class="title">LeetCode Progress</text>

  <!-- total ring -->
  <g transform="translate(115 142)">
    <circle r="{RING_R}" fill="none" stroke="#21262d" stroke-width="12"/>
    <circle r="{RING_R}" fill="none" stroke="url(#ring)" stroke-width="12" stroke-linecap="round"
            stroke-dasharray="{RING_C:.2f}" stroke-dashoffset="{RING_C:.2f}" transform="rotate(-90)" filter="url(#glow)">
      <animate attributeName="stroke-dashoffset" from="{RING_C:.2f}" to="{ring_off:.2f}" dur="1.6s" fill="freeze"
               calcMode="spline" keySplines="0.2 0.8 0.2 1" keyTimes="0;1"/>
    </circle>
    <text class="big" text-anchor="middle" y="8" opacity="0">{total}
      <animate attributeName="opacity" from="0" to="1" dur="0.8s" begin="0.6s" fill="freeze"/>
    </text>
    <text class="sub" text-anchor="middle" y="30">SOLVED</text>
  </g>
{''.join(rows)}
</svg>
"""


def write_svg(easy: int, medium: int, hard: int, path: str = "assets/leetcode-progress.svg") -> None:
    out = Path(path)
    out.parent.mkdir(parents=True, exist_ok=True)
    out.write_text(build_svg(easy, medium, hard), encoding="utf-8")


if __name__ == "__main__":
    write_svg(3, 0, 0, "leetcode-progress.svg")
