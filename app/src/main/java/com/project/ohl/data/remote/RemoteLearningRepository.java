package com.project.ohl.data.remote;

import com.project.ohl.domain.model.County;
import com.project.ohl.domain.model.City;
import com.project.ohl.domain.model.Category;
import com.project.ohl.domain.model.School;
import com.project.ohl.domain.model.Role;
import com.project.ohl.domain.model.Section;
import com.project.ohl.domain.model.User;
import com.project.ohl.domain.repository.LearningRepository;
import com.project.ohl.domain.repository.Request;
import com.project.ohl.domain.repository.Result;
import java.util.List;
import java.util.UUID;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;

public final class RemoteLearningRepository implements LearningRepository {
    private final CategoryApi categoryApi;
    private final RoleApi roleApi;
    private final SchoolApi schoolApi;
    private final SchoolDirectoryApi directoryApi;
    private final UserApi userApi;
    private final SectionApi sectionApi;

    public RemoteLearningRepository(Retrofit retrofit) {
        categoryApi = retrofit.create(CategoryApi.class);
        roleApi = retrofit.create(RoleApi.class);
        schoolApi = retrofit.create(SchoolApi.class);
        directoryApi = retrofit.create(SchoolDirectoryApi.class);
        userApi = retrofit.create(UserApi.class);
        sectionApi = retrofit.create(SectionApi.class);
    }

    @Override public Request<List<County>> getCounties() { return adapt(directoryApi.getCounties()); }
    @Override public Request<List<City>> getCities(UUID countyId, String name, int offset, int limit) {
        return adapt(directoryApi.getCities(countyId, name, offset, limit));
    }
    @Override public Request<List<School>> getSchools(UUID cityId, String name, int offset, int limit) {
        return adapt(directoryApi.getSchools(cityId, name, offset, limit));
    }

    private static <T> Request<T> adapt(Call<T> prototype) {
        return callback -> {
            Call<T> call = prototype.clone();
            call.enqueue(new Callback<T>() {
                @Override public void onResponse(Call<T> request, Response<T> response) {
                    if (request.isCanceled()) return;
                    if (response.isSuccessful()) callback.accept(Result.success(response.body()));
                    else {
                        if (response.errorBody() != null) response.errorBody().close();
                        callback.accept(Result.failure(new HttpException(response)));
                    }
                }
                @Override public void onFailure(Call<T> request, Throwable error) {
                    if (!request.isCanceled()) callback.accept(Result.failure(error));
                }
            });
            return call::cancel;
        };
    }

    @Override
    public Request<List<Role>> getAllRoles() {
        return adapt(roleApi.getAllRoles());
    }

    @Override
    public Request<Role> getRoleById(UUID id) {
        return adapt(roleApi.getRoleById(id));
    }

    @Override
    public Request<Role> saveRole(Role role) {
        return adapt(roleApi.saveRole(role));
    }

    @Override
    public Request<Role> updateRoleById(Role role, UUID id) {
        return adapt(roleApi.updateRoleById(role, id));
    }

    @Override
    public Request<Void> deleteRoleById(UUID id) {
        return adapt(roleApi.deleteRoleById(id));
    }

    @Override
    public Request<List<School>> getAllSchools() {
        return adapt(schoolApi.getAllSchools());
    }

    @Override
    public Request<School> getSchoolById(UUID id) {
        return adapt(schoolApi.getSchoolById(id));
    }

    @Override
    public Request<School> saveSchool(School school) {
        return adapt(schoolApi.saveSchool(school));
    }

    @Override
    public Request<School> updateSchoolById(School school, UUID id) {
        return adapt(schoolApi.updateSchoolById(school, id));
    }

    @Override
    public Request<Void> deleteSchoolById(UUID id) {
        return adapt(schoolApi.deleteSchoolById(id));
    }

    @Override
    public Request<List<User>> getAllUsers() {
        return adapt(userApi.getAllUsers());
    }

    @Override
    public Request<User> getUserById(UUID id) {
        return adapt(userApi.getUserById(id));
    }

    @Override
    public Request<User> saveUser(User user) {
        return adapt(userApi.saveUser(user));
    }

    @Override
    public Request<User> updateUserById(User user, UUID id) {
        return adapt(userApi.updateUserById(user, id));
    }

    @Override
    public Request<Void> deleteUserById(UUID id) {
        return adapt(userApi.deleteUserById(id));
    }

    @Override
    public Request<List<Section>> getAllSections() {
        return adapt(sectionApi.getAllSections());
    }

    @Override
    public Request<List<Category>> getAllCategories() {
        return adapt(categoryApi.getAllCategories());
    }

    @Override
    public Request<Category> getCategoryById(UUID id) {
        return getCategoryById(id, null);
    }

    @Override
    public Request<Category> getCategoryById(UUID id, UUID userId) { return adapt(categoryApi.getCategoryById(id, userId)); }

    @Override
    public Request<Category> saveCategory(Category category) {
        return adapt(categoryApi.saveCategory(category));
    }

    @Override
    public Request<Category> updateCategoryById(Category category, UUID id) {
        return adapt(categoryApi.updateCategoryById(category, id));
    }

    @Override
    public Request<Void> deleteCategoryById(UUID id) {
        return adapt(categoryApi.deleteCategoryById(id));
    }

    @Override
    public Request<List<Category>> getCategoriesByParentIdAndName(UUID parentId, String name) {
        return adapt(categoryApi.getCategoriesByParentIdAndName(parentId, name));
    }

    @Override
    public Request<List<Category>> getFavoriteCategories(UUID userId) {
        return adapt(categoryApi.getFavoriteCategories(userId));
    }

    @Override
    public Request<Category> saveFavorite(UUID userId, UUID categoryId) {
        return adapt(categoryApi.saveFavorite(userId, categoryId));
    }

    @Override
    public Request<Category> deleteFavorite(UUID userId, UUID categoryId) {
        return adapt(categoryApi.deleteFavorite(userId, categoryId));
    }

    @Override
    public Request<List<Category>> getFavoritesByName(UUID userId, String name) {
        return adapt(categoryApi.getFavoritesByName(userId, name));
    }
}
