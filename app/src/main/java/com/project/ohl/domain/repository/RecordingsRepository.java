package com.project.ohl.domain.repository;

import com.project.ohl.domain.model.RecordingItem;
import java.util.List;
import java.util.function.Consumer;

public interface RecordingsRepository {
    Request.Cancellation observe(Consumer<List<RecordingItem>> observer);
}
