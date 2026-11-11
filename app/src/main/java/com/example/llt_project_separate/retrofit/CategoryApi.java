package com.example.llt_project_separate.retrofit;

import com.example.llt_project_separate.general.standard_classes.Category;
import java.util.List;
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
   Call<Category> getCategoryById(@Path("id") Integer id);

   @POST("/api/categories")
   Call<Category> saveCategory(@Body Category category);

   @PUT("/api/categories/{id}")
   Call<Category> updateCategoryById(@Body Category category, @Path("id") Integer id);

   @DELETE("/api/categories/{id}")
   Call<Void> deleteCategoryById(@Path("id") Integer id);

   @GET("/api/categories/filter")
   Call<List<Category>> getCategoriesByParentIdAndSectionIdAndName(@Query("parentId") Integer parentId, @Query("sectionId") Integer sectionId, @Query("name") String name);

   @GET("/api/categories/favorites")
   Call<List<Category>> getFavoriteCategories();

   @GET("/api/categories/favorites/filter")
   Call<List<Category>> getFavoritesByName(@Query("name") String name);

   @PATCH("/api/categories/favorites/save")
   Call<Category> saveFavorite(@Query("categoryId") Integer categoryId);

   @PATCH("/api/categories/favorites/delete")
   Call<Category> deleteFavorite(@Query("categoryId") Integer categoryId);
}