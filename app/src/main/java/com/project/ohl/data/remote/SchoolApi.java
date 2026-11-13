package com.project.ohl.data.remote;

import com.project.ohl.domain.model.School;
import java.util.List;
import java.util.UUID;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface SchoolApi {

   @GET("/api/schools")
   Call<List<School>> getAllSchools();

   @GET("/api/schools/{id}")
   Call<School> getSchoolById(@Path("id") UUID id);

   @POST("/api/schools")
   Call<School> saveSchool(@Body School school);

   @PUT("/api/schools/{id}")
   Call<School> updateSchoolById(@Body School school, @Path("id") UUID id);

   @DELETE("/api/schools/{id}")
   Call<Void> deleteSchoolById(@Path("id") UUID id);
}
