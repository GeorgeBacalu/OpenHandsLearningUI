package com.project.ohl.data.remote;

import androidx.annotation.NonNull;
import com.project.ohl.domain.model.Registration;
import com.project.ohl.domain.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface AuthApi {
    record Credentials(String email, String password) {
        @Override
        public @NonNull String toString() {
            return "Credentials[email=" + email + "]";
        }
    }

    record AuthenticationResponse(String accessToken, String tokenType, long expiresIn, User user) {
        @Override
        public @NonNull String toString() {
            return "AuthenticationResponse";
        }
    }

    @POST("/api/auth/login")
    Call<AuthenticationResponse> login(@Body Credentials credentials);

    @POST("/api/auth/register")
    Call<AuthenticationResponse> register(@Body Registration registration);

    @GET("/api/auth/me")
    Call<User> current();

    @POST("/api/auth/logout")
    Call<Void> logout();

    record PasswordChange(String currentPassword, String newPassword) {
        @Override
        public @NonNull String toString() {
            return "PasswordChange";
        }
    }

    record AccountDeletion(String currentPassword) {
        @Override
        public @NonNull String toString() {
            return "AccountDeletion";
        }
    }

    @PUT("/api/auth/password")
    Call<Void> changePassword(@Body PasswordChange change);

    @HTTP(method = "DELETE", path = "/api/auth/me", hasBody = true)
    Call<Void> deleteAccount(@Body AccountDeletion deletion);
}
