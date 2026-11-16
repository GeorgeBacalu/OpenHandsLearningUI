package com.project.ohl.presentation.state;

import com.project.ohl.domain.repository.Request;

@FunctionalInterface
public interface DelayScheduler {
    Request.Cancellation schedule(Runnable action);
}
