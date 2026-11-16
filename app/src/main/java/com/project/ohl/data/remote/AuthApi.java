package com.project.ohl.data.remote;

import com.project.ohl.domain.model.Registration;
import com.project.ohl.domain.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface AuthApi {
    record Credentials(String email, String password) {
        @Override public String toString() { return "Credentials[email=" + email + "]"; }
    }
    record AuthenticationResponse(String accessToken, String tokenType, long expiresIn, User user) {
        @Override public String toString() { return "AuthenticationResponse"; }
    }
    @POST("/api/auth/login") Call<AuthenticationResponse> login(@Body Credentials credentials);
    @POST("/api/auth/register") Call<AuthenticationResponse> register(@Body Registration registration);
    @GET("/api/auth/me") Call<User> current();
    @POST("/api/auth/logout") Call<Void> logout();
}
