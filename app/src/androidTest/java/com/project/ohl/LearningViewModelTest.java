package com.project.ohl;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.ViewModelStore;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import com.project.ohl.domain.model.Category;
import com.project.ohl.domain.repository.LearningRepository;
import com.project.ohl.domain.repository.Request;
import com.project.ohl.domain.repository.Result;
import com.project.ohl.presentation.LearningViewModel;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class LearningViewModelTest {
    private static final class Owner implements LifecycleOwner {
        final LifecycleRegistry lifecycle = new LifecycleRegistry(this);
        @Override public Lifecycle getLifecycle() { return lifecycle; }
    }

    @Test public void cancelsReplacedRequestsAndIgnoresTheirQueuedResults() {
        var instrumentation = InstrumentationRegistry.getInstrumentation();
        var callbacks = new ArrayList<Consumer<Result<Category>>>();
        var cancelled = new ArrayList<AtomicBoolean>();
        LearningRepository repository = (LearningRepository) Proxy.newProxyInstance(
                LearningRepository.class.getClassLoader(), new Class<?>[]{LearningRepository.class},
                (proxy, method, args) -> (Request<Category>) callback -> {
                    callbacks.add(callback);
                    var flag = new AtomicBoolean();
                    cancelled.add(flag);
                    return () -> flag.set(true);
                });
        var received = new ArrayList<Integer>();
        var model = new LearningViewModel(repository);
        var store = new ViewModelStore();
        var owner = new Owner();
        instrumentation.runOnMainSync(() -> {
            store.put("learning", model);
            owner.lifecycle.setCurrentState(Lifecycle.State.STARTED);
            model.<Category>load(owner, "search", r -> r.getCategoryById(1), result -> received.add(result.value().getId()));
            callbacks.get(0).accept(Result.success(new Category(1, "Old")));
            model.<Category>load(owner, "search", r -> r.getCategoryById(2), result -> received.add(result.value().getId()));
            callbacks.get(1).accept(Result.success(new Category(2, "Current")));
        });
        instrumentation.waitForIdleSync();
        assertTrue(cancelled.get(0).get());
        assertEquals(List.of(2), received);
        instrumentation.runOnMainSync(() -> {
            owner.lifecycle.setCurrentState(Lifecycle.State.DESTROYED);
            store.clear();
        });
        assertTrue(cancelled.get(1).get());
    }
}
