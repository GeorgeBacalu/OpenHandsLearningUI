package com.project.ohl.app;

import android.content.Context;
import com.project.ohl.data.local.RecordingDatabase;
import com.project.ohl.data.local.RoomRecordingsRepository;
import com.project.ohl.data.remote.RemoteLearningRepository;
import com.project.ohl.data.remote.RetrofitService;
import com.project.ohl.domain.repository.LearningRepository;
import com.project.ohl.domain.repository.RecordingsRepository;

public final class ApplicationGraph {
    private ApplicationGraph() {}
    private static final class Holder {
        private static final LearningRepository LEARNING = new RemoteLearningRepository(RetrofitService.getRetrofit());
    }
    public static RecordingsRepository recordings(Context context) {
        return new RoomRecordingsRepository(
                RecordingDatabase.get(context).recordings());
    }
    public static LearningRepository learning() { return Holder.LEARNING; }
}
