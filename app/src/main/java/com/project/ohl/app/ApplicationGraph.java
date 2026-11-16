package com.project.ohl.app;

import android.content.Context;
import com.project.ohl.data.local.RecordingDatabase;
import com.project.ohl.data.local.RoomRecordingsRepository;
import com.project.ohl.data.remote.RemoteLearningRepository;
import com.project.ohl.data.remote.RetrofitService;
import com.project.ohl.domain.repository.LearningRepository;
import com.project.ohl.domain.repository.RecordingsRepository;
import com.project.ohl.domain.repository.AuthenticationRepository;
import com.project.ohl.data.remote.RemoteAuthenticationRepository;

public final class ApplicationGraph {
    private ApplicationGraph() {}
    private static final class Holder {
        private static final LearningRepository LEARNING = new RemoteLearningRepository(RetrofitService.getRetrofit());
        private static final AuthenticationRepository AUTHENTICATION = new RemoteAuthenticationRepository(
                RetrofitService.getRetrofit(), RetrofitService.cookies());
    }
    public static RecordingsRepository recordings(Context context) {
        return new RoomRecordingsRepository(
                RecordingDatabase.get(context).recordings());
    }
    public static LearningRepository learning() { return Holder.LEARNING; }
    public static AuthenticationRepository authentication() { return Holder.AUTHENTICATION; }
}
