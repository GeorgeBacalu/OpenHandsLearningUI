package com.project.ohl.data.remote;

import com.project.ohl.domain.model.Category;
import java.util.List;
import java.util.UUID;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CategoryApi {

    @GET("/api/categories/{id}")
    Call<Category> getCategoryById(@Path("id") UUID id, @Query("userId") UUID userId);

    @GET("/api/categories/filter")
    Call<List<Category>> getCategoriesByParentIdAndName(@Query("parentId") UUID parentId, @Query("name") String name);

    @GET("/api/categories/favorites/filter")
    Call<List<Category>> getFavoritesByName(@Query("userId") UUID userId, @Query("name") String name);

    @PATCH("/api/categories/favorites/save")
    Call<Category> saveFavorite(@Query("userId") UUID userId, @Query("categoryId") UUID categoryId);

    @PATCH("/api/categories/favorites/delete")
    Call<Category> deleteFavorite(@Query("userId") UUID userId, @Query("categoryId") UUID categoryId);
}
