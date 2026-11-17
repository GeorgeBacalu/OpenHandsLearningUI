package com.project.ohl;

import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModelStore;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import com.project.ohl.domain.model.City;
import com.project.ohl.presentation.SchoolDirectoryViewModel;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class SchoolDirectoryViewModelTest {
    @Test
    public void parentChangesInvalidateChildSelectionsAndInFlightSearches() {
        InstrumentationRegistry.getInstrumentation()
                .runOnMainSync(() -> {
                    var repository = new ControlledLearningRepository();
                    var saved = new SavedStateHandle();
                    var model =
                            new SchoolDirectoryViewModel(
                                    repository.repository, saved, Runnable::run, action -> () -> {});
                    var oldCounty = UUID.randomUUID();
                    var newCounty = UUID.randomUUID();

                    model.selectCounty(oldCounty);
                    model.selectCity(UUID.randomUUID());
                    model.selectSchool(UUID.randomUUID());
                    model.searchCities("old");
                    model.searchSchools("old school");
                    model.selectCounty(newCounty);

                    assertNull(model.cityId());
                    assertNull(model.schoolId());
                    assertTrue(repository.calls.get(0).cancelled);
                    assertTrue(repository.calls.get(1).cancelled);

                    repository.calls.get(0).succeed(List.of(new City()));

                    assertEquals(List.of(), requireNonNull(model.cities().getValue()).value());

                    model.searchCities("new");

                    assertEquals(newCounty, repository.calls.get(2).arguments[0]);
                    assertEquals("new", repository.calls.get(2).arguments[1]);

                    var restored =
                            new SchoolDirectoryViewModel(
                                    repository.repository, saved, Runnable::run, action -> () -> {});

                    assertEquals(newCounty, restored.countyId());
                    assertNull(restored.cityId());

                    var store = new ViewModelStore();

                    store.put("directory", model);
                    store.put("restored", restored);
                    store.clear();
                });
    }

    @Test
    public void debounceKeepsOnlyTheLatestQueryAndCannotRunAfterAParentChange() {
        InstrumentationRegistry.getInstrumentation()
                .runOnMainSync(() -> {
                    var repository = new ControlledLearningRepository();
                    var delayed = new ArrayList<Runnable>();
                    var saved = new SavedStateHandle();
                    var model =
                            new SchoolDirectoryViewModel(
                                    repository.repository,
                                    saved,
                                    Runnable::run,
                                    action -> {
                                        delayed.add(action);

                                        return () -> {};
                                    });

                    UUID county = UUID.randomUUID();

                    model.selectCounty(county);
                    model.editCityQuery("old");
                    model.editCityQuery("latest");

                    delayed.get(0).run();

                    assertTrue(repository.calls.isEmpty());

                    delayed.get(1).run();

                    assertEquals(1, repository.calls.size());
                    assertEquals("latest", repository.calls.get(0).arguments[1]);

                    model.editCityQuery("superseded");
                    model.selectCounty(UUID.randomUUID());

                    delayed.get(2).run();

                    assertEquals(1, repository.calls.size());
                    assertTrue(repository.calls.get(0).cancelled);

                    var store = new ViewModelStore();

                    store.put("directory", model);
                    store.clear();
                });
    }
}
