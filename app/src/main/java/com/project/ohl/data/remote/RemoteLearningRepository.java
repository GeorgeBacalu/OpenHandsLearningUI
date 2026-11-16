package com.project.ohl.data.remote;

import com.project.ohl.domain.model.County;
import com.project.ohl.domain.model.City;
import com.project.ohl.domain.model.Category;
import com.project.ohl.domain.model.School;
import com.project.ohl.domain.model.Section;
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
    private final SchoolDirectoryApi directoryApi;
    private final SectionApi sectionApi;

    public RemoteLearningRepository(Retrofit retrofit) {
        categoryApi = retrofit.create(CategoryApi.class);
        directoryApi = retrofit.create(SchoolDirectoryApi.class);
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
