package com.project.ohl.domain.model;

import java.util.UUID;
import org.jspecify.annotations.NonNull;

public record Registration(String name, String email, String password, String phone, UUID schoolId) {
    @Override public @NonNull String toString() { return "Registration[email=" + email + "]"; }
}
