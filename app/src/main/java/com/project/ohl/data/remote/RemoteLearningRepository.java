package com.project.ohl.data.remote;

import androidx.annotation.NonNull;
import com.project.ohl.domain.model.Category;
import com.project.ohl.domain.model.City;
import com.project.ohl.domain.model.County;
import com.project.ohl.domain.model.School;
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

    public RemoteLearningRepository(Retrofit retrofit) {
        categoryApi = retrofit.create(CategoryApi.class);

        directoryApi = retrofit.create(SchoolDirectoryApi.class);
    }

    @Override
    public Request<List<County>> getCounties() {
        return adapt(directoryApi.getCounties());
    }

    @Override
    public Request<List<City>> getCities(UUID countyId, String name, int offset, int limit) {
        return adapt(directoryApi.getCities(countyId, name, offset, limit));
    }

    @Override
    public Request<List<School>> getSchools(UUID cityId, String name, int offset, int limit) {
        return adapt(directoryApi.getSchools(cityId, name, offset, limit));
    }

    private static <T> Request<T> adapt(Call<T> prototype) {
        return callback -> {
            Call<T> call = prototype.clone();

            call.enqueue(
                    new Callback<>() {
                        @Override
                        public void onResponse(@NonNull Call<T> request, @NonNull Response<T> response) {
                            try (var ignored = response.errorBody()) {
                                if (request.isCanceled()) return;

                                if (response.isSuccessful()) callback.accept(Result.success(response.body()));

                                else {
                                    callback.accept(Result.failure(new HttpException(response)));
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NonNull Call<T> request, @NonNull Throwable error) {
                            if (!request.isCanceled()) callback.accept(Result.failure(error));
                        }
                    });

            return call::cancel;
        };
    }

    @Override
    public Request<Category> getCategoryById(UUID id, UUID userId) {
        return adapt(categoryApi.getCategoryById(id, userId));
    }

    @Override
    public Request<List<Category>> getCategoriesByParentIdAndName(UUID parentId, String name) {
        return adapt(categoryApi.getCategoriesByParentIdAndName(parentId, name));
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
