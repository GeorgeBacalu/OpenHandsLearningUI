package com.project.ohl;

import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import java.security.MessageDigest;
import java.util.Locale;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

@RunWith(AndroidJUnit4.class)
public class S3MediaTest {
    @Test public void publicWebpImagesMatchTheVerifiedArtwork() throws Exception {
        enabled();
        image("a", "aedb5341c2f200cd86472680b80856d77b0f164d1fee155f982f2240a89e25fd", 132, 156);
        image("voice_a_breve", "d1b21dda8cbbf2ca899f8fc2ef310fe9144de11a23a2d717275a9007a6d45af3", 1254, 1254);
        image("ardei", "cfbfa72f95b7f4ac0b659ed0d3960bbc75475bfc2ca548910cdbc583b33646fa", 700, 700);
        var context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals(0, context.getResources().getIdentifier("a", "drawable", context.getPackageName()));
        assertEquals(0, context.getResources().getIdentifier("a", "raw", context.getPackageName()));
    }

    @Test public void androidDecodesAndSeeksThePublicMp4WithOriginalDimensionsAndDuration() throws Exception {
        enabled();
        try (var remote = new MediaMetadataRetriever()) {
            remote.setDataSource(TestMedia.video("a"), Map.of());
            assertEquals("1920", remote.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH));
            assertEquals("1080", remote.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT));
            long duration = Long.parseLong(requireNonNull(remote.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)));
            assertTrue(Math.abs(duration - 2600) <= 100);
            var frame = remote.getFrameAtTime(duration * 500, MediaMetadataRetriever.OPTION_CLOSEST);
            assertNotNull("Public MP4 has a decodable midpoint frame", frame);
            frame.recycle();
        }
    }

    private void image(String name, String expectedHash, int width, int height) throws Exception {
        byte[] bytes = TestMedia.download(TestMedia.url(name));
        var hex = new StringBuilder();
        for (byte value : MessageDigest.getInstance("SHA-256").digest(bytes))
            hex.append(String.format(Locale.ROOT, "%02x", value & 255));
        assertEquals("Unchanged S3 artwork: " + name, expectedHash, hex.toString());
        var image = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        assertNotNull(image);
        try { assertEquals(width, image.getWidth()); assertEquals(height, image.getHeight()); }
        finally { image.recycle(); }
    }

    private void enabled() {
        assumeTrue("true".equals(InstrumentationRegistry.getArguments().getString("auditS3Media")));
    }
}
