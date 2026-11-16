package com.project.ohl.presentation.state;

public record UiState<T>(Status status, T value, Throwable error) {
    public enum Status { IDLE, LOADING, SUCCESS, ERROR }

    public static <T> UiState<T> idle() { return new UiState<>(Status.IDLE, null, null); }
    public static <T> UiState<T> loading(T previous) { return new UiState<>(Status.LOADING, previous, null); }
    public static <T> UiState<T> success(T value) { return new UiState<>(Status.SUCCESS, value, null); }
    public static <T> UiState<T> failure(T previous, Throwable error) { return new UiState<>(Status.ERROR, previous, error); }
    public boolean isLoading() { return status == Status.LOADING; }
    public boolean isSuccess() { return status == Status.SUCCESS; }
}
