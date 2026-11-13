package com.project.ohl.presentation;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import com.project.ohl.app.ApplicationGraph;
import com.project.ohl.domain.model.User;
import com.project.ohl.domain.repository.AuthenticationRepository;
import com.project.ohl.domain.repository.Request;
import com.project.ohl.domain.repository.Result;
import java.util.function.Function;
import java.util.concurrent.atomic.AtomicBoolean;

public final class AuthenticationViewModel extends ViewModel {
    private Request.Cancellation pending;
    private Request.Cancellation authentication;
    private final MutableLiveData<Result<User>> authenticationResult = new MutableLiveData<>();
    private volatile boolean submitting;

    public boolean isSubmitting() { return submitting; }

    public void observeAuthentication(LifecycleOwner owner, Observer<Result<User>> observer) {
        authenticationResult.observe(owner, observer);
    }

    public void authenticate(Function<AuthenticationRepository, Request<User>> operation) {
        if (submitting) return;
        submitting = true;
        authenticationResult.setValue(null);
        authentication = operation.apply(ApplicationGraph.authentication()).start(result -> {
            submitting = false;
            authenticationResult.postValue(result);
        });
    }

    public static AuthenticationViewModel of(ViewModelStoreOwner owner) {
        return new ViewModelProvider(owner).get(AuthenticationViewModel.class);
    }

    public <T> void load(LifecycleOwner owner, Function<AuthenticationRepository, Request<T>> operation,
            Observer<Result<T>> observer) {
        if (pending != null) pending.cancel();
        var state = new MutableLiveData<Result<T>>();
        var active = new AtomicBoolean(true);
        state.observe(owner, result -> { if (active.get()) observer.onChanged(result); });
        var cancellation = operation.apply(ApplicationGraph.authentication()).start(state::postValue);
        pending = () -> { active.set(false); cancellation.cancel(); };
    }

    @Override protected void onCleared() {
        if (pending != null) pending.cancel();
        if (authentication != null) authentication.cancel();
    }
}
