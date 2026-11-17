package com.project.ohl.presentation.state;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.project.ohl.domain.repository.Request;
import com.project.ohl.domain.repository.Result;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

public final class AsyncRequest<T> implements AutoCloseable {
    private final MutableLiveData<UiState<T>> state = new MutableLiveData<>(UiState.idle());
    private final Executor mainThread;
    private Request.Cancellation pending;
    private long generation;
    private boolean closed;

    public AsyncRequest(Executor mainThread) {
        this.mainThread = mainThread;
    }

    public LiveData<UiState<T>> state() {
        return state;
    }

    public UiState<T> current() {
        return state.getValue();
    }

    public void start(Request<T> operation) {
        start(operation, result -> {});
    }

    public void start(Request<T> operation, Consumer<Result<T>> completion) {
        if (closed) return;

        cancel();

        long requested = generation;

        T previous = current().value();

        state.setValue(UiState.loading(previous));

        pending =
                operation.start(result ->
                        mainThread.execute(() -> {
                            if (closed || requested != generation) return;

                            state.setValue(
                                    result.isSuccess()
                                            ? UiState.success(result.value())
                                            : UiState.failure(previous, result.error()));

                            completion.accept(result);
                        }));
    }

    public void replace(T value) {
        cancel();

        state.setValue(UiState.success(value));
    }

    public void reset() {
        cancel();

        state.setValue(UiState.idle());
    }

    public void cancel() {
        generation++;

        if (pending != null) {
            pending.cancel();

            pending = null;
        }
    }

    @Override
    public void close() {
        closed = true;

        cancel();
    }
}
