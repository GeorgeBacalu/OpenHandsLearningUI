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
    private final JwtTokenStore tokens;
    private volatile User account;

    public RemoteAuthenticationRepository(Retrofit retrofit, JwtTokenStore tokens) {
        api = retrofit.create(AuthApi.class);
        this.tokens = tokens;
    }

    @Override public User account() { return tokens.token() != null ? account : null; }
    @Override public Request<User> login(String email, String password) { return establish(api.login(new AuthApi.Credentials(email, password))); }
    @Override public Request<User> register(Registration registration) { return establish(api.register(registration)); }
    @Override public Request<User> current() {
        if (tokens.token() == null) return callback -> {
            account = null;
            callback.accept(Result.failure(new AuthenticationException(401)));
            return () -> {};
        };
        return authenticate(api.current());
    }

    private Request<User> establish(Call<AuthApi.AuthenticationResponse> prototype) {
        return callback -> {
            var call = prototype.clone();
            call.enqueue(new Callback<>() {
                @Override public void onResponse(Call<AuthApi.AuthenticationResponse> request, Response<AuthApi.AuthenticationResponse> response) {
                    if (request.isCanceled()) return;
                    var body = response.body();
                    if (response.isSuccessful() && body != null && body.user() != null
                            && body.accessToken() != null && !body.accessToken().isBlank() && "Bearer".equals(body.tokenType())) {
                        tokens.save(body.accessToken());
                        account = body.user();
                        callback.accept(Result.success(account));
                    } else {
                        if (response.errorBody() != null) response.errorBody().close();
                        callback.accept(Result.failure(new AuthenticationException(response.code())));
                    }
                }
                @Override public void onFailure(Call<AuthApi.AuthenticationResponse> request, Throwable error) {
                    if (!request.isCanceled()) callback.accept(Result.failure(error));
                }
            });
            return call::cancel;
        };
    }

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
                        if (response.code() == 401) { account = null; tokens.clear(); }
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

    @Override public Request<Void> changePassword(String currentPassword, String newPassword) {
        return accountAction(api.changePassword(new AuthApi.PasswordChange(currentPassword, newPassword)));
    }

    @Override public Request<Void> deleteAccount(String currentPassword) {
        return accountAction(api.deleteAccount(new AuthApi.AccountDeletion(currentPassword)));
    }

    private Request<Void> accountAction(Call<Void> prototype) {
        return callback -> {
            var call = prototype.clone();
            call.enqueue(new Callback<>() {
                @Override public void onResponse(Call<Void> request, Response<Void> response) {
                    if (request.isCanceled()) return;
                    if (response.isSuccessful()) {
                        account = null;
                        tokens.clear();
                        callback.accept(Result.success(null));
                    } else {
                        if (response.errorBody() != null) response.errorBody().close();
                        callback.accept(Result.failure(new AuthenticationException(response.code())));
                    }
                }
                @Override public void onFailure(Call<Void> request, Throwable error) {
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
                private void clear() { account = null; tokens.clear(); }
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
            return () -> { call.cancel(); account = null; tokens.clear(); };
        };
    }
}
