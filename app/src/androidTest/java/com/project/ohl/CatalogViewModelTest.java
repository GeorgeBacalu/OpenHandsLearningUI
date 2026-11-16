package com.project.ohl;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModelStore;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import com.project.ohl.domain.model.Category;
import com.project.ohl.presentation.CatalogViewModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class CatalogViewModelTest {
    private static final class Owner implements LifecycleOwner {
        final LifecycleRegistry lifecycle = new LifecycleRegistry(this);
        @Override public Lifecycle getLifecycle() { return lifecycle; }
    }

    @Test public void ignoresAlreadyQueuedResultsFromCancelledSearchesAndAfterClearing() {
        InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> {
            var repository = new ControlledLearningRepository();
            var queued = new ArrayList<Runnable>();
            var model = new CatalogViewModel(repository.repository, new SavedStateHandle(), queued::add);
            var store = new ViewModelStore(); store.put("catalog", model);
            model.initialize(UUID.randomUUID());
            repository.calls.get(0).succeed(List.of(new Category(UUID.randomUUID(), "Old")));
            model.search("current");
            var current = new Category(UUID.randomUUID(), "Current");
            repository.calls.get(1).succeed(List.of(current));
            queued.forEach(Runnable::run); queued.clear();
            assertTrue(repository.calls.get(0).cancelled);
            assertEquals(List.of(current), model.state().getValue().value());
            store.clear();
            assertTrue(repository.calls.get(1).cancelled);
            repository.calls.get(1).succeed(List.of(new Category(UUID.randomUUID(), "Late")));
            queued.forEach(Runnable::run);
            assertEquals(List.of(current), model.state().getValue().value());
        });
    }

    @Test public void recreatedUiObservesTheSamePendingRequestAndItsResult() {
        InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> {
            var repository = new ControlledLearningRepository();
            var model = new CatalogViewModel(repository.repository, new SavedStateHandle(), Runnable::run);
            var parent = UUID.randomUUID();
            var first = new Owner(); first.lifecycle.setCurrentState(Lifecycle.State.STARTED);
            var firstResults = new ArrayList<Object>();
            model.state().observe(first, state -> { if (state.isSuccess()) firstResults.add(state.value()); });
            model.initialize(parent);
            first.lifecycle.setCurrentState(Lifecycle.State.DESTROYED);
            var recreated = new Owner(); recreated.lifecycle.setCurrentState(Lifecycle.State.STARTED);
            var recreatedResults = new ArrayList<Object>();
            model.state().observe(recreated, state -> { if (state.isSuccess()) recreatedResults.add(state.value()); });
            model.initialize(parent);
            assertEquals(1, repository.calls.size());
            assertFalse(repository.calls.get(0).cancelled);
            var result = List.of(new Category(UUID.randomUUID(), "Letter"));
            repository.calls.get(0).succeed(result);
            assertTrue(firstResults.isEmpty());
            assertEquals(List.of(result), recreatedResults);
            recreated.lifecycle.setCurrentState(Lifecycle.State.DESTROYED);
            var store = new ViewModelStore(); store.put("catalog", model); store.clear();
        });
    }

    @Test public void restoresTheSubmittedQueryAndPreservesTheSelectedParent() {
        InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> {
            var repository = new ControlledLearningRepository();
            var saved = new SavedStateHandle(Map.of("catalog.query", "saved query"));
            var model = new CatalogViewModel(repository.repository, saved, Runnable::run);
            UUID parent = UUID.randomUUID();
            model.initialize(parent);
            assertEquals(parent, repository.calls.get(0).arguments[0]);
            assertEquals("saved query", repository.calls.get(0).arguments[1]);
            model.search("  new query  ");
            assertEquals("new query", saved.get("catalog.query"));
            assertEquals(parent, repository.calls.get(1).arguments[0]);
            assertEquals("new query", repository.calls.get(1).arguments[1]);
            var store = new ViewModelStore(); store.put("catalog", model); store.clear();
        });
    }
}
