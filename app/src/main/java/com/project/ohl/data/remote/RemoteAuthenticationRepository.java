package com.project.ohl.data.remote;

import com.project.ohl.domain.model.Registration;
import com.project.ohl.domain.model.User;
import com.project.ohl.domain.repository.AuthenticationException;
import com.project.ohl.domain.repository.AuthenticationRepository;
import com.project.ohl.domain.repository.Request;
import com.project.ohl.domain.repository.Result;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public final class RemoteAuthenticationRepository implements AuthenticationRepository {
    private final AuthApi api;
    private final SessionCookieJar cookies;
    private volatile User account;

    public RemoteAuthenticationRepository(Retrofit retrofit, SessionCookieJar cookies) {
        api = retrofit.create(AuthApi.class);
        this.cookies = cookies;
    }

    @Override public User account() { return cookies.hasSession() ? account : null; }
    @Override public Request<User> login(String email, String password) { return authenticate(api.login(new AuthApi.Credentials(email, password))); }
    @Override public Request<User> register(Registration registration) { return authenticate(api.register(registration)); }
    @Override public Request<User> current() { return authenticate(api.current()); }

    private Request<User> authenticate(Call<User> prototype) {
        return callback -> {
            var call = prototype.clone();
            call.enqueue(new Callback<>() {
                @Override public void onResponse(Call<User> request, Response<User> response) {
                    if (request.isCanceled()) return;
                    if (response.isSuccessful() && response.body() != null) {
                        account = response.body();
                        callback.accept(Result.success(account));
                    } else {
                        if (response.code() == 401) { account = null; cookies.clear(); }
                        if (response.errorBody() != null) response.errorBody().close();
                        callback.accept(Result.failure(new AuthenticationException(response.code())));
                    }
                }
                @Override public void onFailure(Call<User> request, Throwable error) {
                    if (!request.isCanceled()) callback.accept(Result.failure(error));
                }
            });
            return call::cancel;
        };
    }

    @Override public Request<Void> logout() {
        return callback -> {
            var call = api.logout();
            call.enqueue(new Callback<>() {
                private void clear() { account = null; cookies.clear(); }
                @Override public void onResponse(Call<Void> request, Response<Void> response) {
                    clear();
                    if (response.errorBody() != null) response.errorBody().close();
                    callback.accept(Result.success(null));
                }
                @Override public void onFailure(Call<Void> request, Throwable error) {
                    clear();
                    if (!request.isCanceled()) callback.accept(Result.success(null));
                }
            });
            return () -> { call.cancel(); account = null; cookies.clear(); };
        };
    }
}
