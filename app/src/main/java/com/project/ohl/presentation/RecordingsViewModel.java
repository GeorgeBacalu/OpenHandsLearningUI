package com.project.ohl.presentation;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.project.ohl.app.ApplicationGraph;
import com.project.ohl.domain.model.RecordingItem;
import com.project.ohl.domain.repository.Request;
import java.util.List;

public final class RecordingsViewModel extends AndroidViewModel {
    private final MutableLiveData<List<RecordingItem>> recordings = new MutableLiveData<>();
    private final Request.Cancellation subscription;

    public RecordingsViewModel(Application application) {
        super(application);
        subscription = ApplicationGraph.recordings(application).observe(recordings::setValue);
    }

    public LiveData<List<RecordingItem>> recordings() { return recordings; }
    @Override protected void onCleared() { subscription.cancel(); }
}
