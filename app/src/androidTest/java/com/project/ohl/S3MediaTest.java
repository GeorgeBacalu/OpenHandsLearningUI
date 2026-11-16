package com.project.ohl;

import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import static org.junit.Assume.assumeTrue;

/** Explicit public-S3 audit; no account, credentials, or fallback can mask a failed URL. */
@RunWith(AndroidJUnit4.class)
public class S3MediaTest {
    @Test public void publicWebpImagesMatchTheBundledArtwork() throws Exception {
        enabled();
        image("categories/alfabet/a.webp", R.drawable.a);
        image("voice/voice_a_breve.webp", R.drawable.voice_a_breve);
        image("categories/obiecte/magazin/produse/ardei.webp", R.drawable.ardei);
    }

    @Test public void androidDecodesAndSeeksThePublicMp4WithOriginalDimensionsAndDuration() throws Exception {
        enabled();
        var context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        var remote = new MediaMetadataRetriever();
        var local = new MediaMetadataRetriever();
        try {
            remote.setDataSource(BuildConfig.MEDIA_BASE_URL + "categories/alfabet/a.mp4", Map.of());
            local.setDataSource(context, android.net.Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.a));
            for (int field : new int[]{MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH,
                    MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT, MediaMetadataRetriever.METADATA_KEY_DURATION}) {
                assertEquals(local.extractMetadata(field), remote.extractMetadata(field));
            }
            long duration = Long.parseLong(remote.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
            var frame = remote.getFrameAtTime(duration * 500, MediaMetadataRetriever.OPTION_CLOSEST);
            assertNotNull("Public MP4 has a decodable frame at its midpoint", frame);
            frame.recycle();
        } finally {
            remote.release();
            local.release();
        }
    }

    private void image(String key, int resource) throws Exception {
        var connection = (HttpURLConnection) URI.create(BuildConfig.MEDIA_BASE_URL + key).toURL().openConnection();
        connection.setConnectTimeout(15000);
        connection.setReadTimeout(30000);
        var resources = InstrumentationRegistry.getInstrumentation().getTargetContext().getResources();
        try {
            assertEquals(key, 200, connection.getResponseCode());
            assertEquals("image/webp", connection.getContentType());
            try (var input = connection.getInputStream(); var bundled = resources.openRawResource(resource)) {
                var remote = BitmapFactory.decodeStream(input);
                var local = BitmapFactory.decodeStream(bundled);
                assertNotNull(key, remote);
                assertNotNull(key, local);
                try { assertTrue("Remote and bundled pixels match: " + key, remote.sameAs(local)); }
                finally { remote.recycle(); local.recycle(); }
            }
        } finally { connection.disconnect(); }
    }

    private void enabled() {
        assumeTrue("true".equals(InstrumentationRegistry.getArguments().getString("auditS3Media")));
    }
}
