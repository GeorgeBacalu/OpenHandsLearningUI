package com.project.ohl.presentation;

import com.project.ohl.app.ApplicationGraph;
import java.util.UUID;

public final class UserSession {
    private UserSession() {}
    public static UUID id() {
        var account = ApplicationGraph.authentication().account();
        return account == null ? null : account.getId();
    }
}
