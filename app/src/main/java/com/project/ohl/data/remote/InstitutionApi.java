package com.project.ohl.data.remote;

import com.project.ohl.domain.model.Institution;
import java.util.List;
import java.util.UUID;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface InstitutionApi {

   @GET("/api/institutions")
   Call<List<Institution>> getAllInstitutions();

   @GET("/api/institutions/{id}")
   Call<Institution> getInstitutionById(@Path("id") UUID id);

   @POST("/api/institutions")
   Call<Institution> saveInstitution(@Body Institution institution);

   @PUT("/api/institutions/{id}")
   Call<Institution> updateInstitutionById(@Body Institution institution, @Path("id") UUID id);

   @DELETE("/api/institutions/{id")
   Call<Void> deleteInstitutionById(@Path("id") UUID id);
}
