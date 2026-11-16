# Romanian alphabet and diacritics

These are distinct demonstrations, not two labels for the same clip.

| Letter | Packaged video | Source |
| --- | --- | --- |
| A | `app/src/main/res/raw/a.mp4` | [PeSemne dictionary](https://pesemne.ro/dictionar/), entry `a`, interpreter Lavinia Chițu; [original video](https://pesemne.ro/wp-content/uploads/clips/a.mp4) |
| Ă | `app/src/main/res/raw/a_breve.mp4` | Existing ANIALMG / Fundația Orange clip bundled in this project, visibly labeled Ă; exact copy retained here as `ă.mp4` |
| Â | `app/src/main/res/raw/a_circumflex.mp4` | Asociația LSR alphabet lesson, 6.25–9.75 seconds; copy `â.mp4` |
| Î | `app/src/main/res/raw/i_circumflex.mp4` | Same lesson, 40.25–43.00 seconds; copy `î.mp4` |
| Ș | `app/src/main/res/raw/s_comma.mp4` | Same lesson, 84.00–87.70 seconds; copy `ș.mp4` |
| Ț | `app/src/main/res/raw/t_comma.mp4` | Same lesson, 92.25–96.00 seconds; copy `ț.mp4` |

## Â, Î, Ș and Ț verification

Source: [ABC – Învață alfabetul limbii semnelor (LSR), Asociația LSR](https://www.youtube.com/watch?v=0KsvyYj7dCg).
The association's [website](https://asociatialsr.ro/) describes its Romanian Sign
Language education work. The source video labels each letter and shows its
demonstration simultaneously from the front and side. The extracted clips retain
the complete gestures, source letter labels, original timing, logo and audio;
there are no invented or synthesized hand movements. Outputs are H.264/AAC MP4.

Previously Â was mapped to the T video in the database and to A in voice results;
Î, Ș and Ț reused I, S and T. They now have separate category and voice media keys.
The ordinary A, I, S and T assets are unchanged by this correction. A static
thumbnail represents a frame of the sign; the clip preserves its movement.

The old ANIALMG entries were also checked, but their embedded YouTube clips were
unavailable on 2026-11-14: [Â](https://dlmg.ro/ajax-post/?id=21675),
[Î](https://dlmg.ro/ajax-post/?id=21362), [Ș](https://dlmg.ro/ajax-post/?id=21186),
[Ț](https://dlmg.ro/ajax-post/?id=21149). They are not used as replacement footage.

Thumbnail extraction from the Asociația LSR source (time in seconds; crop `width:height:x:y`):

| Letter | Source frame time | Crop |
| --- | --- | --- |
| Â | 7.90 | `180:240:240:340` |
| Î | 41.10 | `150:200:190:280` |
| Ș | 85.90 | `160:220:190:255` |
| Ț | 93.90 | `150:220:185:205` |

Each frame keeps the whole signing hand and wrist. The shared square `fitCenter`
frame in the app remains unchanged. Category IDs, names and favorites are preserved.

The Ă demonstration had previously been assigned to A. It has been preserved
byte for byte under its distinct Ă filename. Android resource names cannot
contain Romanian diacritics, so the packaged key is `a_breve`.

SHA-256:

- A: `5d434cfffaf049c82a05a84d2608f954d48be4d82ed93fb4468aefc41de7f5ba`
- Ă: `95aae24cde2b3c7a1010449529df0afda2cadc970d9ceeccea3d4c4c43a6326c`
- Â: `70beb5ec8ff27f75016eae6f5e93479b4985b9ec7f4cef90599f58231c7b1b6a`
- Î: `40c999a84953e4b9e6a6809b03429fc36234159d70173f4b72eedebd8f9c2376`
- Ș: `7ee2451b7ce3aa5cef15f0ef918d8aee61a8d21f21f3a345f115d3e9dce4bc0e`
- Ț: `f5589aac5b7c60f1cb01319b5c1b7bd5158458d5e6b6a49be957969e36cf2b5f`

The A category uses the original photographic hand thumbnail, `a.png`.
The Ă category uses `a_breve.png`, extracted at 1 second
from its actual demonstration (180 × 240 crop at x=440, y=270, containing the
whole hand and wrist). All category images still use the shared square frame
with `fitCenter`; the image itself is not stretched.

Voice results use a separate illustration catalog. The A, Ă, Â, Î, Ș and Ț
illustrations are stored as `voice_*.png`; they do not reuse these
category photos. See [voice illustration provenance and prompts](voice-illustrations.md).

The distinction was cross-checked against the Romanian dactyl alphabet chart,
printed page 52 (PDF page 12), in [Surditate și comunicare, chapter 2, hosted by
DGASPC Sector 6](https://www.asistentasociala6.ro/files/2021/SURDITATE%20SI%20COMUNICARE%20CAP.2.pdf#page=12).
Sources checked 2026-11-14. Media remains attributed to its respective creators.
