# Open Hands Learning — Android

An Android app for learning Romanian sign language through category images,
video demonstrations, and voice-to-sign spelling, including Romanian letters.
The app also supports accounts, favorite signs, and school selection.

[Latest APK release](https://github.com/GeorgeBacalu/OpenHandsLearningUI/releases/latest)
· [Backend repository](https://github.com/GeorgeBacalu/OpenHandsLearningAPI)
· [Release build guide](DISTRIBUTION.md)

## Install the latest APK on Android

You need **Android 7.0 or newer** and an internet connection. You do not need
Android Studio or USB debugging to install the published APK.

1. On your phone, open the [latest release](https://github.com/GeorgeBacalu/OpenHandsLearningUI/releases/latest).
2. Expand **Assets** if necessary and download `OpenHandsLearning-<version>.apk`.
   Choose the APK, rather than either **Source code** archive.
3. Open the downloaded file from the browser's downloads or your **Files / Downloads** app.
4. If Android asks for permission to install from this source, open **Settings**
   from that prompt and enable **Allow from this source** for the browser or file
   manager opening the APK. Return to the installer and tap **Install**. On older
   Android versions this setting may be called **Unknown sources**; menu names
   vary by device. See [Google's installation guidance](https://support.google.com/pixelphone/answer/7391672?hl=en).
5. Tap **Open**, then register or sign in. Allow microphone access when you want
   to use voice-to-sign recognition. Images and videos load over the internet.

The latest published version, verified on **17 November 2026**, is **1.0.2**:
[download OpenHandsLearning-1.0.2.apk](https://github.com/GeorgeBacalu/OpenHandsLearningUI/releases/download/v1.0.2/OpenHandsLearning-1.0.2.apk).
Use the latest-release link above for future updates.

To update an existing release installation, download the new APK and install it
over the current app. You do not need to uninstall first. If Android reports a
conflicting signature, check whether the installed app is a development build
signed with another key. Uninstalling it removes local app data.

### Install over USB with ADB

With [Android SDK Platform Tools](https://developer.android.com/tools/releases/platform-tools)
available on your computer, enable **Developer options → USB debugging** on the
phone, connect it by USB, unlock it, and accept the debugging authorization prompt.
Run these commands from the directory containing the downloaded APK:

```powershell
adb devices
adb install -r ".\OpenHandsLearning-1.0.2.apk"
```

Replace the filename when installing a newer release. `-r` updates an existing
installation while retaining app data, provided the signing key matches.

## Develop locally

The project uses Java, Android Views and ViewBinding, ViewModels/LiveData,
Retrofit/OkHttp for the API, and Glide for remote images. Source is organized into
`app`, `domain`, `data`, and `presentation` packages under `app/src/main/java/com/project/ohl/`.

Requirements:

- JDK **25**, with `JAVA_HOME` pointing to it.
- Android Studio supporting the project's Android Gradle Plugin **9.5.1**.
- Android SDK **37, minor API 2**, and Build Tools **37.0.0**.
- A device or emulator running Android API **24** or newer.

Open this directory in Android Studio and let Gradle sync. The included wrapper
provides Gradle **9.9.1**. Set the SDK location through Android Studio or `sdk.dir`
in your ignored `local.properties`.

Start the [local API](https://github.com/GeorgeBacalu/OpenHandsLearningAPI#run-locally),
then choose the development API address in `local.properties`:

```properties
# Standard Android emulator: reach the computer's localhost.
apiBaseUrl=http://10.0.2.2:8080/
```

For a USB-connected phone, use `apiBaseUrl=http://127.0.0.1:8080/` instead and run
`adb reverse tcp:8080 tcp:8080` after connecting. URLs must end in `/`. On Android
versions that request local-network permission, allow it for local development.

```powershell
.\gradlew.bat :app:assembleDebug
.\gradlew.bat :app:installDebug
```

The debug APK is written to `app/build/outputs/apk/debug/app-debug.apk`. Debug and
published release builds use different signing keys, so they cannot update each
other in place under the same application ID (`com.project.ohl`).

## API and media configuration

| Build | API | Media |
| --- | --- | --- |
| Debug | `apiBaseUrl` in `local.properties` or `-PapiBaseUrl=...` | AWS S3 bucket root |
| Release | `releaseApiBaseUrl` or `OHL_RELEASE_API_BASE_URL` | `releaseMediaBaseUrl` or `OHL_RELEASE_MEDIA_BASE_URL` |

The published release uses:

```properties
releaseApiBaseUrl=https://openhandslearning-api.onrender.com/
releaseMediaBaseUrl=https://br-red-base-b9fi1yrl.storage.c-7.eu-central-1.aws.neon.tech/open-hands-learning/
```

Debug media uses `https://open-hands-learning.s3.us-east-1.amazonaws.com/`.
Both providers store `categories/` and `voice/` directly at the bucket root,
without a `dev/` or `prod/` prefix. Media credentials belong on the backend;
the APK loads public media URLs.

## Checks and release builds

```powershell
.\gradlew.bat :app:testDebugUnitTest :app:lintDebug
```

Instrumentation tests are in `app/src/androidTest/`. The live category, storage,
and playback audits require their opt-in runner arguments and, for protected
screens, a signed-in account. An ordinary connected test run does not enable
those audits automatically.

For signed release builds, endpoint configuration, and keystore setup, follow
[DISTRIBUTION.md](DISTRIBUTION.md). Keep signing material private and use the same
release key for every published update.

If the hosted API has been idle, the first sign-in or catalog request can take
longer while the Render service starts. Allow the initial request to finish.
