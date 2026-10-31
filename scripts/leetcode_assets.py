"""Generate all animated README assets (pure SVG + SMIL, GitHub-safe, no JS).

Usage in scripts/leetcode_sync.py after counting solves:
    from leetcode_assets import write_all
    write_all(easy, medium, hard, "assets")
"""
from pathlib import Path

from leetcode_svg import build_svg as build_progress

TITLE = "LeetCode Solutions  /  Java"
CHAR_W = 13.4  # approx width of one monospace char at 22px


def build_header() -> str:
    n = len(TITLE)
    w = round(n * CHAR_W + 6)
    widths = ";".join(str(round(w * i / n)) for i in range(n + 1))
    xs = ";".join(str(round(w * i / n)) for i in range(n + 1))
    keys = ";".join(f"{i / n:.4f}" for i in range(n + 1))
    return f"""<svg xmlns="http://www.w3.org/2000/svg" width="780" height="190" viewBox="0 0 780 190" role="img"
     aria-label="LeetCode Solutions in Java">
  <defs>
    <linearGradient id="bg" x1="0" y1="0" x2="1" y2="1">
      <stop offset="0" stop-color="#0d1117"><animate attributeName="stop-color" values="#0d1117;#10202b;#1b1030;#0d1117" dur="10s" repeatCount="indefinite"/></stop>
      <stop offset="1" stop-color="#161b22"><animate attributeName="stop-color" values="#161b22;#1b1030;#10202b;#161b22" dur="10s" repeatCount="indefinite"/></stop>
    </linearGradient>
    <linearGradient id="wave" x1="0" y1="0" x2="1" y2="0">
      <stop offset="0" stop-color="#00b8a3"/><stop offset="0.5" stop-color="#ffc01e"/><stop offset="1" stop-color="#ff375f"/>
    </linearGradient>
    <clipPath id="type"><rect x="0" y="0" width="0" height="60">
      <animate attributeName="width" from="0" to="{w}" dur="2.4s" begin="0.4s" fill="freeze" calcMode="discrete" values="{widths}" keyTimes="{keys}"/>
    </rect></clipPath>
    <style>
      text {{ font-family: 'Fira Code', 'JetBrains Mono', Consolas, 'Courier New', monospace; }}
      .t {{ font-size: 22px; font-weight: 700; fill: #e6edf3; }}
      .s {{ font-size: 13px; fill: #8b949e; }}
    </style>
  </defs>
  <rect width="780" height="190" rx="16" fill="url(#bg)"/>

  <!-- code-ish floating symbols -->
  <g font-size="16" fill="#30363d" font-family="monospace">
    <text x="60" y="40">{{ }}<animate attributeName="y" values="40;30;40" dur="5s" repeatCount="indefinite"/></text>
    <text x="690" y="60">&lt;/&gt;<animate attributeName="y" values="60;48;60" dur="6s" repeatCount="indefinite"/></text>
    <text x="620" y="130">[ ]<animate attributeName="y" values="130;120;130" dur="4.5s" repeatCount="indefinite"/></text>
    <text x="120" y="120">O(n)<animate attributeName="y" values="120;110;120" dur="5.5s" repeatCount="indefinite"/></text>
  </g>

  <g transform="translate(70 92)">
    <g clip-path="url(#type)"><text class="t" y="0">{TITLE}</text></g>
    <rect x="0" y="-20" width="3" height="26" fill="#00b8a3">
      <animate attributeName="x" values="{xs}" keyTimes="{keys}" calcMode="discrete" dur="2.4s" begin="0.4s" fill="freeze"/>
      <animate attributeName="opacity" values="1;0;1" dur="0.9s" begin="2.8s" repeatCount="indefinite"/>
    </rect>
    <text class="s" y="30" opacity="0">Arrays · Strings · Two Pointers · Greedy · one problem at a time
      <animate attributeName="opacity" from="0" to="1" dur="0.8s" begin="2.9s" fill="freeze"/>
    </text>
  </g>

  <!-- waves -->
  <g>
    <path fill="url(#wave)" opacity="0.35">
      <animate attributeName="d" dur="6s" repeatCount="indefinite"
        values="M0 160 Q 100 140 200 160 T 400 160 T 600 160 T 800 160 V190 H0Z;
                M0 160 Q 100 180 200 160 T 400 160 T 600 160 T 800 160 V190 H0Z;
                M0 160 Q 100 140 200 160 T 400 160 T 600 160 T 800 160 V190 H0Z"/>
    </path>
    <path fill="url(#wave)" opacity="0.6">
      <animate attributeName="d" dur="4s" repeatCount="indefinite"
        values="M0 172 Q 100 188 200 172 T 400 172 T 600 172 T 800 172 V190 H0Z;
                M0 172 Q 100 156 200 172 T 400 172 T 600 172 T 800 172 V190 H0Z;
                M0 172 Q 100 188 200 172 T 400 172 T 600 172 T 800 172 V190 H0Z"/>
    </path>
  </g>
</svg>
"""


def build_divider() -> str:
    return """<svg xmlns="http://www.w3.org/2000/svg" width="780" height="20" viewBox="0 0 780 20" role="presentation">
  <defs>
    <linearGradient id="g" x1="0" y1="0" x2="1" y2="0">
      <stop offset="0" stop-color="#00b8a3" stop-opacity="0"/>
      <stop offset="0.3" stop-color="#00b8a3"/><stop offset="0.5" stop-color="#ffc01e"/><stop offset="0.7" stop-color="#ff375f"/>
      <stop offset="1" stop-color="#ff375f" stop-opacity="0"/>
    </linearGradient>
  </defs>
  <rect x="40" y="9" width="700" height="2" rx="1" fill="url(#g)" opacity="0.6"/>
  <circle cy="10" r="4" fill="#fff">
    <animate attributeName="cx" values="40;740;40" dur="5s" repeatCount="indefinite" calcMode="spline" keyTimes="0;0.5;1" keySplines="0.4 0 0.2 1;0.4 0 0.2 1"/>
    <animate attributeName="fill" values="#00b8a3;#ffc01e;#ff375f;#ffc01e;#00b8a3" dur="5s" repeatCount="indefinite"/>
  </circle>
</svg>
"""


def write_all(easy: int, medium: int, hard: int, out_dir: str = "assets") -> None:
    d = Path(out_dir)
    d.mkdir(parents=True, exist_ok=True)
    (d / "header.svg").write_text(build_header(), encoding="utf-8")
    (d / "divider.svg").write_text(build_divider(), encoding="utf-8")
    (d / "leetcode-progress.svg").write_text(build_progress(easy, medium, hard), encoding="utf-8")


if __name__ == "__main__":
    write_all(3, 0, 0, "assets")
