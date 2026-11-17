# Direct Android distribution

The selected distribution route is a signed APK outside Google Play, with a $0
budget. Google Play registration was not completed.

## Hosted services

- API: https://openhandslearning-api.onrender.com/
- Media: https://br-red-base-b9fi1yrl.storage.c-7.eu-central-1.aws.neon.tech/open-hands-learning/
- PostgreSQL and media run in the app's separate Neon Free project; the API uses
  Render Free compute. The production catalog contains 207 categories and the
  public school directory. Local user accounts were not copied.
- The private release keystore is in `signing/openhandslearning-release.jks`, with
  its configuration in ignored `signing.properties`. Back up both privately;
  future APK updates require the same signing key.

## Build a signed APK

The first release key has already been generated. On another machine, restore
the private backup instead of generating a replacement. For a separate new app,
your JDK's interactive keytool command is:

```powershell
keytool -genkeypair -v -keystore openhandslearning-release.jks -alias openhandslearning -keyalg RSA -keysize 3072 -validity 10000
```

Move the keystore into `signing/` and copy `signing.properties.example` to
`signing.properties`. Fill in the local passwords. Both the properties file and
keystore are ignored by Git. Do not put passwords on the command line or commit
them. Back up these files privately before distributing the first release.

Set `releaseApiBaseUrl` in `local.properties` to the real HTTPS backend URL with a
trailing `/`, or set `OHL_RELEASE_API_BASE_URL` in the build environment. Release
configuration is separate from the existing development `apiBaseUrl`.
Set `releaseMediaBaseUrl` (or `OHL_RELEASE_MEDIA_BASE_URL`) to the media URL above,
also with a trailing `/`. Both release URLs are configured in this workspace's
ignored `local.properties`. Development URLs and media settings are separate. Debug media uses the AWS
bucket root (`categories/` and `voice/`), without an environment prefix. The
obsolete `mediaEnvironment` Gradle option has been removed.

```powershell
.\gradlew.bat :app:verifyReleaseConfiguration
.\gradlew.bat :app:testDebugUnitTest :app:lintDebug :app:assembleRelease
```

The output is `app/build/outputs/apk/release/app-release.apk`. Verify the signature
with Android SDK `apksigner verify --verbose --print-certs` before sharing it.
Increase `versionCode` in `app/build.gradle` for each distributed update and sign
every update with the same keystore. A debug install signed with another key must
be uninstalled before installing the release; uninstalling removes local app data.

CI can use `OHL_KEYSTORE_FILE`, `OHL_KEYSTORE_PASSWORD`, `OHL_KEY_ALIAS`, and
`OHL_KEY_PASSWORD` instead of `signing.properties`.

## Local development testing

```powershell
.\gradlew.bat :app:assembleDebug
```

`app/build/outputs/apk/debug/app-debug.apk` is a development build, not a working
public release. With the development loopback API URL, a USB-connected Android device
or emulator needs `adb reverse tcp:8080 tcp:8080`, and the local backend must be
running. Do not advertise this APK as ready for general users.

## Free hosting limits

Render Free runs the Java API; Neon Free runs PostgreSQL and the public media
bucket. No payment card was added. Both providers impose quotas and can suspend
service when those are exhausted. Render sleeps after idle time and a cold start
can take several minutes. The release API client waits up to 210 seconds for a
request, so the first connection may take longer than later ones.

The signed release uses Neon media, which was recovered from local Git history
and uploaded without downloading from S3. Existing AWS resources and their
billing are separate from this free deployment.

Sources checked on 2026-11-17:

- https://render.com/docs/free
- https://neon.com/pricing
- https://support.google.com/googleplay/android-developer/answer/6112435
