package com.project.ohl.domain.repository;

import com.project.ohl.domain.model.Category;
import com.project.ohl.domain.model.City;
import com.project.ohl.domain.model.County;
import com.project.ohl.domain.model.School;
import java.util.List;
import java.util.UUID;

public interface LearningRepository {
    Request<List<County>> getCounties();
    Request<List<City>> getCities(UUID countyId, String name, int offset, int limit);
    Request<List<School>> getSchools(UUID cityId, String name, int offset, int limit);
    Request<Category> getCategoryById(UUID id, UUID userId);
    Request<List<Category>> getCategoriesByParentIdAndName(UUID parentId, String name);
    Request<Category> saveFavorite(UUID userId, UUID categoryId);
    Request<Category> deleteFavorite(UUID userId, UUID categoryId);
    Request<List<Category>> getFavoritesByName(UUID userId, String name);
}
