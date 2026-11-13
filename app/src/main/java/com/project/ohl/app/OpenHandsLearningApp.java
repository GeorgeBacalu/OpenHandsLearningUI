package com.project.ohl.app;

import android.app.Application;
import com.project.ohl.data.remote.RetrofitService;

public final class OpenHandsLearningApp extends Application {
    @Override public void onCreate() {
        super.onCreate();
        RetrofitService.initialize(this);
    }
}
