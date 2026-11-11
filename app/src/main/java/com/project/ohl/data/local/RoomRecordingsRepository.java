package com.project.ohl.data.local;

import androidx.lifecycle.Observer;
import com.project.ohl.domain.model.RecordingItem;
import com.project.ohl.domain.repository.RecordingsRepository;
import com.project.ohl.domain.repository.Request;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public final class RoomRecordingsRepository implements RecordingsRepository {
    private final RecordingDao dao;
    public RoomRecordingsRepository(RecordingDao dao) { this.dao = dao; }

    @Override public Request.Cancellation observe(Consumer<List<RecordingItem>> consumer) {
        var source = dao.observeAll();
        Observer<List<RecordingEntity>> observer = entities -> {
            var items = new ArrayList<RecordingItem>(entities.size());
            for (var entity : entities) items.add(entity.toDomain());
            consumer.accept(items);
        };
        source.observeForever(observer);
        return () -> source.removeObserver(observer);
    }
}
