package com.project.ohl;

import android.Manifest;
import android.app.Instrumentation;
import android.content.Intent;
import android.os.Build;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import com.project.ohl.data.remote.RetrofitService;
import com.project.ohl.presentation.MainActivity;
import com.project.ohl.presentation.user.LoginActivity;
import com.project.ohl.presentation.video_section.VideoSectionActivity;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

@RunWith(AndroidJUnit4.class)
public class GuestNavigationTest {
    @Test
    public void guestsOpeningMainAreSentToLoginBeforeSectionsAppear() {
        verifyGuestRedirect(MainActivity.class);
    }

    @Test
    public void restoredProtectedScreensCannotBypassLogin() {
        verifyGuestRedirect(VideoSectionActivity.class);
    }

    private void verifyGuestRedirect(Class<?> screen) {
        assumeTrue(RetrofitService.tokens().token() == null);

        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();

        if (BuildConfig.DEBUG && Build.VERSION.SDK_INT >= 37) {
            instrumentation
                    .getUiAutomation()
                    .grantRuntimePermission(
                            instrumentation.getTargetContext().getPackageName(),
                            Manifest.permission.ACCESS_LOCAL_NETWORK);
        }

        var monitor = instrumentation.addMonitor(LoginActivity.class.getName(), null, false);

        try {
            var opened =
                    instrumentation.startActivitySync(
                            new Intent(instrumentation.getTargetContext(), screen)
                                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            var login = instrumentation.waitForMonitorWithTimeout(monitor, 10000);

            assertNotNull("Guests must reach login", login);

            instrumentation.runOnMainSync(() -> {
                assertNull(login.findViewById(R.id.sectionsRecyclerView));
                assertNotNull(login.findViewById(R.id.login_email));
                assertTrue(opened.isFinishing() || opened.isDestroyed());

                login.finish();
            });
        } finally {
            instrumentation.removeMonitor(monitor);
        }
    }
}
