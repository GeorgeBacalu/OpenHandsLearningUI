package com.project.ohl;

import android.app.Instrumentation;
import android.content.Intent;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import com.project.ohl.data.remote.RetrofitService;
import com.project.ohl.presentation.MainActivity;
import com.project.ohl.presentation.user.LoginActivity;
import com.project.ohl.presentation.video_section.VideoSectionActivity;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import static org.junit.Assume.assumeTrue;

@RunWith(AndroidJUnit4.class)
public class GuestNavigationTest {
    @Test public void guestsOpeningMainAreSentToLoginBeforeSectionsAppear() {
        verifyGuestRedirect(MainActivity.class);
    }

    @Test public void restoredProtectedScreensCannotBypassLogin() {
        verifyGuestRedirect(VideoSectionActivity.class);
    }

    private void verifyGuestRedirect(Class<?> screen) {
        // Preserve a developer's signed-in account when running on a personal device.
        assumeTrue(RetrofitService.tokens().token() == null);
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        var monitor = instrumentation.addMonitor(LoginActivity.class.getName(), null, false);
        try {
            var opened = instrumentation.startActivitySync(new Intent(instrumentation.getTargetContext(), screen)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            var login = instrumentation.waitForMonitorWithTimeout(monitor, 10000);
            assertNotNull("Guests must reach login", login);
            instrumentation.runOnMainSync(() -> {
                assertNull(login.findViewById(R.id.sectionsRecyclerView));
                assertNotNull(login.findViewById(R.id.login_email));
                assertTrue(opened.isFinishing() || opened.isDestroyed());
                login.finish();
            });
        } finally { instrumentation.removeMonitor(monitor); }
    }
}
