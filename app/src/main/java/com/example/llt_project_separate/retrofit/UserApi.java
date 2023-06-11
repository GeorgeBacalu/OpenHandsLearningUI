package com.example.llt_project_separate.retrofit;

import com.example.llt_project_separate.general.standard_classes.User;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserApi {

   @GET("/api/users")
   Call<List<User>> getAllUsers();

   @GET("/api/users/{id}")
   Call<User> getUserById(@Path("id") Long id);

   @POST("/api/users")
   Call<User> saveUser(@Body User user);

   @PUT("/api/users/{id}")
   Call<User> updateUserById(@Body User user, @Path("id") Long id);

   @DELETE("/api/users/{id}")
   Call<Void> deleteUserById(@Path("id") Long id);
}
