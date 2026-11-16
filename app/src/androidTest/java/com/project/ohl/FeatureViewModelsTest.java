package com.project.ohl;

import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelStore;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import com.project.ohl.domain.model.Category;
import com.project.ohl.domain.model.Registration;
import com.project.ohl.domain.model.User;
import com.project.ohl.domain.repository.AuthenticationRepository;
import com.project.ohl.domain.repository.Request;
import com.project.ohl.domain.repository.Result;
import com.project.ohl.presentation.AuthenticationViewModel;
import com.project.ohl.presentation.CategoryViewModel;
import com.project.ohl.presentation.FavoritesViewModel;
import com.project.ohl.presentation.SessionViewModel;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class FeatureViewModelsTest {
    private static final class Authentication implements AuthenticationRepository {
        int requests;
        boolean cancelled;
        Consumer<Result<User>> callback;
        private Request<User> request() {
            return consumer -> { requests++; callback = consumer; return () -> cancelled = true; };
        }
        @Override public Request<User> current() { return request(); }
        @Override public Request<User> login(String email, String password) { return request(); }
        @Override public Request<User> register(Registration registration) { return request(); }
        @Override public User account() { return null; }
        @Override public Request<Void> logout() { throw new AssertionError(); }
        @Override public Request<Void> changePassword(String oldPassword, String newPassword) { throw new AssertionError(); }
        @Override public Request<Void> deleteAccount(String password) { throw new AssertionError(); }
    }

    private static void clear(ViewModel model) {
        var store = new ViewModelStore(); store.put("model", model); store.clear();
    }

    @Test public void authenticationIgnoresDuplicateSubmissionsAndLateResultsAfterClearing() {
        InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> {
            var repository = new Authentication();
            var model = new AuthenticationViewModel(repository, Runnable::run);
            model.login("first@example.test", "password");
            model.login("second@example.test", "other password");
            assertEquals(1, repository.requests);
            assertTrue(model.state().getValue().isLoading());
            clear(model);
            assertTrue(repository.cancelled);
            repository.callback.accept(Result.success(new User()));
            assertFalse(model.state().getValue().isSuccess());
        });
    }

    @Test public void favoriteChangesUpdateRetainedCategoryStateWithoutReloadingTheVideo() {
        InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> {
            var repository = new ControlledLearningRepository();
            var model = new CategoryViewModel(repository.repository, Runnable::run);
            UUID categoryId = UUID.randomUUID(), userId = UUID.randomUUID();
            model.initialize(categoryId, userId);
            var category = new Category(categoryId, "Letter"); category.setFavorite(false);
            repository.calls.get(0).succeed(category);
            model.toggleFavorite(); model.toggleFavorite();
            assertEquals(2, repository.calls.size());
            assertEquals("saveFavorite", repository.calls.get(1).method);
            assertEquals(userId, repository.calls.get(1).arguments[0]);
            var saved = new Category(categoryId, "Letter"); saved.setFavorite(true);
            repository.calls.get(1).succeed(saved);
            model.initialize(categoryId, userId);
            assertEquals(2, repository.calls.size());
            assertTrue(model.state().getValue().value().getFavorite());
            assertFalse(model.changingFavorite());
            clear(model);
        });
    }

    @Test public void removalsRefreshTheCurrentFavoritesQueryAndStayScopedToTheAccount() {
        InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> {
            var repository = new ControlledLearningRepository();
            var model = new FavoritesViewModel(repository.repository, new SavedStateHandle(), Runnable::run);
            UUID userId = UUID.randomUUID(), categoryId = UUID.randomUUID();
            model.initialize(userId); model.search("old query");
            repository.calls.get(0).succeed(List.of(new Category(categoryId, "Letter")));
            model.remove(categoryId); model.remove(categoryId);
            model.search("new query");
            assertEquals(2, repository.calls.size());
            repository.calls.get(1).succeed(new Category(categoryId, "Letter"));
            assertEquals(3, repository.calls.size());
            assertEquals(userId, repository.calls.get(2).arguments[0]);
            assertEquals("new query", repository.calls.get(2).arguments[1]);
            clear(model);
        });
    }

    @Test public void revalidatingASessionCancelsThePreviousSectionRequest() {
        InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> {
            var authentication = new Authentication();
            var learning = new ControlledLearningRepository();
            var model = new SessionViewModel(authentication, learning.repository, Runnable::run);
            model.refresh(); model.refresh();
            assertEquals(1, authentication.requests);
            assertTrue(learning.calls.isEmpty());
            authentication.callback.accept(Result.success(new User()));
            assertEquals(1, learning.calls.size());
            model.refresh();
            assertTrue(learning.calls.get(0).cancelled);
            learning.calls.get(0).succeed(List.of());
            assertFalse(model.sections().getValue().isSuccess());
            authentication.callback.accept(Result.failure(new IllegalStateException("offline")));
            assertNotNull(model.state().getValue().error());
            assertFalse(model.sections().getValue().isSuccess());
            clear(model);
        });
    }
    @Test public void switchingAccountsDiscardsPendingFavoritesAndTheirResults() {
        InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> {
            var repository = new ControlledLearningRepository();
            var model = new FavoritesViewModel(repository.repository, new SavedStateHandle(), Runnable::run);
            model.initialize(UUID.randomUUID()); model.search("old account query");
            var nextUser = UUID.randomUUID();
            model.initialize(nextUser);
            assertTrue(repository.calls.get(0).cancelled);
            repository.calls.get(0).succeed(List.of(new Category(UUID.randomUUID(), "Old account")));
            assertFalse(model.state().getValue().isSuccess());
            assertEquals("", model.query());
            model.refresh();
            assertEquals(nextUser, repository.calls.get(1).arguments[0]);
            clear(model);
        });
    }

}
