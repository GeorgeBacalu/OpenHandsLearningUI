package com.project.ohl.domain.repository;

import com.project.ohl.domain.model.County;
import com.project.ohl.domain.model.City;
import com.project.ohl.domain.model.Category;
import com.project.ohl.domain.model.School;
import com.project.ohl.domain.model.Role;
import com.project.ohl.domain.model.Section;
import com.project.ohl.domain.model.User;
import java.util.List;
import java.util.UUID;

public interface LearningRepository {
    Request<List<County>> getCounties();
    Request<List<City>> getCities(UUID countyId, String name, int offset, int limit);
    Request<List<School>> getSchools(UUID cityId, String name, int offset, int limit);
    Request<List<Role>> getAllRoles();
    Request<Role> getRoleById(UUID id);
    Request<Role> saveRole(Role role);
    Request<Role> updateRoleById(Role role, UUID id);
    Request<Void> deleteRoleById(UUID id);
    Request<List<School>> getAllSchools();
    Request<School> getSchoolById(UUID id);
    Request<School> saveSchool(School school);
    Request<School> updateSchoolById(School school, UUID id);
    Request<Void> deleteSchoolById(UUID id);
    Request<List<User>> getAllUsers();
    Request<User> getUserById(UUID id);
    Request<User> saveUser(User user);
    Request<User> updateUserById(User user, UUID id);
    Request<Void> deleteUserById(UUID id);
    Request<List<Section>> getAllSections();
    Request<List<Category>> getAllCategories();
    Request<Category> getCategoryById(UUID id);
    Request<Category> getCategoryById(UUID id, UUID userId);
    Request<Category> saveCategory(Category category);
    Request<Category> updateCategoryById(Category category, UUID id);
    Request<Void> deleteCategoryById(UUID id);
    Request<List<Category>> getCategoriesByParentIdAndName(UUID parentId, String name);
    Request<List<Category>> getFavoriteCategories(UUID userId);
    Request<Category> saveFavorite(UUID userId, UUID categoryId);
    Request<Category> deleteFavorite(UUID userId, UUID categoryId);
    Request<List<Category>> getFavoritesByName(UUID userId, String name);
}
