package com.project.ohl.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.project.ohl.domain.model.Section;
import com.project.ohl.domain.model.User;
import com.project.ohl.domain.repository.AuthenticationRepository;
import com.project.ohl.domain.repository.LearningRepository;
import com.project.ohl.presentation.state.AsyncRequest;
import com.project.ohl.presentation.state.UiState;
import java.util.List;
import java.util.concurrent.Executor;

public final class SessionViewModel extends ViewModel {
    private final AuthenticationRepository authentication;
    private final LearningRepository learning;
    private final AsyncRequest<User> account;
    private final AsyncRequest<List<Section>> sections;

    public SessionViewModel(AuthenticationRepository authentication, LearningRepository learning, Executor mainThread) {
        this.authentication = authentication;
        this.learning = learning;
        account = new AsyncRequest<>(mainThread);
        sections = new AsyncRequest<>(mainThread);
    }

    public LiveData<UiState<User>> state() { return account.state(); }
    public LiveData<UiState<List<Section>>> sections() { return sections.state(); }

    public void refresh() {
        if (account.current().isLoading()) return;
        sections.reset();
        account.start(authentication.current(), result -> {
            if (result.isSuccess() && result.value() != null) sections.start(learning.getAllSections());
        });
    }

    @Override protected void onCleared() { account.close(); sections.close(); }
}
