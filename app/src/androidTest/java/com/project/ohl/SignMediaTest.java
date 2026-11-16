package com.project.ohl;

import android.Manifest;
import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.speech.RecognizerIntent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import com.project.ohl.domain.model.SignText;
import com.project.ohl.presentation.MediaResources;
import com.project.ohl.presentation.voice_to_sign_section.VoiceToSignSectionActivity;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileOutputStream;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class SignMediaTest {
    @Test public void aAndABreveResolveDistinctPlayableVideosAndImages() throws Exception {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        int aVideo = MediaResources.raw(context, "a");
        int breveVideo = MediaResources.raw(context, "ă");
        assertTrue(aVideo != 0 && breveVideo != 0);
        assertNotEquals(aVideo, breveVideo);
        assertEquals(R.raw.a_breve, breveVideo);
        var a = new com.project.ohl.domain.model.Category(java.util.UUID.randomUUID(), "A");
        var breve = new com.project.ohl.domain.model.Category(java.util.UUID.randomUUID(), "Ă");
        a.setMediaKey("a");
        breve.setMediaKey("a_breve");
        MediaResources.bindImages(context, List.of(a, breve));
        assertEquals(Integer.valueOf(R.drawable.a), a.getImageSource());
        assertEquals(Integer.valueOf(R.drawable.a_breve), breve.getImageSource());
        var signs = SignText.translate("AĂ");
        MediaResources.bindSigns(context, signs);
        assertEquals(R.drawable.voice_a, signs.get(0).getImageSource());
        assertEquals(R.drawable.voice_a_breve, signs.get(1).getImageSource());
        assertNotEquals(a.getImageSource().intValue(), signs.get(0).getImageSource());
        assertNotEquals(breve.getImageSource().intValue(), signs.get(1).getImageSource());
        assertNotEquals(signs.get(0).getImageSource(), signs.get(1).getImageSource());
        for (int video : new int[]{aVideo, breveVideo}) {
            try (var metadata = new android.media.MediaMetadataRetriever()) {
                metadata.setDataSource(context, android.net.Uri.parse(
                        "android.resource://" + context.getPackageName() + "/" + video));
                assertTrue(Long.parseLong(metadata.extractMetadata(
                        android.media.MediaMetadataRetriever.METADATA_KEY_DURATION)) >= 2000);
                var frame = metadata.getFrameAtTime(1_000_000,
                        android.media.MediaMetadataRetriever.OPTION_CLOSEST);
                assertNotNull(frame);
                frame.recycle();
            }
        }
    }

    @Test public void resolvesEveryDictionaryEntryFromBundledResources() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        for (String key : SignText.dictionary().values()) assertTrue(MediaResources.voiceDrawable(context, key) != 0);
        assertEquals(R.drawable.a, context.getResources().getIdentifier("a", "drawable", context.getPackageName()));
        assertTrue(context.getResources().getIdentifier("a", "raw", context.getPackageName()) != 0);
        var signs = SignText.translate("aăâbcd efg h iîjklmnopqrsşștţțuvwxyz AĂÂÎȘȚ");
        MediaResources.bindSigns(context, signs);
        assertTrue(signs.stream().allMatch(sign -> sign.getImageSource() != 0));
    }

    @Test public void romanianDactylsUseDistinctImagesAndPlayableDemonstrations() throws Exception {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        var signs = SignText.translate("ÂÎȘȚ");
        var plain = SignText.translate("AIST");
        MediaResources.bindSigns(context, signs);
        MediaResources.bindSigns(context, plain);
        int[] expectedImages = {R.drawable.voice_a_circumflex, R.drawable.voice_i_circumflex,
                R.drawable.voice_s_comma, R.drawable.voice_t_comma};
        int[] categoryPhotos = {R.drawable.a_circumflex, R.drawable.i_circumflex,
                R.drawable.s_comma, R.drawable.t_comma};
        var videoIds = new java.util.HashSet<Integer>();
        for (int index = 0; index < signs.size(); index++) {
            var sign = signs.get(index);
            assertEquals(expectedImages[index], sign.getImageSource());
            assertNotEquals(plain.get(index).getImageSource(), sign.getImageSource());
            var category = new com.project.ohl.domain.model.Category(java.util.UUID.randomUUID(), sign.getLetter());
            category.setMediaKey(sign.getMediaKey());
            MediaResources.bindImages(context, List.of(category));
            assertEquals(Integer.valueOf(categoryPhotos[index]), category.getImageSource());
            assertNotEquals(category.getImageSource().intValue(), sign.getImageSource());
            int video = MediaResources.raw(context, "" + sign.getLetter());
            assertTrue(video != 0 && videoIds.add(video));
            assertEquals(video, MediaResources.raw(context, sign.getMediaKey()));
            try (var metadata = new android.media.MediaMetadataRetriever()) {
                metadata.setDataSource(context, android.net.Uri.parse(
                        "android.resource://" + context.getPackageName() + "/" + video));
                assertTrue(Long.parseLong(metadata.extractMetadata(
                        android.media.MediaMetadataRetriever.METADATA_KEY_DURATION)) >= 2500);
                var beginning = metadata.getFrameAtTime(0, android.media.MediaMetadataRetriever.OPTION_CLOSEST);
                var gesture = metadata.getFrameAtTime(1_000_000, android.media.MediaMetadataRetriever.OPTION_CLOSEST);
                assertNotNull(beginning);
                assertNotNull(gesture);
                assertFalse("The demonstration must contain movement", beginning.sameAs(gesture));
                beginning.recycle();
                gesture.recycle();
            }
        }
        assertNotEquals(MediaResources.raw(context, "t"), MediaResources.raw(context, "â"));
    }

    @Test public void voiceAndCategoryImagesCannotFallBackToEachOther() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals(0, MediaResources.drawable(context, "voice_a"));
        assertEquals(0, MediaResources.voiceDrawable(context, "alfabet"));
        var categoryImages = new java.util.HashSet<Integer>();
        var categoryCatalog = context.getResources().obtainTypedArray(R.array.catalog_drawables);
        var voiceCatalog = context.getResources().obtainTypedArray(R.array.catalog_voice_signs);
        try {
            for (int index = 0; index < categoryCatalog.length(); index++)
                categoryImages.add(categoryCatalog.getResourceId(index, 0));
            for (int index = 0; index < voiceCatalog.length(); index++) {
                int illustration = voiceCatalog.getResourceId(index, 0);
                assertTrue(illustration != 0);
                assertFalse(categoryImages.contains(illustration));
            }
        } finally {
            categoryCatalog.recycle();
            voiceCatalog.recycle();
        }
    }

    @Test public void voiceRecognitionDisplaysDistinctRomanianDactyls() throws Exception {
        checkRecognitionResult(Activity.RESULT_OK, "Â Î Ș Ț", 7);
    }

    @Test public void voiceRecognitionImmediatelyDisplaysSigns() throws Exception {
        checkRecognitionResult(Activity.RESULT_OK, "aĂ ș!", 4);
    }

    @Test public void cancelledRecognitionDoesNotDisplaySigns() throws Exception {
        checkRecognitionResult(Activity.RESULT_CANCELED, "aĂ ș!", 0);
    }

    @Test public void blankRecognitionDoesNotDisplaySigns() throws Exception {
        checkRecognitionResult(Activity.RESULT_OK, "   ", 0);
    }

    @Test public void longTranscriptWrapsIntoRowsWithoutHorizontalScrolling() throws Exception {
        checkRecognitionResult(Activity.RESULT_OK, "Bună ziua tuturor", 17);
    }

    private void checkRecognitionResult(int resultCode, String transcript, int expectedSigns) throws Exception {
        try (var account = new MediaTestAccount()) {
            checkAuthenticatedRecognitionResult(resultCode, transcript, expectedSigns);
        }
    }

    private void checkAuthenticatedRecognitionResult(int resultCode, String transcript, int expectedSigns) throws Exception {
        var instrumentation = InstrumentationRegistry.getInstrumentation();
        instrumentation.getUiAutomation().grantRuntimePermission(
                instrumentation.getTargetContext().getPackageName(), Manifest.permission.RECORD_AUDIO);
        var data = new Intent().putStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS,
                new ArrayList<>(List.of(transcript)));
        var intent = new Intent(Intent.ACTION_MAIN)
                .setClass(instrumentation.getTargetContext(), VoiceToSignSectionActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Activity activity = instrumentation.startActivitySync(intent);
        RecyclerView list = activity.findViewById(R.id.voiceToSignRecyclerView);
        var laidOut = new CountDownLatch(expectedSigns > 0 ? 1 : 0);
        ViewTreeObserver.OnGlobalLayoutListener layoutListener = () -> {
            if (list.getAdapter().getItemCount() == expectedSigns && list.getChildCount() > 0
                    && list.getWidth() > 0 && list.getHeight() > 0 && !list.isLayoutRequested()) {
                laidOut.countDown();
            }
        };
        var monitor = instrumentation.addMonitor(new IntentFilter(RecognizerIntent.ACTION_RECOGNIZE_SPEECH),
                new Instrumentation.ActivityResult(resultCode, data), true);
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
                if (expectedSigns > 0) assertEquals(transcript,
                        ((TextView) activity.findViewById(R.id.categoryNameCardText)).getText().toString());
                assertEquals(expectedSigns, list.getAdapter().getItemCount());
                assertEquals(expectedSigns > 0 ? View.VISIBLE : View.GONE,
                        activity.findViewById(R.id.outputContent).getVisibility());
                var grid = (GridLayoutManager) list.getLayoutManager();
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
            if (expectedSigns == 17 && "true".equals(InstrumentationRegistry.getArguments().getString("captureSignPreview"))) {
                instrumentation.getUiAutomation().waitForIdle(500, 5000);
                var decor = activity.getWindow().getDecorView();
                Bitmap screenshot = Bitmap.createBitmap(decor.getWidth(), decor.getHeight(), Bitmap.Config.ARGB_8888);
                instrumentation.runOnMainSync(() -> decor.draw(new Canvas(screenshot)));
                try (var output = new FileOutputStream(new File(
                        instrumentation.getTargetContext().getExternalFilesDir(null), "sign-grid-preview.png"))) {
                    assertTrue(screenshot.compress(Bitmap.CompressFormat.PNG, 100, output));
                } finally { screenshot.recycle(); }
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
