package com.example.llt_project_separate.retrofit;

import com.example.llt_project_separate.general.standard_classes.TextSignPair;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TextSignPairApi {

   @GET("/api/letter-sign-pairs")
   Call<List<TextSignPair>> getAllTextSignPairs();

   @GET("/api/letter-sign-pairs/{id}")
   Call<TextSignPair> getTextSignPairById(@Path("id") Long id);

   @POST("/api/letter-sign-pairs")
   Call<TextSignPair> saveTextSignPair(@Body TextSignPair textSignPair);

   @PUT("/api/letter-sign-pairs/{id}")
   Call<TextSignPair> updateTextSignPairById(@Body TextSignPair textSignPair, @Path("id") Long id);

   @DELETE("/api/letter-sign-pairs/{id}")
   Call<Void> deleteTextSignPairById(@Path("id") Long id);
}
