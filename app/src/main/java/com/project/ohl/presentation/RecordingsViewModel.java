package com.project.ohl.presentation;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.project.ohl.domain.repository.RecordingsRepository;
import com.project.ohl.domain.model.RecordingItem;
import com.project.ohl.domain.repository.Request;
import java.util.List;

public final class RecordingsViewModel extends ViewModel {
    private final MutableLiveData<List<RecordingItem>> recordings = new MutableLiveData<>();
    private final Request.Cancellation subscription;

    public RecordingsViewModel(RecordingsRepository repository) {
        subscription = repository.observe(recordings::setValue);
    }

    public LiveData<List<RecordingItem>> recordings() { return recordings; }
    @Override protected void onCleared() { subscription.cancel(); }
}
