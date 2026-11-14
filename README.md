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
The read-only API menu supplies Video → Voice to Sign order; the client preserves it. Category images and videos use `mediaKey`;
sections also provide `iconKey`, so translating display
names does not change resource lookup. Room schema version 3 preserves recordings
from versions 1 and 2 while migrating local IDs to UUIDs.

Section metadata is defined exclusively in API code. The sections table and section
mutation/detail endpoints are removed; Android reads `/api/sections` and routes by
type without section UUIDs. Section titles can change without changing routing.

`Expresii` is a parent category in Video. Its UUID opens the expression child-category
screen with search; existing expression media and favorites remain available.
Category requests use parentId and name only, and category models have no sectionId.

The navigation bar shows registration for guests and settings only after
`/api/auth/me` confirms a server-authenticated account. Login is available from
the registration form. The side drawer and hamburger button have been removed;
section cards provide navigation. Profile selection has been removed.
Favorites use the authenticated user's UUID, which the server checks against the
session. Use these changes with the secured API and V15 schema. Voice to Sign supports typed text and speech
recognition using an immutable local Unicode-to-media-key dictionary.
Letter lookup works without a backend connection; uppercase/lowercase, Romanian
diacritics, legacy cedilla spellings, Unicode composition, and whitespace are
normalized consistently. The dictionary is created once and every conversion
returns fresh display models. The standalone Text to Sign section, screen and
navigation entry have been removed.
The retired letter-sign API and remote CRUD client
have been removed. The retained `dactilema_a` image belongs
to the matching line-drawing collection; the unrelated photographic `a.png` was
removed. Unicode letter lookup handles case fallback, Romanian diacritics, spaces,
and empty input without a second hardcoded sign catalog.

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

Registration requires name, email, a password of at least 15 characters, phone,
and a school from the directory. The API normalizes emails, assigns the USER role,
hashes passwords with BCrypt, and starts a session. Existing BCrypt accounts can
log in with their current password. Password confirmation is checked on the phone.

The app sends session cookies and fetches a fresh CSRF token before mutations.
The session cookie is encrypted with AES-GCM using Android Keystore and stored
outside Android backups. Passwords are not persisted. App startup validates the
stored session with `/api/auth/me`; 401 responses clear the cookie. Settings
validates the session again and offers logout, which revokes the server session
and clears the local cookie. Expired sessions require login again.

Release builds require an HTTPS API URL and reject cleartext connections. Debug
builds request Android 17 local-network permission for private API addresses and
allow HTTP for local emulator or phone previews; use test credentials on
that connection. The local HTTP API also requires `SESSION_COOKIE_SECURE=false`.
Production keeps secure cookies enabled and terminates HTTPS at the API or proxy.

Favorites remain user/category pairs, but selecting or supplying a UUID never
authenticates a user or authorizes access to another account's favorites.

All app text is Romanian, with modern comma-below diacritics. The app locale is
Romanian even when the phone uses another system language. Registration, login,
and logout use „Înregistrare”, „Autentificare”, and „Deconectare”; voice conversion
uses „semn”. API section types and media keys remain stable.

## App icon

The launcher and in-app logo use `app/src/main/res/drawable-nodpi/openhandslearning_mark.png`:
open palms support a book and heart in ivory, teal and coral on navy. Adaptive
icons include mask-safe padding; legacy launcher aliases use the same artwork.
The artwork was made with the built-in image-generation tool.

Final image prompt:

> Refine this into a final square Android app icon for OpenHandsLearning. Redraw a COMPLETE clean emblem: two welcoming warm ivory open palms gently supporting a teal open book with ivory pages, and one small coral heart above the book. Show all three elements clearly. Smooth crisp edges and solid flat fills only; remove every stray speckle, fringe, texture and shadow. Use a perfectly uniform solid midnight navy #132B45 background covering the ENTIRE square. Keep all artwork within the central 58% of the canvas, visually centered both horizontally and vertically, with generous navy padding for Android adaptive icon masks. Bold elegant geometry, friendly modern care-and-education identity, clear negative space, no words, no letters, no border, no rounded square outline, no gradients.

For a USB-connected phone preview, run `adb reverse tcp:8080 tcp:8080`, keep the API
running on the computer's port 8080, and build with
`-PapiBaseUrl=http://127.0.0.1:8080/`. The phone uses USB forwarding while connected.
