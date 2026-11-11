package com.project.ohl.domain.repository;

import java.util.function.Consumer;

@FunctionalInterface
public interface Request<T> {
    Cancellation start(Consumer<Result<T>> callback);

    @FunctionalInterface
    interface Cancellation {
        void cancel();
    }
}
