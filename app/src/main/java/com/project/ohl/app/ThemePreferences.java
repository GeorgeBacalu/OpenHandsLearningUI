package com.project.ohl.app;

import android.content.Context;
import android.content.res.Configuration;
import androidx.appcompat.app.AppCompatDelegate;

public final class ThemePreferences {
    private static final String PREFERENCES = "appearance";
    private static final String NIGHT_MODE = "night_mode";

    private ThemePreferences() {}

    public static void applySavedTheme(Context context) {
        int mode = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
                .getInt(NIGHT_MODE, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        AppCompatDelegate.setDefaultNightMode(mode);
    }

    public static boolean isDarkTheme(Context context) {
        return (context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK)
                == Configuration.UI_MODE_NIGHT_YES;
    }

    public static void setDarkTheme(Context context, boolean dark) {
        int mode = dark ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO;
        context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE).edit()
                .putInt(NIGHT_MODE, mode).apply();
        AppCompatDelegate.setDefaultNightMode(mode);
    }
}
