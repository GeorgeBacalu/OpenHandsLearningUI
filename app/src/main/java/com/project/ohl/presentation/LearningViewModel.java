package com.project.ohl.presentation;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import com.project.ohl.app.ApplicationGraph;
import com.project.ohl.domain.repository.LearningRepository;
import com.project.ohl.domain.repository.Request;
import com.project.ohl.domain.repository.Result;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public final class LearningViewModel extends ViewModel {
    private final LearningRepository repository;
    private final Map<String, Request.Cancellation> requests = new HashMap<>();

    public LearningViewModel() { this(ApplicationGraph.learning()); }
    public LearningViewModel(LearningRepository repository) { this.repository = repository; }

    public static LearningViewModel of(ViewModelStoreOwner owner) {
        return new ViewModelProvider(owner).get(LearningViewModel.class);
    }

    public <T> void load(LifecycleOwner owner, String key,
            Function<LearningRepository, Request<T>> operation, Observer<Result<T>> observer) {
        var previous = requests.remove(key);
        if (previous != null) previous.cancel();
        var state = new MutableLiveData<Result<T>>();
        var active = new java.util.concurrent.atomic.AtomicBoolean(true);
        state.observe(owner, result -> { if (active.get()) observer.onChanged(result); });
        var cancellation = operation.apply(repository).start(state::postValue);
        requests.put(key, () -> { active.set(false); cancellation.cancel(); });
    }

    @Override protected void onCleared() {
        requests.values().forEach(Request.Cancellation::cancel);
        requests.clear();
    }
}
