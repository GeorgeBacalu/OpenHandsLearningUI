package com.project.ohl.domain.repository;

import com.project.ohl.domain.model.Registration;
import com.project.ohl.domain.model.User;

public interface AuthenticationRepository {
    Request<User> login(String email, String password);
    Request<User> register(Registration registration);
    Request<User> current();
    Request<Void> logout();
    Request<Void> changePassword(String currentPassword, String newPassword);
    Request<Void> deleteAccount(String currentPassword);
    User account();
}
