package com.project.ohl;

import android.app.Activity;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import com.project.ohl.domain.model.Section;
import com.project.ohl.domain.model.SectionType;
import com.project.ohl.presentation.SectionNavigation;
import com.project.ohl.presentation.video_section.VideoSectionActivity;
import com.project.ohl.presentation.voice_to_sign_section.VoiceToSignSectionActivity;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;

@RunWith(AndroidJUnit4.class)
public class SectionRoutingTest {
    @Test public void retiredTextScreenIsNotDeclared() {
        var context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        var component = new ComponentName(context.getPackageName(),
                "com.project.ohl.presentation.text_to_sign_section.TextToSignSectionActivity");
        assertThrows(PackageManager.NameNotFoundException.class,
                () -> context.getPackageManager().getActivityInfo(component, 0));
        assertEquals(0, context.getResources().getIdentifier("nav_text_to_sign", "string", context.getPackageName()));
    }

    @Test public void renamedSectionsKeepTheirDedicatedScreensAndTypes() {
        Map<SectionType, Class<? extends Activity>> screens = Map.of(
                SectionType.VIDEO, VideoSectionActivity.class,
                SectionType.VOICE_TO_SIGN, VoiceToSignSectionActivity.class);
        var context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        screens.forEach((id, screen) -> {
            var section = new Section(id, "Original name", 0, 0);
            var original = SectionNavigation.intent(context, section);
            section.setName("Translated and renamed");
            var renamed = SectionNavigation.intent(context, section);
            assertEquals(screen.getName(), renamed.getComponent().getClassName());
            assertEquals(original.getComponent(), renamed.getComponent());
            assertFalse(renamed.hasExtra("sectionId"));
        });
    }

}
