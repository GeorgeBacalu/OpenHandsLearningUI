# Open Hands Learning

Android application for learning sign language.

## Build and test

Install JDK 25 and Android Studio. Both the Gradle daemon and Java compiler use
JDK 25, and Java sources target Java 25. JDK 17 is not required.
Set the Android SDK location in your local, untracked `local.properties`, or set
`ANDROID_HOME`. Install Android SDK Platform
37.2 and Build Tools 37.0.0, and accept the SDK licenses in Android Studio.

```powershell
.\gradlew.bat clean :app:assembleDebug :app:assembleRelease :app:testDebugUnitTest :app:lintDebug :app:assembleDebugAndroidTest
```

On macOS/Linux, use `./gradlew`. Run `:app:connectedDebugAndroidTest` with an
emulator or device connected to execute instrumentation tests.

The validation includes debug, unsigned release, unit tests, lint, and
instrumentation APK compilation. With a connected device, run
`:app:connectedDebugAndroidTest` to verify lifecycle cancellation and migration of
the legacy recording database. Test databases use separate names and are removed
after verification. Architecture tests enforce the domain/data/presentation
boundaries. Lint treats warnings as errors. The reusable toolbar layout
retains its intentionally colored partial background with a scoped `Overdraw`
exception; full-screen backgrounds use their activity themes.

The emulator connects to `http://10.0.2.2:8080/` by default. Override this with
`-PapiBaseUrl=http://your-server:8080/` when building for a physical device.
For a USB-connected phone, add `apiBaseUrl=http://127.0.0.1:8080/` to the untracked
`local.properties` file, then run `adb reverse tcp:8080 tcp:8080`. This also configures
Android Studio builds. Repeat the reverse command after reconnecting the phone;
the second port must match the running backend. A `-PapiBaseUrl` argument takes
precedence over the local file. The `10.0.2.2` address is specific to the emulator.

## Dependency baseline

Updated against stable releases on 2026-11-11: Android 17 (compile API 37.2,
target API 37), Build Tools 37.0.0, Android Gradle Plugin 9.5.1, Gradle 9.9.1, and
Kotlin Gradle Plugin/compiler 2.4.21 (for Java 25 target support).
The Gradle distribution is pinned with its SHA-256 checksum. AndroidX, Material,
Kotlin standard library, Glide, Gson, Lottie, Retrofit, OkHttp, Lifecycle, Room, and instrumentation test
dependencies use their latest stable releases from Google Maven and Maven Central.

The additional architecture components use Lifecycle 2.11.0, Room 2.8.5,
OkHttp 5.5.1, and core library desugaring 2.1.5. Desugaring supports the Java library APIs used by the application
on all supported Android versions.

The minimum supported Android version is Android 7.0 (API 24).
The API 37 target enables Android 17 behavior changes; test the app's screens,
permissions, media, and navigation on a device before distributing a release.

## Build configuration

`gradle/libs.versions.toml` is the source of Android SDK, Java, plugin, and library
versions. Plugin and dependency repositories are declared in `settings.gradle`.
The Java toolchain sets the Java 25 compiler and bytecode target; the daemon's
Java 25 requirement is in `gradle/gradle-daemon-jvm.properties`.

The build uses the plugins DSL and AGP's built-in Kotlin support. The Kotlin
plugin is resolved without applying the separate Kotlin Android plugin. Java
sources use AndroidX Core directly; unused Navigation dependencies, Glide's
unused annotation processor, and obsolete Kotlin module exclusions were removed.
Fragment dependencies used by application code are explicit.
The namespace and application ID are both `com.project.ohl`. Android installs this
as a separate application from builds published with the previous application ID.
The display name is `Open Hands Learning`, and launcher and in-app logos use a
shared OHL vector mark.

## Architecture

- `domain/model`: plain Java models with no Android, Retrofit, Gson, or Room dependencies.
- `domain/repository`: repository, cancellable request, and result contracts.
- `data/remote`: Retrofit endpoints, HTTP repository, shared OkHttp connection pool, and JSON codecs.
- `data/local`: Room recording database, migration, and observable repository.
- `presentation`: Activities, Fragments, adapters, and lifecycle ViewModels.
- `app`: dependency wiring at the application boundary.

UI screens observe lifecycle-aware results instead of holding Retrofit callbacks.
ViewModels cancel superseded requests and cancel outstanding work when cleared;
queued results from cancelled searches cannot overwrite newer results. The shared
HTTP client has explicit connection, read, and call timeouts. JSON codecs exchange UUID references and stable media keys. Resolved Android resource
IDs are transient client fields and are never sent to the API. The backend's checked-in
OpenAPI contract is served at `/openapi.json`.

Entity IDs and references use UUIDs. Category navigation uses the selected parent UUID. A single
UUID-to-activity registry selects the dedicated category screens, so category
renames do not change the selected screen or its child query. The canonical
catalog UUIDs in that registry match the existing database records. New UUIDs
use the shared category browser, which loads direct children and opens the video
for a leaf. Main section selection uses fixed VIDEO and VOICE_TO_SIGN types.
The read-only API menu supplies Video → Voice to Sign order; the client preserves it. Categories load `imageUrl` and `videoUrl` from S3;
sections also provide `iconKey`, so translating display
names does not change resource lookup. Room schema version 3 preserves recordings
from versions 1 and 2 while migrating local IDs to UUIDs.

Section metadata is defined exclusively in API code. The sections table and section
mutation/detail endpoints are removed; Android reads `/api/sections` and routes by
type without section UUIDs. Section titles can change without changing routing.

`Expresii` is a parent category in Video. Its UUID opens the expression child-category
screen with search; existing expression media and favorites remain available.
Category requests use parentId and name only, and category models have no sectionId.

The main page is accessible only after `/api/auth/me` validates the JWT and
returns the current account. Guests go to login; successful login or registration
opens the main page with “Bine ai venit, {nume}!”. Its toolbar exposes settings.
Section cards provide navigation, and toolbar/system Back remain available in
categories and favorites. Protected activities also guard restored navigation.
Logout clears the task history and opens login. Connection failures leave the
main content hidden with a retry action.

The side drawer, profile selection and category/subcategory creation screens
have been removed. Favorites use the authenticated user's UUID; the API validates
ownership. Use these changes with the JWT API and V16 schema. Voice to Sign
immediately converts a successful nonblank speech transcript into finger-spelling
images using the immutable local Unicode-to-media-key dictionary. The result card
shows the transcript and its signs without manual entry or confirmation. Microphone
permission is requested only from the recording button; denied permission, cancelled
recognition and blank results show no signs.
The results use a vertical grid that adapts to screen width and font size, with
larger letter cards and full-width word separators. The page scrolls to the result
automatically; horizontal scrolling and nested scrolling are disabled.
Letter lookup works without a backend connection; uppercase/lowercase, Romanian
diacritics, legacy cedilla spellings, Unicode composition, and whitespace are
normalized consistently. The dictionary is created once and every conversion
returns fresh display models. The standalone Text to Sign section, screen and
navigation entry have been removed.
The retired letter-sign API and remote CRUD client
have been removed. A and Ă have distinct media keys, videos, category thumbnails,
and voice-to-sign images. A uses `a.mp4` (PeSemne) and the original
photographic hand image, stored once as `a.png`. Ă uses the existing ANIALMG
demonstration, previously mislabeled as A, packaged as `a_breve.mp4`,
with a photographic still of its full hand. The requested source filename
`media/alphabet/ă.mp4` is retained; Android resource filenames require
ASCII names. The resolver preserves this distinction before removing accents.
See [alphabet media provenance](media/alphabet/README.md) for sources and hashes.
Â, Î, Ș and Ț also use independent `a_circumflex`,
`i_circumflex`, `s_comma` and `t_comma` media keys.
Their clips are complete gesture excerpts from the Asociația LSR alphabet lesson,
with original frontal/profile views and audio. Category thumbnails are photographic
stills; voice results use independent black-and-white illustrations. Unicode composition, case and
legacy Ş/Ţ spellings preserve these distinct keys instead of falling back to A/I/S/T.
Category photos and voice illustrations have disjoint resource catalogs.
`bindImages` resolves category photos only; `bindSigns` resolves `catalog_voice_signs`
only and never falls back to a photo. Every letter uses `<letter>.png` for its
video category, `voice_<letter>.png` for voice results, and `<letter>.mp4` for its
video. Only A and the five diacritic letters received new artwork; the existing
drawings for the other letters retain their original content.
See [voice illustration provenance and prompts](media/alphabet/voice-illustrations.md).
Unicode letter lookup uses one text-to-media dictionary for case fallback,
Romanian diacritics, spaces, and empty input.

Favorites are read and changed through the API repository. The obsolete local
favorites and duplicate catalog in SharedPreferences have been removed. Existing
recordings in `saved_recordings.db` migrate to Room without deleting their rows;
the recording filename and filesystem path remain distinct. A shared resource
resolver normalizes Romanian diacritics and resolves compiled resource IDs through
`res/values/media_catalog.xml`, without reflective resource lookup. Add each new
packaged image or video to this catalog; unit tests verify complete coverage,
including every sign-dictionary image. Bundled image loads include the installed APK
revision and resource configuration in their Glide cache signature, so local
updates cannot reuse a thumbnail belonging to a reassigned resource ID. Bitmap artwork uses `drawable-nodpi`, while
UI icons use vectors. Room queries run off
the main thread and observations are detached when their ViewModel is cleared.

Generated build files and IDE configuration, including `.idea` directories, are
ignored at all module levels.

Release sources: [Android 17](https://developer.android.com/about/versions/17),
[SDK packages](https://dl.google.com/android/repository/repository2-3.xml),
[Android Gradle Plugin](https://developer.android.com/build/releases/agp-9-5-0-release-notes),
[AndroidX](https://developer.android.com/jetpack/androidx/versions), and
[Gradle](https://gradle.org/releases/).

## School directory

The backend provides Romania-only counties, localities and schools in the
`counties`, `cities` and `schools` tables.
`SchoolDirectoryApi` and the learning repository expose parent-filtered directory
queries. City and school searches support `name`, `offset` and `limit` (maximum
200); they do not require downloading the entire national directory.
Schools use `name` and `cityId`; user references use `schoolId`.
Registration uses searchable county, locality and school selectors. Users never
enter UUIDs or choose their own role. School CRUD remains an administrator API.
Directory imports run explicitly on the backend.

## Authentication

Registration requires name, email, a password, phone,
and a school from the directory. The API normalizes emails, assigns the USER role,
hashes passwords securely, and issues a JWT. Existing accounts can
log in with their current password. Password confirmation is checked on the phone.

The app sends JWTs in `Authorization: Bearer <token>` headers. Login and
registration return the token together with the authenticated account. JWTs are
encrypted with AES-GCM using Android Keystore and stored outside Android backups;
passwords are never persisted. Existing session-cookie storage is discarded.
Startup validates the stored token with `/api/auth/me` before showing any main
content. A 401 clears the token, and expired tokens require login again.
Settings validates the account and offers logout, which revokes the JWT on the
server and clears it locally. Tokens expire 30 minutes after issue; restarting
the single-instance API also invalidates them.

Release builds require an HTTPS API URL and reject cleartext connections. Debug
builds request Android 17 local-network permission for private API addresses and
allow HTTP for local emulator or phone previews; use test credentials on that
connection. Authentication uses no session cookie or CSRF handshake.

Favorites remain user/category pairs, but selecting or supplying a UUID never
authenticates a user or authorizes access to another account's favorites.

All app text is Romanian, with modern comma-below diacritics. The app locale is
Romanian even when the phone uses another system language. Registration, login,
and logout use „Înregistrare”, „Autentificare”, and „Deconectare”; voice conversion
uses „semn”. API section types and media keys remain stable.

## App icon

### Homepage artwork

The main page uses transparent WebP artwork (with unchanged PNG logos) in `app/src/main/res/drawable-nodpi/`:
`video_section.webp`, `voice_to_sign_section.webp`, and
`openhandslearning_logo_transparent.png`. The logo is registered in the media
catalog and referenced by `activity_main.xml`. These assets were edited with the
built-in image-generation tool. Final prompts:

**Video:** Remove the white background of this video-section illustration. Produce a clean transparent PNG cutout of ONLY the navy smartphone outline, navy video camera with cyan play button and its white stroke, and the four colorful circular buttons. Remove all pale gray background marks, faint stock-watermark camera outlines/lettering and diagonal hairlines so they do not become visible debris against a colored background. Keep the foreground geometry, arrangement and flat colors identical to the original. Use solid navy fills, no gradients or added texture. The empty phone screen, camera lens hole and home button hole should be transparent. Leave original padding and aspect ratio. Clean smooth antialiased edges, no stray pixels or shadows, no new content.

**Voice:** Edit target: supplied voice-to-sign illustration. Remove ONLY its pale white background and export a genuinely transparent RGBA PNG cutout. Retain the existing hand holding the turquoise phone, microphone circle, small phone sound waveform, and large multicolor waveform on the right. Preserve their exact composition, proportions, colors and flat illustration appearance. Do not redesign or add shapes, text, borders or shadows. The phone screen and intentional white microphone symbol must retain their original fills. Empty space around the hand, phone and right waveform must be fully transparent. Preserve any source markings.

**Homepage logo:** Create a clean production-ready transparent version of this exact existing logo. Extract only the coral heart, ivory open hands, and teal open book with ivory pages. Keep their exact shape, proportions, arrangement and square canvas padding. Use smooth solid fills and smooth edges. Completely eliminate the dark navy background, all background remnants, all white fringe/halo, all speckled pixels, and all shadows. Also remove the navy from all interior gaps between fingers, palms, book and heart: those gaps must be fully transparent with clean boundaries. No flecks, no texture, no outline, no glow. All pixels outside these three emblem shapes must have alpha zero. Do not modify the design, do not add anything. This is a transparent UI logo PNG for use on bright blue backgrounds; its edges must be exceptionally clean.

### Launcher artwork

The launcher and introductory/category-screen logos use `app/src/main/res/drawable-nodpi/openhandslearning_mark.png`:
open palms support a book and heart in ivory, teal and coral on navy. Adaptive
icons include mask-safe padding; legacy launcher aliases use the same artwork.
The artwork was made with the built-in image-generation tool.

Final image prompt:

> Refine this into a final square Android app icon for OpenHandsLearning. Redraw a COMPLETE clean emblem: two welcoming warm ivory open palms gently supporting a teal open book with ivory pages, and one small coral heart above the book. Show all three elements clearly. Smooth crisp edges and solid flat fills only; remove every stray speckle, fringe, texture and shadow. Use a perfectly uniform solid midnight navy #132B45 background covering the ENTIRE square. Keep all artwork within the central 58% of the canvas, visually centered both horizontally and vertically, with generous navy padding for Android adaptive icon masks. Bold elegant geometry, friendly modern care-and-education identity, clear negative space, no words, no letters, no border, no rounded square outline, no gradients.

For a USB-connected phone preview, run `adb reverse tcp:8080 tcp:8080`, keep the API
running on the computer's port 8080, and build with
`-PapiBaseUrl=http://127.0.0.1:8080/`. The phone uses USB forwarding while connected.

## Profile

The home toolbar opens Profil, with the signed-in name, email, phone, and the school/city/county combination returned by `/api/auth/me`. The screen supports light and dark themes, large text, loading feedback, and retry after connection failures.
Password changes require the current password, a new password, and matching confirmation. Account deletion requires an explicit confirmation and the current password; the API removes the account and its favorites. Both successful operations return to login. Logout closes the session on this device. Profile requests survive configuration changes and disable repeated submissions while pending.
Profile and catalog layout checks cover light/dark themes and large text without calling the account API. To preserve an existing device session, install both APKs as updates and invoke the instrumentation runner directly:

```powershell
.\gradlew.bat :app:assembleDebug :app:assembleDebugAndroidTest
adb install -r app/build/outputs/apk/debug/app-debug.apk
adb install -r app/build/outputs/apk/androidTest/debug/app-debug-androidTest.apk
adb shell am instrument -w -e class com.project.ohl.ProfileLayoutTest,com.project.ohl.ProfileViewModelTest,com.project.ohl.CatalogCardLayoutTest com.project.ohl.test/androidx.test.runner.AndroidJUnitRunner
```

The Gradle connected-test task can uninstall the target app during cleanup; use the direct runner above when preserving local data and the signed-in account matters. Updates must keep the same API origin so the encrypted session remains readable.

## App palette

`values/brand_colors.xml` and its night overrides define the shared indigo gradient, interactive accents, focus/pressed states, and ripple colors. Cards, buttons, search, playback, favorites, authentication, and onboarding use this palette. Category artwork keeps its white square surface.
The API URL can also be stored as `apiBaseUrl` in the untracked `local.properties`; the `-PapiBaseUrl` build property takes precedence.

## S3 media and compressed fallbacks

The screen layouts, Romanian text, routing and logos are unchanged. Categories
and sections read permanent `imageUrl` fields, and category playback reads nullable
`videoUrl`. Remote images retain the existing ImageView sizing and uncropped
artwork; bundled resources appear immediately and remain available on errors.
A failed remote video load falls back to the matching bundled clip.

All nonlogo raster images are WebP. PNG artwork retains identical decoded RGBA
pixels; JPEG photos use WebP quality 90 or higher with maximum encoding effort
and a full-resolution RGB PSNR threshold of 38 dB. Clips, including the former
`.m4v` files, are H.264 `.mp4` with faststart, original dimensions, frame rate,
content and audio. Logos remain their original PNG files and are not uploaded.
Android resource names are preserved, so layouts, the immutable sign dictionary,
and A/Ă/Â/Î/Ș/Ț distinctions continue to resolve the same media. Historical
provenance documents describe original source filenames before this conversion.

Remote media lives in `open-hands-learning/{dev|prod}/categories/...` and
`{dev|prod}/voice/...`. Category paths follow the complete parent hierarchy;
voice illustrations use `voice/voice_{mediaKey}.webp`. Category and section URLs
come from the API. Voice illustration URLs use `BuildConfig.MEDIA_BASE_URL`,
which selects `dev/` for debug and `prod/` for release. Override with
`-PmediaEnvironment=dev` or `prod` when intentionally targeting another environment.
The API must use the same `MEDIA_ENVIRONMENT` value. No AWS credentials are
stored in Android.

The companion API repository contains `infrastructure/migrate_media.py`, the
public asset manifest, scoped S3 policies and the explicit backup/upload/public
verification/database-maintenance workflow. The development database exposes
category image/video URLs and all voice/section asset URLs in `media_assets`.
