package com.project.ohl.presentation;

import android.app.Activity;
import com.project.ohl.presentation.video_section.VideoSectionActivity;
import com.project.ohl.presentation.voice_to_sign_section.VoiceToSignSectionActivity;

public enum HomeFeature {
    VIDEO, VOICE_TO_SIGN;

    public Class<? extends Activity> screen() {
        return switch (this) {
            case VIDEO -> VideoSectionActivity.class;
            case VOICE_TO_SIGN -> VoiceToSignSectionActivity.class;
        };
    }
}
