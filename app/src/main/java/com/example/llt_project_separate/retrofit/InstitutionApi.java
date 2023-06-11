package com.example.llt_project_separate.retrofit;

import com.example.llt_project_separate.general.standard_classes.Institution;
import java.util.List;
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
   Call<Institution> getInstitutionById(@Path("id") Long id);

   @POST("/api/institutions")
   Call<Institution> saveInstitution(@Body Institution institution);

   @PUT("/api/institutions/{id}")
   Call<Institution> updateInstitutionById(@Body Institution institution, @Path("id") Long id);

   @DELETE("/api/institutions/{id")
   Call<Void> deleteInstitutionById(@Path("id") Long id);
}
