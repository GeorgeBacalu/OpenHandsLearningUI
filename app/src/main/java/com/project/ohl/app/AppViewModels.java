package com.project.ohl.app;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.SavedStateHandleSupport;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;
import com.project.ohl.presentation.AuthenticationViewModel;
import com.project.ohl.presentation.CatalogViewModel;
import com.project.ohl.presentation.CategoryViewModel;
import com.project.ohl.presentation.FavoritesViewModel;
import com.project.ohl.presentation.ProfileViewModel;
import com.project.ohl.presentation.RecordingsViewModel;
import com.project.ohl.presentation.SchoolDirectoryViewModel;
import com.project.ohl.presentation.SessionViewModel;
import com.project.ohl.presentation.state.DelayScheduler;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public final class AppViewModels {
    private AppViewModels() {}
    private static final java.util.concurrent.ScheduledExecutorService SEARCH_TIMER = Executors.newSingleThreadScheduledExecutor(action -> {
        var thread = new Thread(action, "directory-search");
        thread.setDaemon(true);
        return thread;
    });
    private static final DelayScheduler SEARCH_DELAY = action -> {
        var pending = SEARCH_TIMER.schedule(action, 300, TimeUnit.MILLISECONDS);
        return () -> pending.cancel(false);
    };

    public static <T extends ViewModel> T get(FragmentActivity owner, Class<T> type) {
        return new ViewModelProvider(owner, factory(owner)).get(type);
    }
    public static <T extends ViewModel> T get(Fragment owner, Class<T> type) {
        return new ViewModelProvider(owner, factory(owner.requireContext())).get(type);
    }

    private static ViewModelProvider.Factory factory(Context context) {
        Context application = context.getApplicationContext();
        var mainThread = ContextCompat.getMainExecutor(application);
        return new ViewModelProvider.Factory() {
            @NonNull @Override public <T extends ViewModel> T create(@NonNull Class<T> type, @NonNull CreationExtras extras) {
                ViewModel model;
                if (type == CatalogViewModel.class) model = new CatalogViewModel(ApplicationGraph.learning(),
                        SavedStateHandleSupport.createSavedStateHandle(extras), mainThread);
                else if (type == FavoritesViewModel.class) model = new FavoritesViewModel(ApplicationGraph.learning(),
                        SavedStateHandleSupport.createSavedStateHandle(extras), mainThread);
                else if (type == SchoolDirectoryViewModel.class) model = new SchoolDirectoryViewModel(ApplicationGraph.learning(),
                        SavedStateHandleSupport.createSavedStateHandle(extras), mainThread, SEARCH_DELAY);
                else if (type == CategoryViewModel.class) model = new CategoryViewModel(ApplicationGraph.learning(), mainThread);
                else if (type == SessionViewModel.class) model = new SessionViewModel(ApplicationGraph.authentication(), ApplicationGraph.learning(), mainThread);
                else if (type == AuthenticationViewModel.class) model = new AuthenticationViewModel(ApplicationGraph.authentication(), mainThread);
                else if (type == ProfileViewModel.class) model = new ProfileViewModel(ApplicationGraph.authentication());
                else if (type == RecordingsViewModel.class) model = new RecordingsViewModel(ApplicationGraph.recordings(application));
                else throw new IllegalArgumentException("Unsupported ViewModel: " + type.getName());
                return type.cast(model);
            }
        };
    }
}
