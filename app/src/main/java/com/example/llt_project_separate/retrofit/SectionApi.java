package com.example.llt_project_separate.retrofit;

import com.example.llt_project_separate.general.standard_classes.Section;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface SectionApi {

   @GET("/api/sections")
   Call<List<Section>> getAllSections();

   @GET("/api/sections/{id}")
   Call<Section> getSectionById(@Path("id") int id);

   @POST("/api/sections")
   Call<Section> saveSection(@Body Section section);

   @PUT("/api/sections/{id}")
   Call<Section> updateSectionById(@Body Section section, @Path("id") int id);

   @DELETE("/api/sections/{id}")
   Call<Void> deleteSectionById(@Path("id") int id);
}
