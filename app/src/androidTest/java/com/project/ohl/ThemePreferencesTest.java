package com.project.ohl;

import android.content.Context;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import com.project.ohl.app.ThemePreferences;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class ThemePreferencesTest {
    @Test public void explicitThemeSurvivesReloadAndDefaultsToSystem() {
        var instrumentation = InstrumentationRegistry.getInstrumentation();
        Context context = instrumentation.getContext();
        instrumentation.runOnMainSync(() -> {
            int originalMode = AppCompatDelegate.getDefaultNightMode();
            var preferences = context.getSharedPreferences("appearance", Context.MODE_PRIVATE);
            preferences.edit().clear().commit();
            try {
                ThemePreferences.applySavedTheme(context);
                assertEquals(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM, AppCompatDelegate.getDefaultNightMode());
                for (boolean dark : new boolean[]{true, false}) {
                    int expected = dark ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO;
                    ThemePreferences.setDarkTheme(context, dark);
                    assertEquals(expected, AppCompatDelegate.getDefaultNightMode());
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                    ThemePreferences.applySavedTheme(context);
                    assertEquals(expected, AppCompatDelegate.getDefaultNightMode());
                }
            } finally {
                preferences.edit().clear().commit();
                AppCompatDelegate.setDefaultNightMode(originalMode);
            }
        });
    }
}
