package com.project.ohl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.SeekBar;
import android.widget.VideoView;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import com.project.ohl.app.ApplicationGraph;
import com.project.ohl.data.remote.RetrofitService;
import com.project.ohl.domain.repository.Request;
import com.project.ohl.domain.repository.Result;
import com.project.ohl.presentation.video_section.VideoPlayerActivity;
import java.io.File;
import java.io.FileOutputStream;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BooleanSupplier;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import static org.junit.Assume.assumeTrue;

/** Read-only device audit; opt in with -e auditVideoPlayback true while signed in. */
@RunWith(AndroidJUnit4.class)
public class VideoPlayerPlaybackTest {
    private final android.app.Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();

    @Test public void playbackPausesSeeksRestoresAndReplays() throws Exception {
        assumeTrue("true".equals(InstrumentationRegistry.getArguments().getString("auditVideoPlayback")));
        assumeTrue("Sign in before running the playback audit", RetrofitService.tokens().token() != null);
        assertTrue("Saved session is valid", awaitResult(ApplicationGraph.authentication().current()).isSuccess());
        var catalog = awaitResult(ApplicationGraph.learning().getAllCategories());
        assertTrue("Catalog is available", catalog.isSuccess());
        var category = catalog.value().stream().filter(value -> "acesta_este_colegul_meu".equals(value.getMediaKey()))
                .findFirst().orElseThrow();
        var context = instrumentation.getTargetContext();
        Activity activity = instrumentation.startActivitySync(new Intent(context, VideoPlayerActivity.class)
                .putExtra(VideoPlayerActivity.CATEGORY_ID, category.getId().toString())
                .putExtra(VideoPlayerActivity.CATEGORY_NAME, category.getName())
                .putExtra(VideoPlayerActivity.CATEGORY_IMAGE, com.project.ohl.presentation.MediaResources.imageUrl("acesta_este_colegul_meu"))
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        try {
            Activity first = activity;
            showPlayer(first);
            await(() -> first.findViewById(R.id.video_play_pause).isEnabled());
            instrumentation.runOnMainSync(() -> {
                VideoView video = first.findViewById(R.id.videoView);
                if (video.isPlaying()) first.findViewById(R.id.video_play_pause).performClick();
                assertFalse("Pause stops playback", video.isPlaying());
            });
            var targetPosition = new int[1];
            instrumentation.runOnMainSync(() -> {
                SeekBar progress = first.findViewById(R.id.video_progress);
                targetPosition[0] = progress.getMax() / 2;
                var arguments = new Bundle();
                arguments.putFloat(AccessibilityNodeInfo.ACTION_ARGUMENT_PROGRESS_VALUE, targetPosition[0]);
                assertTrue("Playback can be scrubbed with accessibility actions", progress.performAccessibilityAction(
                        AccessibilityNodeInfo.AccessibilityAction.ACTION_SET_PROGRESS.getId(), arguments));
            });
            await(() -> Math.abs(((VideoView) first.findViewById(R.id.videoView)).getCurrentPosition() - targetPosition[0]) < 800);
            var monitor = instrumentation.addMonitor(VideoPlayerActivity.class.getName(), null, false);
            try {
                instrumentation.runOnMainSync(first::recreate);
                activity = instrumentation.waitForMonitorWithTimeout(monitor, 10000);
                assertNotNull("Player is recreated", activity);
            } finally { instrumentation.removeMonitor(monitor); }
            Activity restored = activity;
            showPlayer(restored);
            await(() -> restored.findViewById(R.id.video_play_pause).isEnabled());
            await(() -> Math.abs(((VideoView) restored.findViewById(R.id.videoView)).getCurrentPosition() - targetPosition[0]) < 800);
            instrumentation.runOnMainSync(() -> {
                assertFalse("Recreation preserves paused playback", ((VideoView) restored.findViewById(R.id.videoView)).isPlaying());
                restored.findViewById(R.id.video_replay).performClick();
            });
            await(() -> ((VideoView) restored.findViewById(R.id.videoView)).isPlaying()
                    && ((VideoView) restored.findViewById(R.id.videoView)).getCurrentPosition() < 1500);
            await(() -> ((VideoView) restored.findViewById(R.id.videoView)).getCurrentPosition() >= 600);
            instrumentation.runOnMainSync(() -> restored.findViewById(R.id.video_play_pause).performClick());
            var screenshot = instrumentation.getUiAutomation().takeScreenshot();
            assertNotNull(screenshot);
            try (var output = new FileOutputStream(new File(context.getExternalFilesDir(null), "video-player-live.png"))) {
                assertTrue(screenshot.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, output));
            } finally { screenshot.recycle(); }
        } finally {
            if (activity != null) {
                Activity opened = activity;
                instrumentation.runOnMainSync(opened::finish);
            }
        }
    }

    private void showPlayer(Activity activity) {
        instrumentation.runOnMainSync(() -> {
            activity.getWindow().addFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            if (android.os.Build.VERSION.SDK_INT >= 27) {
                activity.setShowWhenLocked(true);
                activity.setTurnScreenOn(true);
            }
        });
    }

    private <T> Result<T> awaitResult(Request<T> request) throws InterruptedException {
        var result = new AtomicReference<Result<T>>();
        var completed = new CountDownLatch(1);
        var cancellation = request.start(value -> { result.set(value); completed.countDown(); });
        try { assertTrue("API read completed", completed.await(15, TimeUnit.SECONDS)); }
        finally { cancellation.cancel(); }
        return result.get();
    }

    private void await(BooleanSupplier condition) {
        long deadline = SystemClock.uptimeMillis() + 10000;
        while (SystemClock.uptimeMillis() < deadline) {
            var ready = new AtomicBoolean();
            instrumentation.runOnMainSync(() -> ready.set(condition.getAsBoolean()));
            if (ready.get()) return;
            SystemClock.sleep(100);
        }
        fail("Player did not reach the expected state");
    }
}
