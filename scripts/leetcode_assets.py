"""Generate all animated README assets (pure SVG + SMIL, GitHub-safe, no JS).

Files used (all optional except the sync data):
  scripts/header_fonts.css   embedded @font-face rules (Space Grotesk + JetBrains Mono)
  assets/profile.jpg         your photo, shown in the header

Usage in scripts/leetcode_sync.py after counting solves:
    from leetcode_assets import write_all
    write_all(easy, medium, hard, "assets", topics)
"""
import base64
import math
from pathlib import Path

from leetcode_svg import build_svg as build_progress

HERE = Path(__file__).resolve().parent

# ---- edit these to change the header text -------------------------------------
NAME = "Rishabh Pal"
PILL = "LEETCODE \u00b7 JAVA"
GREETING = "Hi there, I'm"
ROLES = [
    ("> DSA in Java", "#00b8a3"),
    ("> One LeetCode problem at a time", "#ffc01e"),
    ("> Clean, optimized solutions", "#ff375f"),
]
TOPICS_LABEL = "Topics solved: "
TOPICS_MAX_CHARS = 66   # extra topics collapse into "+N more"
SYMBOLS = [("{ }", "#00b8a3"), ("</>", "#ffc01e"), ("O(n)", "#ff375f"), ("[ ]", "#c9d1d9")]
# -------------------------------------------------------------------------------

CX, CY = 940, 215          # photo centre
PHOTO_R, RING_R, ORBIT_R = 146, 166, 192


def _fonts_css() -> str:
    p = HERE / "header_fonts.css"
    return p.read_text(encoding="utf-8") if p.exists() else ""


def _photo_uri(photo_path):
    p = Path(photo_path) if photo_path else None
    if p and p.exists():
        return "data:image/jpeg;base64," + base64.b64encode(p.read_bytes()).decode()
    return None


def _steps(n, per, x0=0.0):
    """values + keyTimes for a discrete 'typewriter' reveal of n characters."""
    vals = ";".join(f"{x0 + per * i:.1f}" for i in range(n + 1))
    keys = ";".join(f"{i / n:.4f}" for i in range(n + 1))
    return vals, keys


def _role_anim(i, n, eps=0.03):
    a, b = i / n, (i + 1) / n
    if i == 0:
        kt, v = [0, b - eps, b, 1], [1, 1, 0, 0]
    elif i == n - 1:
        kt, v = [0, a, a + eps, 1], [0, 0, 1, 1]
    else:
        kt, v = [0, a, a + eps, b - eps, b, 1], [0, 0, 1, 1, 0, 0]
    return ";".join(map(str, v)), ";".join(f"{k:.4f}" for k in kt)


def _topics_svg(topics, static=False):
    """One line listing the topics solved so far (comes from the sync data)."""
    from html import escape
    names = list(topics or [])
    if not names:
        body = "none yet"
    else:
        shown, used = [], len(TOPICS_LABEL)
        for t in names:
            add = len(t) + (3 if shown else 0)
            if shown and used + add > TOPICS_MAX_CHARS:
                break
            shown.append(t)
            used += add
        rest = len(names) - len(shown)
        body = " \u00b7 ".join(escape(t) for t in shown) + (f" \u00b7 +{rest} more" if rest else "")
    anim = "" if static else '<animate attributeName="opacity" from="0" to="1" begin="2.2s" dur="0.8s" fill="freeze"/>'
    return (f'<text x="48" y="328" class="m" font-size="15" opacity="{1 if static else 0}">'
            f'<tspan fill="#8b949e">{TOPICS_LABEL}</tspan><tspan fill="#c9d1d9">{body}</tspan>{anim}</text>')


def build_header(easy=0, medium=0, hard=0, photo_path=None, static=False, topics=None) -> str:
    """static=True renders the final frame with no animation (for previews)."""
    A = (lambda s: "") if static else (lambda s: s)
    uri = _photo_uri(photo_path)

    # --- typed greeting
    g_n, g_cw = len(GREETING), 13.2
    g_w = g_n * g_cw
    g_vals, g_keys = _steps(g_n, g_cw)
    g_clip_w = g_w if static else 0
    cursor_x = 48 + g_w + 6

    # --- name reveal
    nm_y, nm_h = (146, 118) if static else (264, 0)

    # --- roles
    roles = []
    for i, (txt, col) in enumerate(ROLES):
        v, k = _role_anim(i, len(ROLES))
        op = 1 if (static and i == 0) else (0 if static else (1 if i == 0 else 0))
        roles.append(
            f'<text x="48" y="292" class="m b" font-size="24" fill="{col}" opacity="{op}">'
            f'{txt.replace(">", "&gt;")}'
            + A(f'<animate attributeName="opacity" values="{v}" keyTimes="{k}" dur="9s" repeatCount="indefinite"/>')
            + "</text>"
        )

    # --- difficulty chips (dynamic counts)
    chips, x = [], 48
    for k, (label, c, col) in enumerate([("EASY", easy, "#00b8a3"), ("MEDIUM", medium, "#ffc01e"), ("HARD", hard, "#ff375f")]):
        text = f"{label} {c}"
        w = int(len(text) * 9.6 + 46)
        chips.append(
            f'<g><rect x="{x}" y="346" width="{w}" height="32" rx="16" fill="{col}" fill-opacity=".1" stroke="{col}" stroke-opacity=".5"/>'
            f'<circle cx="{x + 18}" cy="362" r="4" fill="{col}">'
            + A(f'<animate attributeName="r" values="3;5.5;3" dur="2s" begin="{k * 0.4:.1f}s" repeatCount="indefinite"/>')
            + f'</circle><text x="{x + 32}" y="367" class="m b" font-size="13" fill="{col}" letter-spacing="1">{text}</text></g>'
        )
        x += w + 12

    # --- orbiting symbols
    orbit = []
    path = (f"M{CX},{CY - ORBIT_R} A{ORBIT_R},{ORBIT_R} 0 0,1 {CX},{CY + ORBIT_R} "
            f"A{ORBIT_R},{ORBIT_R} 0 0,1 {CX},{CY - ORBIT_R} Z")
    for i, (sym, col) in enumerate(SYMBOLS):
        label = f'<text y="5" class="m b" font-size="15" fill="{col}" text-anchor="middle">{sym.replace("<", "&lt;").replace(">", "&gt;")}</text>'
        if static:
            ang = math.radians(-45 + 90 * i)
            orbit.append(f'<g transform="translate({CX + ORBIT_R * math.cos(ang):.1f} {CY + ORBIT_R * math.sin(ang):.1f})">{label}</g>')
        else:
            orbit.append(
                f'<g>{label}<animateMotion path="{path}" dur="20s" begin="-{i * 5}s" repeatCount="indefinite"/></g>'
            )

    # --- photo block
    if uri:
        photo = (f'<image x="{CX - PHOTO_R}" y="{CY - PHOTO_R}" width="{2 * PHOTO_R}" height="{2 * PHOTO_R}" '
                 f'href="{uri}" preserveAspectRatio="xMidYMid slice" clip-path="url(#h_ph)"/>')
    else:
        photo = (f'<circle cx="{CX}" cy="{CY}" r="{PHOTO_R}" fill="#161722"/>'
                 f'<text x="{CX}" y="{CY + 24}" class="d" font-size="72" fill="url(#h_g)" text-anchor="middle">RP</text>')

    return f"""<svg xmlns="http://www.w3.org/2000/svg" width="1200" height="430" viewBox="0 0 1200 430" role="img"
     aria-label="{NAME} - LeetCode solutions in Java">
<defs>
<style>
{_fonts_css()}
.d{{font-family:'SG','Segoe UI',Arial,sans-serif;font-weight:700}}
.m{{font-family:'JB','SFMono-Regular',Consolas,'Courier New',monospace}}
.b{{font-weight:700}}
</style>
<linearGradient id="h_g" x1="0" y1="0" x2="1" y2="1"><stop offset="0" stop-color="#00b8a3"/><stop offset=".5" stop-color="#ffc01e"/><stop offset="1" stop-color="#ff375f"/></linearGradient>
<linearGradient id="h_name" gradientUnits="userSpaceOnUse" x1="48" y1="0" x2="608" y2="0" spreadMethod="reflect"><stop offset="0" stop-color="#00b8a3"/><stop offset=".5" stop-color="#ffc01e"/><stop offset="1" stop-color="#ff375f"/>
{A('<animateTransform attributeName="gradientTransform" type="translate" from="0 0" to="1120 0" dur="8s" repeatCount="indefinite"/>')}</linearGradient>
<radialGradient id="h_glow" cx=".5" cy=".5" r=".5"><stop offset=".6" stop-color="#00b8a3" stop-opacity=".22"/><stop offset="1" stop-color="#00b8a3" stop-opacity="0"/></radialGradient>
<linearGradient id="h_bg" x1="0" y1="0" x2="1" y2="1"><stop offset="0" stop-color="#0d1117"/><stop offset="1" stop-color="#161b22"/></linearGradient>
<pattern id="h_dots" width="18" height="18" patternUnits="userSpaceOnUse"><circle cx="2" cy="2" r="1" fill="#fff" fill-opacity=".05"/></pattern>
<clipPath id="h_ph"><circle cx="{CX}" cy="{CY}" r="{PHOTO_R}"/></clipPath>
<clipPath id="h_typ"><rect x="48" y="102" width="{g_clip_w}" height="38">{A(f'<animate attributeName="width" begin="0.3s" dur="1s" calcMode="discrete" fill="freeze" keyTimes="{g_keys}" values="{g_vals}"/>')}</rect></clipPath>
<clipPath id="h_nm"><rect x="40" y="{nm_y}" width="700" height="{nm_h}">{A('<animate attributeName="y" from="264" to="146" begin="1.1s" dur=".9s" fill="freeze" calcMode="spline" keyTimes="0;1" keySplines=".2 .8 .2 1"/><animate attributeName="height" from="0" to="118" begin="1.1s" dur=".9s" fill="freeze" calcMode="spline" keyTimes="0;1" keySplines=".2 .8 .2 1"/>')}</rect></clipPath>
</defs>

<rect width="1200" height="430" rx="26" fill="url(#h_bg)"/>
<rect width="1200" height="430" rx="26" fill="url(#h_dots)"/>
<rect x=".7" y=".7" width="1198.6" height="428.6" rx="26" fill="none" stroke="url(#h_g)" stroke-opacity=".45" stroke-width="1.4"/>

<!-- left column -->
<g>
<rect x="48" y="44" width="{int(len(PILL) * 9.2 + 56)}" height="34" rx="17" fill="#00b8a3" fill-opacity=".1" stroke="#00b8a3" stroke-opacity=".5"/>
<circle cx="70" cy="61" r="5" fill="#00b8a3">{A('<animate attributeName="r" values="4;7;4" dur="1.8s" repeatCount="indefinite"/><animate attributeName="opacity" values="1;.35;1" dur="1.8s" repeatCount="indefinite"/>')}</circle>
<text x="86" y="66" class="m b" font-size="13" fill="#00b8a3" letter-spacing="1">{PILL}</text>
</g>
<g clip-path="url(#h_typ)"><text x="48" y="128" class="m" font-size="22" fill="#e6edf3">{GREETING}</text></g>
<rect x="{cursor_x:.0f}" y="108" width="11" height="22" fill="#00b8a3">{A('<animate attributeName="opacity" values="1;0;1" dur="1s" repeatCount="indefinite"/>')}</rect>
<g clip-path="url(#h_nm)"><text x="46" y="232" class="d" font-size="100" fill="url(#h_name)" letter-spacing="-2">{NAME}</text></g>
{''.join(roles)}
{_topics_svg(topics, static)}
{''.join(chips)}

<!-- photo column -->
<circle cx="{CX}" cy="{CY}" r="{ORBIT_R + 22}" fill="url(#h_glow)"/>
<circle cx="{CX}" cy="{CY}" r="{ORBIT_R}" fill="none" stroke="#fff" stroke-opacity=".07" stroke-dasharray="3 7"/>
<g>
{A('<animateTransform attributeName="transform" type="translate" values="0 0;0 -6;0 0" dur="5s" repeatCount="indefinite"/>')}
<g transform="translate({CX} {CY})"><circle r="{RING_R}" fill="none" stroke="url(#h_g)" stroke-width="3" stroke-dasharray="70 36" stroke-linecap="round">{A('<animateTransform attributeName="transform" type="rotate" from="0" to="360" dur="14s" repeatCount="indefinite"/>')}</circle></g>
<circle cx="{CX}" cy="{CY}" r="{PHOTO_R + 5}" fill="none" stroke="#0d1117" stroke-width="10"/>
{photo}
</g>
{''.join(orbit)}
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


def write_all(easy: int, medium: int, hard: int, out_dir: str = "assets", topics=None) -> None:
    d = Path(out_dir)
    d.mkdir(parents=True, exist_ok=True)
    (d / "header.svg").write_text(build_header(easy, medium, hard, d / "profile.jpg", topics=topics), encoding="utf-8")
    (d / "divider.svg").write_text(build_divider(), encoding="utf-8")
    (d / "leetcode-progress.svg").write_text(build_progress(easy, medium, hard), encoding="utf-8")


if __name__ == "__main__":
    write_all(3, 0, 0, "assets", ["Array", "String", "Two Pointers", "Greedy"])
