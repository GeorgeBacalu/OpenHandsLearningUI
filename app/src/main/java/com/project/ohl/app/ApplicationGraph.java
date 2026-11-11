package com.project.ohl.app;

import com.project.ohl.data.remote.RemoteLearningRepository;
import com.project.ohl.data.remote.RetrofitService;
import com.project.ohl.domain.repository.LearningRepository;

public final class ApplicationGraph {
    private ApplicationGraph() {}
    private static final class Holder {
        private static final LearningRepository LEARNING = new RemoteLearningRepository(RetrofitService.getRetrofit());
    }
    public static com.project.ohl.domain.repository.RecordingsRepository recordings(android.content.Context context) {
        return new com.project.ohl.data.local.RoomRecordingsRepository(
                com.project.ohl.data.local.RecordingDatabase.get(context).recordings());
    }
    public static LearningRepository learning() { return Holder.LEARNING; }
}
