package com.project.ohl.data.remote;

import com.project.ohl.domain.model.Role;
import java.util.List;
import java.util.UUID;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RoleApi {

   @GET("/api/roles")
   Call<List<Role>> getAllRoles();

   @GET("/api/roles/{id}")
   Call<Role> getRoleById(@Path("id") UUID id);

   @POST("/api/roles")
   Call<Role> saveRole(@Body Role role);

   @PUT("/api/roles/{id}")
   Call<Role> updateRoleById(@Body Role role, @Path("id") UUID id);

   @DELETE("/api/roles/{id}")
   Call<Void> deleteRoleById(@Path("id") UUID id);
}
