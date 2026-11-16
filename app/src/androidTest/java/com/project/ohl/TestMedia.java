package com.project.ohl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import androidx.test.platform.app.InstrumentationRegistry;
import com.project.ohl.BuildConfig;
import com.project.ohl.presentation.MediaResources;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

final class TestMedia {
    private static final Map<String, Bitmap> IMAGES = new HashMap<>();
    private static Bitmap videoFrame;
    private static final Map<String, String> IMAGE_PATHS = Map.ofEntries(
            Map.entry("membrii_familiei", "categories/persoane/membrii_familiei/main.webp"),
            Map.entry("acesta_este_colegul_meu", "categories/expresii/acesta_este_colegul_meu/main.webp"),
            Map.entry("bunic_bunica", "categories/persoane/membrii_familiei/bunic_bunica/main.webp"),
            Map.entry("ardei", "categories/obiecte/magazin/produse/ardei/main.webp"));

    private TestMedia() {}

    static String url(String name) {
        String image = MediaResources.imageUrl(name);
        if (image != null) return image;
        String key = MediaResources.resourceName(name);
        String path = IMAGE_PATHS.get(key);
        return path == null ? alphabetPhoto(key) : BuildConfig.MEDIA_BASE_URL + path;
    }

    static String alphabetPhoto(String name) {
        return BuildConfig.MEDIA_BASE_URL + "categories/alfabet/" + MediaResources.resourceName(name) + "/main.webp";
    }

    static String video(String name) { return url(name).replace(".webp", ".mp4"); }

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
            try (var metadata = new MediaMetadataRetriever()) {
                metadata.setDataSource(video("acesta_este_colegul_meu"), Map.of());
                videoFrame = metadata.getFrameAtTime(1_000_000);
                if (videoFrame == null) throw new AssertionError("S3 video fixture has no frame");
            } catch (IOException error) {
                throw new AssertionError("Could not read the S3 video fixture", error);
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
