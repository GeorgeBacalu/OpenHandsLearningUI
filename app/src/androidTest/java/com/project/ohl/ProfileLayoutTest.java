package com.project.ohl;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.project.ohl.app.ThemePreferences;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class ProfileLayoutTest {
    @Test
    public void profileRendersInBothThemesAndWithLargeText() {
        render(false, 1f, "day");
        render(true, 1f, "night");
        render(false, 1.6f, "large-text");
    }

    private void render(boolean night, float fontScale, String suffix) {
        var instrumentation = InstrumentationRegistry.getInstrumentation();

        instrumentation.runOnMainSync(() -> {
            var target = instrumentation.getTargetContext();
            var configuration = new Configuration(target.getResources().getConfiguration());

            configuration.uiMode =
                    (configuration.uiMode & ~Configuration.UI_MODE_NIGHT_MASK)
                            | (night ? Configuration.UI_MODE_NIGHT_YES : Configuration.UI_MODE_NIGHT_NO);

            configuration.fontScale = fontScale;

            var context =
                    new ContextThemeWrapper(
                            target.createConfigurationContext(configuration),
                            R.style.Theme_OpenHandsLearning_Authentication);

            View root = LayoutInflater.from(context).inflate(R.layout.activity_profile, null);

            root.setBackgroundColor(context.getColor(R.color.auth_background));

            root.findViewById(R.id.profile_content).setVisibility(View.VISIBLE);

            SwitchMaterial themeSwitch = root.findViewById(R.id.profile_dark_theme);

            assertNotNull("Theme toggle has a visible thumb", themeSwitch.getThumbDrawable());
            assertNotNull("Theme toggle has a visible track", themeSwitch.getTrackDrawable());
            assertEquals("Switch reflects the displayed theme", night, ThemePreferences.isDarkTheme(context));

            themeSwitch.setChecked(ThemePreferences.isDarkTheme(context));

            text(root, R.id.profile_initials, "AM");
            text(root, R.id.profile_display_name, "Ana Maria Popescu");
            text(root, R.id.profile_name, "Ana Maria Popescu");
            text(root, R.id.profile_email, "ana.popescu@example.com");
            text(root, R.id.profile_phone, "0712 345 678");
            text(root, R.id.profile_school, "Școala Gimnazială Mihai Eminescu, Cluj-Napoca, Cluj");

            float density = context.getResources().getDisplayMetrics().density;

            int width = Math.round(360 * density), height = Math.round(800 * density);

            root.measure(
                    View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY));
            root.layout(0, 0, width, height);

            for (int id :
                    new int[] {
                        R.id.profile_change_password,
                        R.id.profile_delete_account,
                        R.id.profile_logout,
                        R.id.profile_dark_theme
                    }) {
                View action = root.findViewById(id);

                assertTrue("Account actions remain touch accessible", action.getMeasuredHeight() >= 48 * density);
                assertTrue("Account actions fit the content width", action.getMeasuredWidth() <= width - 48 * density);
            }

            for (int id :
                    new int[] {
                        R.id.profile_display_name, R.id.profile_school, R.id.profile_email, R.id.profile_dark_theme
                    }) {
                TextView value = root.findViewById(id);

                assertTrue(
                        "Profile text must be fully visible",
                        value.getLayout().getHeight()
                                <= value.getMeasuredHeight()
                                        - value.getCompoundPaddingTop()
                                        - value.getCompoundPaddingBottom());
            }

            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

            root.draw(new Canvas(bitmap));

            try (var output =
                    new FileOutputStream(
                            new File(target.getExternalFilesDir(null), "profile-preview-" + suffix + ".png"))) {
                assertTrue(bitmap.compress(Bitmap.CompressFormat.PNG, 100, output));
            } catch (IOException error) {
                throw new AssertionError(error);
            } finally {
                bitmap.recycle();
            }
        });
    }

    private static void text(View root, int id, String value) {
        ((TextView) root.findViewById(id)).setText(value);
    }
}
