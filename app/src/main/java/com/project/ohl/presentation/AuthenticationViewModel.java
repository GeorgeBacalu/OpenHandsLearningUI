package com.project.ohl.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.project.ohl.domain.model.Registration;
import com.project.ohl.domain.model.User;
import com.project.ohl.domain.repository.AuthenticationRepository;
import com.project.ohl.presentation.state.AsyncRequest;
import com.project.ohl.presentation.state.UiState;
import java.util.concurrent.Executor;

public final class AuthenticationViewModel extends ViewModel {
    private final AuthenticationRepository repository;
    private final AsyncRequest<User> authentication;

    public AuthenticationViewModel(AuthenticationRepository repository, Executor mainThread) {
        this.repository = repository;
        authentication = new AsyncRequest<>(mainThread);
    }

    public LiveData<UiState<User>> state() { return authentication.state(); }
    public void login(String email, String password) {
        if (!authentication.current().isLoading()) authentication.start(repository.login(email, password));
    }
    public void register(Registration registration) {
        if (!authentication.current().isLoading()) authentication.start(repository.register(registration));
    }
    @Override protected void onCleared() { authentication.close(); }
}
