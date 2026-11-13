package com.project.ohl.domain.model;

import java.util.UUID;

public record Registration(String name, String email, String password, String phone, UUID schoolId) {
    @Override public String toString() { return "Registration[email=" + email + "]"; }
}
