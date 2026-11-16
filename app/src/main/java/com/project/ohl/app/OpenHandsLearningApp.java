package com.project.ohl.app;

import android.app.Application;
import android.content.Intent;
import com.project.ohl.presentation.AuthenticatedActivity;
import com.project.ohl.data.remote.RetrofitService;

public final class OpenHandsLearningApp extends Application {
    @Override public void onCreate() {
        super.onCreate();
        ThemePreferences.applySavedTheme(this);
        RetrofitService.initialize(this, () -> sendBroadcast(new Intent(AuthenticatedActivity.AUTHENTICATION_EXPIRED)
                .setPackage(getPackageName())));
    }
}
