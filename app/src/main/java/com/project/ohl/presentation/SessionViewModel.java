package com.project.ohl.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.project.ohl.domain.model.User;
import com.project.ohl.domain.repository.AuthenticationRepository;
import com.project.ohl.presentation.state.AsyncRequest;
import com.project.ohl.presentation.state.UiState;
import java.util.concurrent.Executor;

public final class SessionViewModel extends ViewModel {
    private final AuthenticationRepository authentication;
    private final AsyncRequest<User> account;

    public SessionViewModel(AuthenticationRepository authentication, Executor mainThread) {
        this.authentication = authentication;
        account = new AsyncRequest<>(mainThread);
    }

    public LiveData<UiState<User>> state() { return account.state(); }

    public void refresh() {
        if (account.current().isLoading()) return;
        account.start(authentication.current());
    }

    @Override protected void onCleared() { account.close(); }
}
