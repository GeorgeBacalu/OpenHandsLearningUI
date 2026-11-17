package com.project.ohl.data.remote;

import androidx.annotation.NonNull;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Response;

public final class JwtInterceptor implements Interceptor {
    private final JwtTokenStore tokens;
    private final Runnable authenticationExpired;

    public JwtInterceptor(JwtTokenStore tokens, Runnable authenticationExpired) {
        this.tokens = tokens;
        this.authenticationExpired = authenticationExpired;
    }

    @Override
    public @NonNull Response intercept(@NonNull Chain chain) throws IOException {
        var request = chain.request();

        if (!tokens.sameOrigin(request.url())) return chain.proceed(request);

        String token = tokens.token();
        String path = request.url().encodedPath();

        boolean authenticating = path.equals("/api/auth/login") || path.equals("/api/auth/register");

        if (token != null && !authenticating)
            request = request.newBuilder().header("Authorization", "Bearer " + token).build();

        Response response = chain.proceed(request);

        if (response.code() == 401 && !authenticating && token != null && token.equals(tokens.token())) {
            tokens.clear();

            authenticationExpired.run();
        }

        return response;
    }
}
