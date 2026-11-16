package com.project.ohl.presentation.state;

import com.project.ohl.domain.repository.Request;
import java.util.concurrent.Executor;

public final class DebouncedAction implements AutoCloseable {
    private final DelayScheduler scheduler;
    private final Executor mainThread;
    private Request.Cancellation pending;
    private long generation;
    private boolean closed;

    public DebouncedAction(DelayScheduler scheduler, Executor mainThread) {
        this.scheduler = scheduler;
        this.mainThread = mainThread;
    }

    public void submit(Runnable action) {
        if (closed) return;
        cancel();
        long requested = generation;
        pending = scheduler.schedule(() -> mainThread.execute(() -> {
            if (!closed && generation == requested) action.run();
        }));
    }

    public void cancel() {
        generation++;
        if (pending != null) { pending.cancel(); pending = null; }
    }

    @Override public void close() { closed = true; cancel(); }
}
