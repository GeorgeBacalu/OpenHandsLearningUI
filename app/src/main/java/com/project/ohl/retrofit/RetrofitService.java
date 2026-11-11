package com.project.ohl.retrofit;

import com.project.ohl.BuildConfig;
import com.google.gson.Gson;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

   private Retrofit retrofit;

   public RetrofitService() {
      initializeRetrofit();
   }

   private void initializeRetrofit() {
      retrofit = new Retrofit.Builder()
           .baseUrl(BuildConfig.API_BASE_URL)
           .addConverterFactory(GsonConverterFactory.create(new Gson()))
           .build();
   }

   public Retrofit getRetrofit() {
      return retrofit;
   }
}
