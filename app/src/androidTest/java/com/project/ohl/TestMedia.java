package com.project.ohl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import androidx.test.platform.app.InstrumentationRegistry;
import com.project.ohl.presentation.MediaResources;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/** Fetches real S3 fixtures before detached layout checks run on the UI thread. */
final class TestMedia {
    private static final Map<String, Bitmap> IMAGES = new HashMap<>();
    private static Bitmap videoFrame;

    private TestMedia() {}

    static String url(String name) { return MediaResources.imageUrl(name); }

    static synchronized void preload() {
        for (String name : new String[]{"video_section", "voice_to_sign_section", "alfabet", "animale",
                "formule_de_adresare", "membrii_familiei", "emotii", "expressions_section",
                "acesta_este_colegul_meu", "bunic_bunica"}) {
            String url = url(name);
            if (IMAGES.containsKey(url)) continue;
            byte[] bytes = download(url);
            var options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
            int sample = 1;
            while (Math.max(options.outWidth, options.outHeight) / sample > 1600) sample *= 2;
            options.inJustDecodeBounds = false;
            options.inSampleSize = sample;
            var bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
            if (bitmap == null) throw new AssertionError("S3 image did not decode: " + url);
            bitmap.setDensity(Bitmap.DENSITY_NONE);
            IMAGES.put(url, bitmap);
        }
        if (videoFrame == null) {
            var metadata = new MediaMetadataRetriever();
            try {
                metadata.setDataSource(MediaResources.videoUrl("acesta_este_colegul_meu"), Map.of());
                videoFrame = metadata.getFrameAtTime(1_000_000);
                if (videoFrame == null) throw new AssertionError("S3 video fixture has no frame");
            } finally {
                try { metadata.release(); } catch (IOException error) { throw new AssertionError(error); }
            }
        }
    }

    static Drawable drawable(String url) {
        var bitmap = IMAGES.get(url);
        if (bitmap == null) throw new AssertionError("Preload S3 fixtures off the UI thread: " + url);
        return new BitmapDrawable(InstrumentationRegistry.getInstrumentation().getTargetContext().getResources(), bitmap);
    }

    static Bitmap videoFrame() { return videoFrame.copy(Bitmap.Config.ARGB_8888, false); }

    static byte[] download(String url) {
        IOException failure = null;
        for (int attempt = 0; attempt < 3; attempt++) {
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) URI.create(url).toURL().openConnection();
                connection.setConnectTimeout(15000);
                connection.setReadTimeout(30000);
                if (connection.getResponseCode() != 200) throw new IOException("S3 HTTP " + connection.getResponseCode());
                try (var input = connection.getInputStream(); var output = new ByteArrayOutputStream()) {
                    byte[] buffer = new byte[8192];
                    int count;
                    while ((count = input.read(buffer)) != -1) output.write(buffer, 0, count);
                    return output.toByteArray();
                }
            } catch (IOException error) { failure = error; }
            finally { if (connection != null) connection.disconnect(); }
        }
        throw new AssertionError("Could not fetch S3 fixture: " + url, failure);
    }
}
