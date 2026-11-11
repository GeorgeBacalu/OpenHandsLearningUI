package com.project.ohl.data.remote;

import com.project.ohl.BuildConfig;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class RetrofitService {
    private RetrofitService() {}

    private static final class Holder {
        private static final Retrofit INSTANCE = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_BASE_URL)
                .client(new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
                        .readTimeout(20, TimeUnit.SECONDS).callTimeout(30, TimeUnit.SECONDS).build())
                .addConverterFactory(GsonConverterFactory.create(JsonCodec.create())).build();
    }

    public static Retrofit getRetrofit() { return Holder.INSTANCE; }
}
