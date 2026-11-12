package com.project.ohl.presentation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.project.ohl.domain.model.Section;
import com.project.ohl.presentation.expressions_section.ExpressionsActivity;
import com.project.ohl.presentation.video_section.VideoSectionActivity;
import com.project.ohl.presentation.voice_to_sign_section.VoiceToSignSectionActivity;
import java.util.Map;
import java.util.UUID;

public final class SectionNavigation {
    private static final Map<UUID, Class<? extends Activity>> SCREENS = Map.of(
            UUID.fromString("20a6880c-2e37-32e3-8e45-07a17e99a000"), VideoSectionActivity.class,
            UUID.fromString("07329929-eb4b-3793-8b2d-f6364d711a79"), VoiceToSignSectionActivity.class,
            UUID.fromString("1deb5200-b06d-3977-9426-e2ea373195f9"), ExpressionsActivity.class);

    private SectionNavigation() {}

    public static Class<? extends Activity> screen(UUID sectionId) {
        return SCREENS.getOrDefault(sectionId, VideoSectionActivity.class);
    }

    public static Intent intent(Context context, Section section) {
        return new Intent(context, screen(section.getId()))
                .putExtra(CategoryNavigation.SECTION_ID, section.getId().toString());
    }
}
