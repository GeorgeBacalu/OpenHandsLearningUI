package com.project.ohl.presentation;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.project.ohl.BuildConfig;
import com.project.ohl.domain.model.Category;
import java.text.Normalizer;
import java.util.Locale;

public final class MediaResources {
    private MediaResources() {}

    public static void loadImage(Context context, Category category, ImageView target) {
        loadImage(context, category.getImageUrl(), target);
    }

    public static void loadImage(Context context, String url, ImageView target) {
        var request = Glide.with(context).asBitmap().load(url).dontTransform().dontAnimate();
        var layout = target.getLayoutParams();
        if (layout != null && (layout.width == ViewGroup.LayoutParams.WRAP_CONTENT
                || layout.height == ViewGroup.LayoutParams.WRAP_CONTENT)) {
            request = request.override(Target.SIZE_ORIGINAL);
        }
        request.into(target);
    }

    public static String imageUrl(String name) {
        String key = MediaCatalog.IMAGES.get(resourceName(name));
        return key == null ? null : BuildConfig.MEDIA_BASE_URL + key;
    }

    public static String voiceUrl(String mediaKey) {
        String name = resourceName(mediaKey);
        return imageUrl(name.equals("space") ? name : "voice_" + name);
    }

    public static String resourceName(String name) {
        if (name == null) return "";
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

}
