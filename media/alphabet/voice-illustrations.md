# Separate voice illustrations

The video catalog keeps the existing photographic thumbnails and videos. Voice
results have their own `catalog_voice_signs` resource array. Its resource IDs
are disjoint from the category image catalog, so a missing voice illustration
cannot silently fall back to a category photograph.

Only A and the Romanian diacritic letters received new artwork. The other
letter images retain their original content. All voice drawings use the
`voice_<letter>.png` filename convention and appear only in the voice catalog.

| Letter | Voice illustration | Photo retained for video categories |
| --- | --- | --- |
| A | [voice_a.png](../../app/src/main/res/drawable-nodpi/voice_a.png) | `a.png` |
| Ă | [voice_a_breve.png](../../app/src/main/res/drawable-nodpi/voice_a_breve.png) | `a_breve.png` |
| Â | [voice_a_circumflex.png](../../app/src/main/res/drawable-nodpi/voice_a_circumflex.png) | `a_circumflex.png` |
| Î | [voice_i_circumflex.png](../../app/src/main/res/drawable-nodpi/voice_i_circumflex.png) | `i_circumflex.png` |
| Ș | [voice_s_comma.png](../../app/src/main/res/drawable-nodpi/voice_s_comma.png) | `s_comma.png` |
| Ț | [voice_t_comma.png](../../app/src/main/res/drawable-nodpi/voice_t_comma.png) | `t_comma.png` |

Created with the built-in `image_gen` tool on 2026-11-14, with transparent
backgrounds. The corresponding existing photo was the pose reference for each
edit; the existing line drawings and the user's screenshot were style references.
The final PNGs were copied into the project unchanged, then inspected against
their references for silhouette, extended digit, thumb position, and full-hand
framing. No other alphabet assets, video files or database media keys changed.

[Final prompts and inputs](voice-illustrations-prompts.json) record the six
prompts and their reference roles. A static illustration represents a pose;
the original videos retain the complete gestures and movement.

The six voice illustrations were refined on 2026-11-14 to match the visual weight
of the original alphabet: bolder black contours, larger hands inside the square
frame, and less unused margin. Their existing gestures and orientation were
preserved. The user's 18:01 screenshot and the original N illustration supplied
the style reference. The refined PNGs were generated with built-in `image_gen`
with transparent backgrounds, then copied to the same six resource paths without
postprocessing. [Refinement prompts and output provenance](voice-style-prompts.json)
record each edit. The category photographs and video clips were retained.
