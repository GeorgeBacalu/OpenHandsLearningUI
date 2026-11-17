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

public final class CatalogViewModel extends ViewModel {
    private final LearningRepository repository;
    private final SavedStateHandle savedState;
    private final AsyncRequest<List<Category>> categories;
    private UUID parentId;
    private boolean initialized;

    public CatalogViewModel(LearningRepository repository, SavedStateHandle savedState, Executor mainThread) {
        this.repository = repository;
        this.savedState = savedState;

        categories = new AsyncRequest<>(mainThread);
    }

    public LiveData<UiState<List<Category>>> state() {
        return categories.state();
    }

    public String query() {
        return savedState.get("catalog.query") == null ? "" : savedState.get("catalog.query");
    }

    public void initialize(UUID selectedParent) {
        if (initialized && Objects.equals(parentId, selectedParent)) return;

        initialized = true;

        parentId = selectedParent;

        search(query());
    }

    public void search(String query) {
        String value = query == null ? "" : query.strip();

        savedState.set("catalog.query", value);

        categories.start(repository.getCategoriesByParentIdAndName(parentId, value));
    }

    @Override
    protected void onCleared() {
        categories.close();
    }
}
