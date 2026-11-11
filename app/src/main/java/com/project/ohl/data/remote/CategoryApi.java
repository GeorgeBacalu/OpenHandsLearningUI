package com.project.ohl.data.remote;

import com.project.ohl.domain.model.Category;
import java.util.List;
import java.util.UUID;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CategoryApi {

   @GET("/api/categories")
   Call<List<Category>> getAllCategories();

   @GET("/api/categories/{id}")
   Call<Category> getCategoryById(@Path("id") UUID id, @Query("userId") UUID userId);

   @POST("/api/categories")
   Call<Category> saveCategory(@Body Category category);

   @PUT("/api/categories/{id}")
   Call<Category> updateCategoryById(@Body Category category, @Path("id") UUID id);

   @DELETE("/api/categories/{id}")
   Call<Void> deleteCategoryById(@Path("id") UUID id);

   @GET("/api/categories/filter")
   Call<List<Category>> getCategoriesByParentIdAndSectionIdAndName(@Query("parentId") UUID parentId, @Query("sectionId") UUID sectionId, @Query("name") String name);

   @GET("/api/categories/favorites")
   Call<List<Category>> getFavoriteCategories(@Query("userId") UUID userId);

   @GET("/api/categories/favorites/filter")
   Call<List<Category>> getFavoritesByName(@Query("userId") UUID userId, @Query("name") String name);

   @PATCH("/api/categories/favorites/save")
   Call<Category> saveFavorite(@Query("userId") UUID userId, @Query("categoryId") UUID categoryId);

   @PATCH("/api/categories/favorites/delete")
   Call<Category> deleteFavorite(@Query("userId") UUID userId, @Query("categoryId") UUID categoryId);
}