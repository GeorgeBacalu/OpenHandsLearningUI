package com.project.ohl.presentation;

import android.content.Context;
import com.project.ohl.domain.model.Category;
import java.text.Normalizer;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;

public final class MediaResources {
    private static final ConcurrentHashMap<String, Integer> DRAWABLES = new ConcurrentHashMap<>();
    private MediaResources() {}

    public static String resourceName(String name) {
        return Normalizer.normalize(name, Normalizer.Form.NFD).replaceAll("\\p{M}+", "")
                .toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9_]+", "_");
    }

    public static int drawable(Context context, String name) {
        String resource = resourceName(name);
        String key = context.getPackageName() + ":" + resource;
        return DRAWABLES.computeIfAbsent(key, ignored ->
                context.getResources().getIdentifier(resource, "drawable", context.getPackageName()));
    }

    public static void bindImages(Context context, List<Category> categories) {
        for (Category category : categories) category.setImageSource(drawable(context, category.getName()));
    }
}
