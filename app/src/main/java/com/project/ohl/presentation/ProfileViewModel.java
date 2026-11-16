package com.project.ohl.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.project.ohl.app.ApplicationGraph;
import com.project.ohl.domain.model.User;
import com.project.ohl.domain.repository.Request;
import com.project.ohl.domain.repository.Result;
import com.project.ohl.domain.repository.AuthenticationRepository;

public final class ProfileViewModel extends ViewModel {
    public enum Action { PASSWORD, DELETE, LOGOUT }
    public record Completion(Action action, Result<Void> result) {}
    public record State(User user, boolean loading, Action pending, Throwable error, Completion completion) {}
    private final MutableLiveData<State> state = new MutableLiveData<>(new State(null, false, null, null, null));
    private final AuthenticationRepository repository;
    private Request.Cancellation request;

    public ProfileViewModel() { this(ApplicationGraph.authentication()); }
    public ProfileViewModel(AuthenticationRepository repository) { this.repository = repository; }

    public LiveData<State> state() { return state; }

    public void load() {
        var current = state.getValue();
        if (current.loading() || current.pending() != null || current.user() != null) return;
        state.setValue(new State(null, true, null, null, null));
        request = repository.current().start(result ->
                state.postValue(new State(result.value(), false, null, result.error(), null)));
    }

    public void submit(Action action, String password, String newPassword) {
        var current = state.getValue();
        if (current.pending() != null || current.user() == null) return;
        state.setValue(new State(current.user(), false, action, null, null));
        Request<Void> operation = switch (action) {
            case PASSWORD -> repository.changePassword(password, newPassword);
            case DELETE -> repository.deleteAccount(password);
            case LOGOUT -> repository.logout();
        };
        request = operation.start(result -> state.postValue(
                new State(current.user(), false, null, null, new Completion(action, result))));
    }

    public void acknowledgeCompletion() {
        var current = state.getValue();
        state.setValue(new State(current.user(), current.loading(), current.pending(), current.error(), null));
    }

    @Override protected void onCleared() {
        if (request != null) request.cancel();
    }
}
