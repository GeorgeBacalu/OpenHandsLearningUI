package com.project.ohl.presentation;

import android.content.Context;
import androidx.annotation.ArrayRes;
import com.project.ohl.R;
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
    private MediaResources() {}

    public static String resourceName(String name) {
        if (name == null) return "";
        return Normalizer.normalize(name, Normalizer.Form.NFD).replaceAll("\\p{M}+", "")
                .toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9_]+", "_");
    }

    public static int drawable(Context context, String name) {
        return catalog(context, R.array.catalog_drawables).getOrDefault(resourceName(name), 0);
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
                    if (resourceId != 0) catalog.put(resources.getResourceEntryName(resourceId), resourceId);
                }
            } finally {
                entries.recycle();
            }
            return Map.copyOf(catalog);
        });
    }

    public static void bindSigns(Context context, List<TextSignPair> signs) {
        for (TextSignPair sign : signs) sign.setImageSource(drawable(context, sign.getMediaKey()));
    }

    public static void bindImages(Context context, List<Category> categories) {
        for (Category category : categories) category.setImageSource(drawable(context, category.getMediaKey() == null ? category.getName() : category.getMediaKey()));
    }
}
