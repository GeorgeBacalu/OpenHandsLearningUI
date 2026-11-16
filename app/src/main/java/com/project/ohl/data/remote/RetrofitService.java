package com.project.ohl.data.remote;

import android.content.Context;
import com.project.ohl.BuildConfig;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class RetrofitService {
    private RetrofitService() {}

    private static Retrofit instance;
    private static JwtTokenStore tokens;

    public static synchronized void initialize(Context context, Runnable authenticationExpired) {
        if (instance != null) return;
        tokens = new JwtTokenStore(context.getApplicationContext());
        var client = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS).callTimeout(30, TimeUnit.SECONDS).build();
        instance = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_BASE_URL)
                .client(client.newBuilder().addInterceptor(new JwtInterceptor(tokens, authenticationExpired)).build())
                .addConverterFactory(GsonConverterFactory.create(JsonCodec.create())).build();
    }

    public static Retrofit getRetrofit() {
        if (instance == null) throw new IllegalStateException("Application graph is not initialized");
        return instance;
    }
    public static JwtTokenStore tokens() { return tokens; }
}
