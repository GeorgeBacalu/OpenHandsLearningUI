package com.project.ohl;

import android.Manifest;
import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.MediaMetadataRetriever;
import android.speech.RecognizerIntent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import com.project.ohl.domain.model.SignText;
import com.project.ohl.presentation.MediaResources;
import com.project.ohl.presentation.voice_to_sign_section.VoiceToSignSectionActivity;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import org.junit.runner.RunWith;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class SignMediaTest {
    @Test
    public void aAndABreveResolveDistinctPlayableVideosAndImages() throws Exception {
        assertNotEquals(TestMedia.video("a"), TestMedia.video("ă"));
        assertNotEquals(TestMedia.alphabetPhoto("a"), TestMedia.alphabetPhoto("ă"));
        assertNotEquals(TestMedia.alphabetPhoto("a"), MediaResources.voiceUrl("a"));
        assertNotEquals(MediaResources.voiceUrl("a"), MediaResources.voiceUrl("ă"));

        for (String url : List.of(TestMedia.video("a"), TestMedia.video("ă"))) {
            try (var metadata = new MediaMetadataRetriever()) {
                metadata.setDataSource(url, Map.of());

                assertTrue(
                        Long.parseLong(
                                        requireNonNull(
                                                metadata.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)))
                                >= 2000);

                var frame = metadata.getFrameAtTime(1_000_000, MediaMetadataRetriever.OPTION_CLOSEST);

                assertNotNull(frame);

                frame.recycle();
            }
        }
    }

    @Test
    public void resolvesEveryDictionaryEntryFromS3WithoutBundledMedia() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

        for (var sign : SignText.translate("ABCDEFGHIJKLMNOPQRSTUVWXYZĂÂÎȘȚ "))
            assertNotNull(MediaResources.voiceUrl(sign.mediaKey()));

        assertEquals(0, context.getResources().getIdentifier("a", "drawable", context.getPackageName()));
        assertEquals(0, context.getResources().getIdentifier("a", "raw", context.getPackageName()));
    }

    @Test
    public void romanianDactylsUseDistinctImagesAndPlayableDemonstrations() throws Exception {
        var videos = new HashSet<String>();

        for (var sign : SignText.translate("ÂÎȘȚ")) {
            String photo = TestMedia.alphabetPhoto(sign.mediaKey());
            String illustration = MediaResources.voiceUrl(sign.mediaKey());
            String video = TestMedia.video(sign.letter());

            assertNotNull(photo);
            assertNotNull(illustration);
            assertNotEquals(photo, illustration);
            assertTrue(videos.add(video));

            try (var metadata = new MediaMetadataRetriever()) {
                metadata.setDataSource(video, Map.of());

                var beginning = metadata.getFrameAtTime(0, MediaMetadataRetriever.OPTION_CLOSEST);
                var gesture = metadata.getFrameAtTime(1_000_000, MediaMetadataRetriever.OPTION_CLOSEST);

                assertNotNull(beginning);
                assertNotNull(gesture);
                assertFalse("The demonstration must contain movement", beginning.sameAs(gesture));

                beginning.recycle();

                gesture.recycle();
            }
        }

        assertNotEquals(TestMedia.video("t"), TestMedia.video("â"));
    }

    @Test
    public void voiceAndCategoryImagesCannotFallBackToEachOther() {
        assertNotEquals(TestMedia.alphabetPhoto("a"), MediaResources.voiceUrl("a"));
        assertNull(MediaResources.voiceUrl("alfabet"));

        for (var sign : SignText.translate("ABCDEFGHIJKLMNOPQRSTUVWXYZĂÂÎȘȚ "))
            assertTrue(MediaResources.voiceUrl(sign.mediaKey()).contains("/voice/"));
    }

    @Test
    public void voiceRecognitionDisplaysDistinctRomanianDactyls() throws Exception {
        checkRecognitionResult(Activity.RESULT_OK, "Â Î Ș Ț", 7);
    }

    @Test
    public void voiceRecognitionImmediatelyDisplaysSigns() throws Exception {
        checkRecognitionResult(Activity.RESULT_OK, "aĂ ș!", 4);
    }

    @Test
    public void cancelledRecognitionDoesNotDisplaySigns() throws Exception {
        checkRecognitionResult(Activity.RESULT_CANCELED, "aĂ ș!", 0);
    }

    @Test
    public void blankRecognitionDoesNotDisplaySigns() throws Exception {
        checkRecognitionResult(Activity.RESULT_OK, "   ", 0);
    }

    @Test
    public void longTranscriptWrapsIntoRowsWithoutHorizontalScrolling() throws Exception {
        checkRecognitionResult(Activity.RESULT_OK, "Bună ziua tuturor", 17);
    }

    private void checkRecognitionResult(int resultCode, String transcript, int expectedSigns) throws Exception {
        try (var ignored = new MediaTestAccount()) {
            checkAuthenticatedRecognitionResult(resultCode, transcript, expectedSigns);
        }
    }

    private void checkAuthenticatedRecognitionResult(int resultCode, String transcript, int expectedSigns)
            throws Exception {
        var instrumentation = InstrumentationRegistry.getInstrumentation();

        instrumentation
                .getUiAutomation()
                .grantRuntimePermission(
                        instrumentation.getTargetContext().getPackageName(), Manifest.permission.RECORD_AUDIO);

        var data =
                new Intent()
                        .putStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS, new ArrayList<>(List.of(transcript)));
        var intent =
                new Intent(Intent.ACTION_MAIN)
                        .setClass(instrumentation.getTargetContext(), VoiceToSignSectionActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        Activity activity = instrumentation.startActivitySync(intent);

        RecyclerView list = activity.findViewById(R.id.voiceToSignRecyclerView);

        var laidOut = new CountDownLatch(expectedSigns > 0 ? 1 : 0);

        ViewTreeObserver.OnGlobalLayoutListener layoutListener =
                () -> {
                    if (requireNonNull(list.getAdapter()).getItemCount() == expectedSigns
                            && list.getChildCount() > 0
                            && list.getWidth() > 0
                            && list.getHeight() > 0
                            && !list.isLayoutRequested()) {
                        laidOut.countDown();
                    }
                };

        var monitor =
                instrumentation.addMonitor(
                        new IntentFilter(RecognizerIntent.ACTION_RECOGNIZE_SPEECH),
                        new Instrumentation.ActivityResult(resultCode, data),
                        true);

        try {
            instrumentation.runOnMainSync(() -> {
                list.getViewTreeObserver().addOnGlobalLayoutListener(layoutListener);

                var resources = activity.getResources();

                assertEquals(0, resources.getIdentifier("textInput", "id", activity.getPackageName()));
                assertEquals(0, resources.getIdentifier("confirmButton", "id", activity.getPackageName()));

                activity.findViewById(R.id.voiceRecordingButton).performClick();
            });
            instrumentation.waitForIdleSync();

            assertEquals(1, monitor.getHits());
            assertTrue("The signs grid should finish laying out", laidOut.await(10, TimeUnit.SECONDS));

            instrumentation.runOnMainSync(() -> {
                if (expectedSigns > 0)
                    assertEquals(
                            transcript,
                            ((TextView) activity.findViewById(R.id.categoryNameCardText)).getText().toString());

                assertEquals(expectedSigns, requireNonNull(list.getAdapter()).getItemCount());
                assertEquals(
                        expectedSigns > 0 ? View.VISIBLE : View.GONE,
                        activity.findViewById(R.id.outputContent).getVisibility());

                var grid = (GridLayoutManager) requireNonNull(list.getLayoutManager());

                assertFalse(grid.canScrollHorizontally());
                assertFalse(list.isNestedScrollingEnabled());

                if (expectedSigns == 17) {
                    var first = list.findViewHolderForAdapterPosition(0);
                    var last = list.findViewHolderForAdapterPosition(16);

                    assertNotNull(first);
                    assertNotNull(last);
                    assertTrue(last.itemView.getTop() > first.itemView.getTop());

                    for (int index = 0; index < list.getChildCount(); index++) {
                        var child = list.getChildAt(index);

                        assertTrue(child.getLeft() >= 0);
                        assertTrue(child.getRight() <= list.getWidth());
                    }

                    assertEquals(grid.getSpanCount(), grid.getSpanSizeLookup().getSpanSize(4));
                    assertEquals(grid.getSpanCount(), grid.getSpanSizeLookup().getSpanSize(9));
                }
            });

            if (expectedSigns == 17
                    && "true".equals(InstrumentationRegistry.getArguments().getString("captureSignPreview"))) {
                instrumentation.getUiAutomation().waitForIdle(500, 5000);

                var decor = activity.getWindow().getDecorView();

                Bitmap screenshot = Bitmap.createBitmap(decor.getWidth(), decor.getHeight(), Bitmap.Config.ARGB_8888);

                instrumentation.runOnMainSync(() -> decor.draw(new Canvas(screenshot)));

                try (var output =
                        new FileOutputStream(
                                new File(
                                        instrumentation.getTargetContext().getExternalFilesDir(null),
                                        "sign-grid-preview.png"))) {
                    assertTrue(screenshot.compress(Bitmap.CompressFormat.PNG, 100, output));
                } finally {
                    screenshot.recycle();
                }
            }
        } finally {
            instrumentation.runOnMainSync(() -> {
                list.getViewTreeObserver().removeOnGlobalLayoutListener(layoutListener);

                activity.finish();
            });
            instrumentation.removeMonitor(monitor);
        }
    }
}
