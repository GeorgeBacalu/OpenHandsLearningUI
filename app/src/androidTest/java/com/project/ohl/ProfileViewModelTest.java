package com.project.ohl;

import androidx.lifecycle.ViewModelStore;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import com.project.ohl.domain.model.Registration;
import com.project.ohl.domain.model.User;
import com.project.ohl.domain.repository.AuthenticationRepository;
import com.project.ohl.domain.repository.Request;
import com.project.ohl.domain.repository.Result;
import com.project.ohl.presentation.ProfileViewModel;
import java.util.function.Consumer;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class ProfileViewModelTest {
    private static final class Repository implements AuthenticationRepository {
        Consumer<Result<User>> profile;
        Consumer<Result<Void>> action;
        int loads, mutations;
        boolean cancelled;
        @Override public Request<User> current() {
            return callback -> { loads++; profile = callback; return () -> cancelled = true; };
        }
        private Request<Void> mutation() {
            return callback -> { mutations++; action = callback; return () -> cancelled = true; };
        }
        @Override public Request<Void> changePassword(String oldPassword, String newPassword) { return mutation(); }
        @Override public Request<Void> deleteAccount(String password) { return mutation(); }
        @Override public Request<Void> logout() { return mutation(); }
        @Override public Request<User> login(String email, String password) { throw new AssertionError(); }
        @Override public Request<User> register(Registration registration) { throw new AssertionError(); }
        @Override public User account() { return null; }
    }

    @Test public void repeatedLoadsAndSubmissionsDoNotReplaceAnInFlightMutation() {
        var instrumentation = InstrumentationRegistry.getInstrumentation();
        var repository = new Repository();
        var model = new ProfileViewModel(repository);
        var user = new User();
        instrumentation.runOnMainSync(() -> {
            model.load(); model.load();
            assertEquals(1, repository.loads);
            repository.profile.accept(Result.success(user));
        });
        instrumentation.waitForIdleSync();
        instrumentation.runOnMainSync(() -> {
            model.submit(ProfileViewModel.Action.PASSWORD, "current", "new");
            model.submit(ProfileViewModel.Action.DELETE, "current", null);
            model.load();
            assertEquals(1, repository.loads);
            assertEquals(1, repository.mutations);
            assertFalse(repository.cancelled);
            assertEquals(ProfileViewModel.Action.PASSWORD, model.state().getValue().pending());
            repository.action.accept(Result.failure(new IllegalStateException("offline")));
        });
        instrumentation.waitForIdleSync();
        instrumentation.runOnMainSync(() -> {
            assertSame(user, model.state().getValue().user());
            assertNull(model.state().getValue().pending());
            assertFalse(model.state().getValue().completion().result().isSuccess());
            model.acknowledgeCompletion();
            model.submit(ProfileViewModel.Action.DELETE, "current", null);
            assertEquals(2, repository.mutations);
            var store = new ViewModelStore();
            store.put("profile", model);
            store.clear();
            assertTrue(repository.cancelled);
        });
    }
}
