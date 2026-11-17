package com.project.ohl.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import com.project.ohl.domain.model.Category;
import com.project.ohl.domain.repository.LearningRepository;
import com.project.ohl.presentation.state.AsyncRequest;
import com.project.ohl.presentation.state.UiState;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.Executor;

public final class FavoritesViewModel extends ViewModel {
    private final LearningRepository repository;
    private final SavedStateHandle savedState;
    private final AsyncRequest<List<Category>> categories;
    private final AsyncRequest<Category> removal;
    private UUID userId;

    public FavoritesViewModel(LearningRepository repository, SavedStateHandle savedState, Executor mainThread) {
        this.repository = repository;
        this.savedState = savedState;

        categories = new AsyncRequest<>(mainThread);

        removal = new AsyncRequest<>(mainThread);
    }

    public LiveData<UiState<List<Category>>> state() {
        return categories.state();
    }

    public LiveData<UiState<Category>> removalState() {
        return removal.state();
    }

    public String query() {
        return savedState.get("favorites.query") == null ? "" : savedState.get("favorites.query");
    }

    public void initialize(UUID id) {
        if (Objects.equals(userId, id)) return;

        if (userId != null) savedState.set("favorites.query", "");

        userId = id;

        categories.reset();

        removal.reset();
    }

    public void refresh() {
        if (!categories.current().isLoading()) search(query());
    }

    public void search(String query) {
        savedState.set("favorites.query", query == null ? "" : query.strip());

        if (userId != null && !removal.current().isLoading())
            categories.start(repository.getFavoritesByName(userId, query()));
    }

    public void remove(UUID categoryId) {
        if (userId == null || removal.current().isLoading()) return;

        removal.start(
                repository.deleteFavorite(userId, categoryId),
                result -> {
                    if (result.isSuccess()) search(query());
                });
    }

    public void acknowledgeRemovalError() {
        if (removal.current().error() != null) removal.reset();
    }

    @Override
    protected void onCleared() {
        categories.close();

        removal.close();
    }
}
