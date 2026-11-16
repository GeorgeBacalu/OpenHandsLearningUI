package com.project.ohl;

import android.view.View;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.project.ohl.app.AppViewModels;
import com.project.ohl.presentation.AuthenticationViewModel;
import com.project.ohl.presentation.CatalogViewModel;
import com.project.ohl.presentation.SchoolDirectoryViewModel;
import com.project.ohl.presentation.user.UserActivity;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class ViewModelFactoryTest {
    @Test public void registrationRecreationRetainsInjectedViewModelsAndDirectorySelection() {
        var directory = new AtomicReference<SchoolDirectoryViewModel>();
        var authentication = new AtomicReference<AuthenticationViewModel>();
        var catalog = new AtomicReference<CatalogViewModel>();
        UUID selectedCounty = UUID.randomUUID();
        try (var scenario = ActivityScenario.launch(UserActivity.class)) {
            scenario.onActivity(activity -> {
                directory.set(AppViewModels.get(activity, SchoolDirectoryViewModel.class));
                authentication.set(AppViewModels.get(activity, AuthenticationViewModel.class));
                catalog.set(AppViewModels.get(activity, CatalogViewModel.class));
                directory.get().selectCounty(selectedCounty);
            });
            scenario.recreate();
            scenario.onActivity(activity -> {
                assertSame(directory.get(), AppViewModels.get(activity, SchoolDirectoryViewModel.class));
                assertSame(authentication.get(), AppViewModels.get(activity, AuthenticationViewModel.class));
                assertSame(catalog.get(), AppViewModels.get(activity, CatalogViewModel.class));
                assertEquals(selectedCounty, directory.get().countyId());
                View cities = activity.findViewById(R.id.registration_city);
                assertTrue(cities.isEnabled());
            });
        }
    }
}
