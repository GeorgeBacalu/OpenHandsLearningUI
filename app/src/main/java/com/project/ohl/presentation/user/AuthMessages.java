package com.project.ohl.presentation.user;

import com.project.ohl.R;
import com.project.ohl.domain.repository.AuthenticationException;

final class AuthMessages {
    private AuthMessages() {}
    static int forError(Throwable error) {
        if (error instanceof AuthenticationException failure) {
            return switch (failure.status()) {
                case 401 -> R.string.invalid_credentials;
                case 409 -> R.string.email_already_registered;
                case 429 -> R.string.login_throttled;
                case 400 -> R.string.invalid_registration;
                default -> R.string.request_failed;
            };
        }
        return R.string.request_failed;
    }
}
