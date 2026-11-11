# OpenHandsLearning

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

The upgrade validation builds debug, unsigned release, and instrumentation APKs,
and passes the unit test. Lint reports 19 missing-translation errors and 581
warnings in existing resources/code; `abortOnError = false` allows the build to
complete. Review `app/build/reports/lint-results-debug.html` for the details.
Instrumentation tests still need to be executed on a device or emulator.

The emulator connects to `http://10.0.2.2:8080/` by default. Override this with
`-PapiBaseUrl=http://your-server:8080/` when building for a physical device.

## Dependency baseline

Updated against stable releases on 2026-11-11: Android 17 (compile API 37.2,
target API 37), Build Tools 37.0.0, Android Gradle Plugin 9.5.1, Gradle 9.9.1, and
Kotlin Gradle Plugin/compiler 2.4.21 (for Java 25 target support).
The Gradle distribution is pinned with its SHA-256 checksum. AndroidX, Material,
Kotlin standard library, Glide, Gson, Lottie, Retrofit, and instrumentation test
dependencies use their latest stable releases from Google Maven and Maven Central.

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
DrawerLayout and Fragment dependencies used by application code are explicit.
The namespace is `com.project.ohl`; the installed application ID remains
`com.example.llt_project_separate`.

Generated build files and IDE configuration are ignored at all module levels.

Release sources: [Android 17](https://developer.android.com/about/versions/17),
[SDK packages](https://dl.google.com/android/repository/repository2-3.xml),
[Android Gradle Plugin](https://developer.android.com/build/releases/agp-9-5-0-release-notes),
[AndroidX](https://developer.android.com/jetpack/androidx/versions), and
[Gradle](https://gradle.org/releases/).
