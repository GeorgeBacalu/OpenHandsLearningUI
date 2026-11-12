package com.project.ohl;

import android.app.Activity;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import com.project.ohl.domain.model.Section;
import com.project.ohl.presentation.CategoryNavigation;
import com.project.ohl.presentation.SectionNavigation;
import com.project.ohl.presentation.expressions_section.ExpressionsActivity;
import com.project.ohl.presentation.video_section.VideoSectionActivity;
import com.project.ohl.presentation.voice_to_sign_section.VoiceToSignSectionActivity;
import java.util.Map;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
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

    @Test public void renamedSectionsKeepTheirDedicatedScreensAndSelectedUuids() {
        Map<UUID, Class<? extends Activity>> screens = Map.of(
                UUID.fromString("20a6880c-2e37-32e3-8e45-07a17e99a000"), VideoSectionActivity.class,
                UUID.fromString("07329929-eb4b-3793-8b2d-f6364d711a79"), VoiceToSignSectionActivity.class,
                UUID.fromString("1deb5200-b06d-3977-9426-e2ea373195f9"), ExpressionsActivity.class);
        var context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        screens.forEach((id, screen) -> {
            var section = new Section(id, "Original name", 0, 0);
            var original = SectionNavigation.intent(context, section);
            section.setName("Translated and renamed");
            var renamed = SectionNavigation.intent(context, section);
            assertEquals(screen.getName(), renamed.getComponent().getClassName());
            assertEquals(original.getComponent(), renamed.getComponent());
            assertEquals(id.toString(), renamed.getStringExtra(CategoryNavigation.SECTION_ID));
        });
    }

    @Test public void newSectionsUseTheirOwnUuidInTheCategoryBrowser() {
        var context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        UUID id = UUID.randomUUID();
        var intent = SectionNavigation.intent(context, new Section(id, "Voice to Sign Section", 0, 0));
        assertEquals(VideoSectionActivity.class.getName(), intent.getComponent().getClassName());
        assertEquals(id.toString(), intent.getStringExtra(CategoryNavigation.SECTION_ID));
    }
}
