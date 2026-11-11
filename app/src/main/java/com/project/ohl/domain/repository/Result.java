package com.project.ohl.domain.repository;

public final class Result<T> {
    private final T value;
    private final Throwable error;

    private Result(T value, Throwable error) {
        this.value = value;
        this.error = error;
    }

    public static <T> Result<T> success(T value) { return new Result<>(value, null); }
    public static <T> Result<T> failure(Throwable error) { return new Result<>(null, error); }
    public boolean isSuccess() { return error == null; }
    public T value() { return value; }
    public Throwable error() { return error; }
}
