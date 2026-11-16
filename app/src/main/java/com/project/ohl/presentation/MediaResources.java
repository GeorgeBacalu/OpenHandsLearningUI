package com.project.ohl.presentation;

import android.content.Context;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.ObjectKey;
import java.io.File;
import androidx.annotation.ArrayRes;
import com.project.ohl.R;
import com.project.ohl.BuildConfig;
import com.project.ohl.domain.model.Category;
import com.project.ohl.domain.model.TextSignPair;
import java.text.Normalizer;
import java.util.List;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class MediaResources {
    private static final ConcurrentHashMap<Integer, Map<String, Integer>> CATALOGS = new ConcurrentHashMap<>();
    private static volatile String apkRevision;
    private MediaResources() {}

    public static void loadImage(Context context, Category category, ImageView target) {
        int fallback = category.getImageSource() == null ? 0 : category.getImageSource();
        loadImage(context, category.getImageUrl(), fallback, target);
    }

    public static void loadImage(Context context, String url, int fallback, ImageView target) {
        if (url == null || url.isBlank()) {
            loadImage(context, fallback, target);
            return;
        }
        Glide.with(context).asBitmap().load(url).placeholder(fallback).error(fallback)
                .dontTransform().into(target);
    }

    public static String voiceUrl(String mediaKey) {
        String name = resourceName(mediaKey);
        return BuildConfig.MEDIA_BASE_URL + "voice/" + (name.equals("space") ? name : "voice_" + name) + ".webp";
    }

    public static void loadImage(Context context, int resourceId, ImageView target) {
        String revision = apkRevision;
        if (revision == null) {
            String apk = context.getApplicationInfo().sourceDir;
            revision = apk + ":" + new File(apk).lastModified();
            apkRevision = revision;
        }
        var configuration = context.getResources().getConfiguration();
        var signature = new ObjectKey(revision + ":" + configuration.uiMode + ":"
                + configuration.densityDpi + ":" + configuration.getLocales().toLanguageTags());
        // ImageView controls sizing: category artwork fills both axes without cropping.
        Glide.with(context).asBitmap().load(resourceId).signature(signature).dontTransform().into(target);
    }

    public static String resourceName(String name) {
        if (name == null) return "";
        // Romanian dactyls must remain distinct before generic accent removal.
        String canonical = Normalizer.normalize(name, Normalizer.Form.NFC).toLowerCase(Locale.ROOT);
        return switch (canonical) {
            case "ă" -> "a_breve";
            case "â" -> "a_circumflex";
            case "î" -> "i_circumflex";
            case "ș", "ş" -> "s_comma";
            case "ț", "ţ" -> "t_comma";
            default -> Normalizer.normalize(canonical, Normalizer.Form.NFD)
                    .replaceAll("\\p{M}+", "").replaceAll("[^a-z0-9_]+", "_");
        };
    }

    public static int drawable(Context context, String name) {
        return catalog(context, R.array.catalog_drawables).getOrDefault(resourceName(name), 0);
    }

    public static int voiceDrawable(Context context, String name) {
        return catalog(context, R.array.catalog_voice_signs).getOrDefault(resourceName(name), 0);
    }

    public static int raw(Context context, String name) {
        return catalog(context, R.array.catalog_videos).getOrDefault(resourceName(name), 0);
    }

    private static Map<String, Integer> catalog(Context context, @ArrayRes int catalogId) {
        return CATALOGS.computeIfAbsent(catalogId, ignored -> {
            var resources = context.getResources();
            var entries = resources.obtainTypedArray(catalogId);
            var catalog = new HashMap<String, Integer>();
            try {
                for (int index = 0; index < entries.length(); index++) {
                    int resourceId = entries.getResourceId(index, 0);
                    if (resourceId != 0) {
                        String key = resources.getResourceEntryName(resourceId);
                        // Illustration filenames are independent of category photos and video keys.
                        if (catalogId == R.array.catalog_voice_signs && key.startsWith("voice_"))
                            key = key.substring("voice_".length());
                        catalog.put(key, resourceId);
                    }
                }
            } finally {
                entries.recycle();
            }
            return Map.copyOf(catalog);
        });
    }

    public static void bindSigns(Context context, List<TextSignPair> signs) {
        for (TextSignPair sign : signs) sign.setImageSource(voiceDrawable(context, sign.getMediaKey()));
    }

    public static void bindImages(Context context, List<Category> categories) {
        for (Category category : categories) {
            String key = resourceName(category.getMediaKey() == null ? category.getName() : category.getMediaKey());
            category.setImageSource(drawable(context, key));
        }
    }
}
