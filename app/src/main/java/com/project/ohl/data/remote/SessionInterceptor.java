package com.project.ohl.data.remote;

import com.google.gson.JsonParser;
import com.project.ohl.BuildConfig;
import java.io.IOException;
import java.util.Set;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public final class SessionInterceptor implements Interceptor {
    private static final Set<String> SAFE = Set.of("GET", "HEAD", "OPTIONS", "TRACE");
    private final OkHttpClient csrfClient;
    private final SessionCookieJar cookies;

    public SessionInterceptor(OkHttpClient csrfClient, SessionCookieJar cookies) {
        this.csrfClient = csrfClient;
        this.cookies = cookies;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!SAFE.contains(request.method())) {
            try (Response csrf = csrfClient.newCall(new Request.Builder()
                    .url(BuildConfig.API_BASE_URL + "api/auth/csrf").build()).execute()) {
                if (!csrf.isSuccessful() || csrf.body() == null) throw new IOException("CSRF handshake failed");
                String token;
                try { token = JsonParser.parseString(csrf.body().string()).getAsJsonObject().get("token").getAsString(); }
                catch (RuntimeException exception) { throw new IOException("Invalid CSRF response", exception); }
                request = request.newBuilder().header("X-CSRF-TOKEN", token).build();
            }
        }
        Response response = chain.proceed(request);
        if (response.code() == 401) cookies.clear();
        return response;
    }
}
