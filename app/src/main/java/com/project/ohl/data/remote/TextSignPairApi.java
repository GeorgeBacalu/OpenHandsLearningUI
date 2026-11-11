package com.project.ohl.data.remote;

import com.project.ohl.domain.model.TextSignPair;
import java.util.List;
import java.util.UUID;
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
   Call<TextSignPair> getTextSignPairById(@Path("id") UUID id);

   @POST("/api/letter-sign-pairs")
   Call<TextSignPair> saveTextSignPair(@Body TextSignPair textSignPair);

   @PUT("/api/letter-sign-pairs/{id}")
   Call<TextSignPair> updateTextSignPairById(@Body TextSignPair textSignPair, @Path("id") UUID id);

   @DELETE("/api/letter-sign-pairs/{id}")
   Call<Void> deleteTextSignPairById(@Path("id") UUID id);
}
