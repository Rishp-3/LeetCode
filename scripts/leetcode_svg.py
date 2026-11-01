"""Animated LeetCode progress card (pure SVG + SMIL, works in GitHub READMEs).

Shows what share of the problems you solved is Easy / Medium / Hard (in %),
as an animated donut plus one bar per difficulty.

    from leetcode_svg import build_svg
    svg = build_svg(easy=3, medium=0, hard=0)
"""
from math import pi

COLORS = {"Easy": "#00b8a3", "Medium": "#ffc01e", "Hard": "#ff375f"}

BAR_X, BAR_W, BAR_H = 250, 470, 14
RING_R = 52
RING_C = 2 * pi * RING_R


def fmt_pct(p: float) -> str:
    return f"{p:.0f}%" if abs(p - round(p)) < 0.05 else f"{p:.1f}%"


def build_svg(easy: int, medium: int, hard: int, static: bool = False) -> str:
    """static=True renders the final frame without animation (for previews)."""
    A = (lambda s: "") if static else (lambda s: s)
    counts = {"Easy": easy, "Medium": medium, "Hard": hard}
    total = sum(counts.values())
    shares = {k: (v * 100.0 / total if total else 0.0) for k, v in counts.items()}

    # ---- donut segments (share of solved, clockwise from the top)
    nonzero = [k for k in counts if counts[k] > 0]
    gap = 3.0 if len(nonzero) > 1 else 0.0
    segs, start = [], 0.0
    for n, name in enumerate(nonzero):
        full = RING_C * shares[name] / 100.0
        length = max(full - gap, 0.5)
        begin = 0.3 + n * 0.35
        dash_final = f"{length:.2f} {RING_C:.2f}"
        segs.append(
            f'<circle r="{RING_R}" fill="none" stroke="{COLORS[name]}" stroke-width="12" '
            f'stroke-dasharray="{dash_final if static else f"0 {RING_C:.2f}"}" '
            f'stroke-dashoffset="{-(start + gap / 2):.2f}" transform="rotate(-90)" filter="url(#glow)">'
            + A(f'<animate attributeName="stroke-dasharray" from="0 {RING_C:.2f}" to="{dash_final}" '
                f'dur="0.9s" begin="{begin:.2f}s" fill="freeze" calcMode="spline" keyTimes="0;1" keySplines="0.2 0.8 0.2 1"/>')
            + "</circle>"
        )
        start += full

    # ---- rows
    rows = []
    for i, name in enumerate(["Easy", "Medium", "Hard"]):
        y = 78 + i * 52
        c, col, p = counts[name], COLORS[name], shares[name]
        w = round(BAR_W * p / 100.0, 1)
        begin = 0.3 + i * 0.25
        noun = "solved"
        rows.append(f"""
  <g opacity="{1 if static else 0}">
    {A(f'<animate attributeName="opacity" from="0" to="1" dur="0.5s" begin="{begin}s" fill="freeze"/>')}
    <circle cx="{BAR_X - 14}" cy="{y - 5}" r="4" fill="{col}">{A('<animate attributeName="r" values="3;5.5;3" dur="2s" repeatCount="indefinite"/>')}</circle>
    <text x="{BAR_X}" y="{y - 14}" class="lbl">{name}</text>
    <text x="{BAR_X + BAR_W - 78}" y="{y - 14}" text-anchor="end" class="pct" fill="{col}">{fmt_pct(p)}</text>
    <text x="{BAR_X + BAR_W}" y="{y - 14}" text-anchor="end" class="cnt">{c} {noun}</text>
    <rect x="{BAR_X}" y="{y - 6}" width="{BAR_W}" height="{BAR_H}" rx="7" fill="#21262d"/>
    <rect x="{BAR_X}" y="{y - 6}" width="{w if static else 0}" height="{BAR_H}" rx="7" fill="{col}" filter="url(#glow)">
      {A(f'<animate attributeName="width" from="0" to="{w}" dur="1.4s" begin="{begin}s" fill="freeze" calcMode="spline" keyTimes="0;1" keySplines="0.2 0.8 0.2 1"/>')}
    </rect>
  </g>""")

    return f"""<svg xmlns="http://www.w3.org/2000/svg" width="780" height="250" viewBox="0 0 780 250" role="img"
     aria-label="LeetCode progress: {total} solved. Easy {fmt_pct(shares['Easy'])}, Medium {fmt_pct(shares['Medium'])}, Hard {fmt_pct(shares['Hard'])}">
  <defs>
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
      .cap   {{ font-size: 11px; fill: #8b949e; letter-spacing: 1.6px; }}
      .lbl   {{ font-size: 14px; font-weight: 600; fill: #c9d1d9; }}
      .pct   {{ font-size: 15px; font-weight: 800; }}
      .cnt   {{ font-size: 12px; fill: #8b949e; }}
      .big   {{ font-size: 34px; font-weight: 800; fill: #e6edf3; }}
      .sub   {{ font-size: 12px; fill: #8b949e; letter-spacing: 1.5px; }}
    </style>
  </defs>

  <rect x="1" y="1" width="778" height="248" rx="16" fill="url(#bg)" stroke="#30363d"/>

  <!-- floating background dots -->
  <circle cx="700" cy="215" r="2" fill="#00b8a3" opacity="0.5">{A('<animate attributeName="cy" values="215;203;215" dur="5s" repeatCount="indefinite"/>')}</circle>
  <circle cx="740" cy="190" r="2.5" fill="#ffc01e" opacity="0.4">{A('<animate attributeName="cy" values="190;178;190" dur="6s" repeatCount="indefinite"/>')}</circle>
  <circle cx="30" cy="228" r="2" fill="#ff375f" opacity="0.4">{A('<animate attributeName="cy" values="228;216;228" dur="4.5s" repeatCount="indefinite"/>')}</circle>

  <text x="28" y="40" class="title">LeetCode Progress</text>
  <text x="752" y="38" class="cap" text-anchor="end">SOLVED BY DIFFICULTY (%)</text>

  <!-- donut: share of solved problems -->
  <g transform="translate(115 142)">
    <circle r="{RING_R}" fill="none" stroke="#21262d" stroke-width="12"/>
    {''.join(segs)}
    <text class="big" text-anchor="middle" y="8" opacity="{1 if static else 0}">{total}
      {A('<animate attributeName="opacity" from="0" to="1" dur="0.8s" begin="0.6s" fill="freeze"/>')}
    </text>
    <text class="sub" text-anchor="middle" y="30">SOLVED</text>
  </g>
{''.join(rows)}
</svg>
"""
