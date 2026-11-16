package com.project.ohl.data.remote;

import com.project.ohl.BuildConfig;
import android.content.Context;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class RetrofitService {
    private RetrofitService() {}

    private static Retrofit instance;
    private static SessionCookieJar cookies;

    public static synchronized void initialize(Context context) {
        if (instance != null) return;
        cookies = new SessionCookieJar(context.getApplicationContext());
        var csrfClient = new OkHttpClient.Builder().cookieJar(cookies).connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS).callTimeout(30, TimeUnit.SECONDS).build();
        instance = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_BASE_URL)
                .client(csrfClient.newBuilder().addInterceptor(new SessionInterceptor(csrfClient, cookies)).build())
                .addConverterFactory(GsonConverterFactory.create(JsonCodec.create())).build();
    }

    public static Retrofit getRetrofit() {
        if (instance == null) throw new IllegalStateException("Application graph is not initialized");
        return instance;
    }
    public static SessionCookieJar cookies() { return cookies; }
}
