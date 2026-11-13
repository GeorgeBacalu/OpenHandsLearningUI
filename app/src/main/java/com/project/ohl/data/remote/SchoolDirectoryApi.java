package com.project.ohl.data.remote;

import com.project.ohl.domain.model.County;
import com.project.ohl.domain.model.City;
import com.project.ohl.domain.model.School;
import java.util.List;
import java.util.UUID;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SchoolDirectoryApi {
    @GET("/api/counties")
    Call<List<County>> getCounties();
    @GET("/api/cities")
    Call<List<City>> getCities(@Query("countyId") UUID countyId, @Query("name") String name, @Query("offset") int offset, @Query("limit") int limit);
    @GET("/api/schools/search")
    Call<List<School>> getSchools(@Query("cityId") UUID cityId, @Query("name") String name, @Query("offset") int offset, @Query("limit") int limit);
}
