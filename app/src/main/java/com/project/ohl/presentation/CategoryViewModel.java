package com.project.ohl.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.project.ohl.domain.model.Category;
import com.project.ohl.domain.repository.LearningRepository;
import com.project.ohl.presentation.state.AsyncRequest;
import com.project.ohl.presentation.state.UiState;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.Executor;

public final class CategoryViewModel extends ViewModel {
    private final LearningRepository repository;
    private final AsyncRequest<Category> category;
    private final AsyncRequest<Category> favorite;
    private UUID categoryId;
    private UUID userId;

    public CategoryViewModel(LearningRepository repository, Executor mainThread) {
        this.repository = repository;

        category = new AsyncRequest<>(mainThread);

        favorite = new AsyncRequest<>(mainThread);
    }

    public LiveData<UiState<Category>> state() {
        return category.state();
    }

    public LiveData<UiState<Category>> favoriteState() {
        return favorite.state();
    }

    public boolean changingFavorite() {
        return favorite.current().isLoading();
    }

    public void initialize(UUID selectedCategory, UUID selectedUser) {
        if (Objects.equals(categoryId, selectedCategory) && Objects.equals(userId, selectedUser)) return;

        categoryId = selectedCategory;

        userId = selectedUser;

        favorite.reset();

        reload();
    }

    public void reload() {
        if (categoryId != null) category.start(repository.getCategoryById(categoryId, userId));
    }

    public void toggleFavorite() {
        Category current = category.current().value();

        if (userId == null || current == null || changingFavorite()) return;

        favorite.start(
                Boolean.TRUE.equals(current.getFavorite())
                        ? repository.deleteFavorite(userId, categoryId)
                        : repository.saveFavorite(userId, categoryId),
                result -> {
                    if (result.isSuccess() && result.value() != null) category.replace(result.value());
                });
    }

    public void acknowledgeFavoriteError() {
        if (favorite.current().error() != null) favorite.reset();
    }

    @Override
    protected void onCleared() {
        category.close();

        favorite.close();
    }
}
