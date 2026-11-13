package com.project.ohl.presentation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.project.ohl.domain.model.Section;
import com.project.ohl.domain.model.SectionType;
import com.project.ohl.presentation.video_section.VideoSectionActivity;
import com.project.ohl.presentation.voice_to_sign_section.VoiceToSignSectionActivity;

public final class SectionNavigation {
    private SectionNavigation() {}

    public static Class<? extends Activity> screen(SectionType type) {
        return switch (type) {
            case VIDEO -> VideoSectionActivity.class;
            case VOICE_TO_SIGN -> VoiceToSignSectionActivity.class;
        };
    }

    public static Intent intent(Context context, Section section) {
        return new Intent(context, screen(section.getType()));
    }
}
