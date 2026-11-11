package com.project.ohl.domain.repository;

import com.project.ohl.domain.model.Category;
import com.project.ohl.domain.model.Institution;
import com.project.ohl.domain.model.Role;
import com.project.ohl.domain.model.Section;
import com.project.ohl.domain.model.User;
import java.util.List;
import java.util.UUID;

public interface LearningRepository {
    Request<List<Role>> getAllRoles();
    Request<Role> getRoleById(UUID id);
    Request<Role> saveRole(Role role);
    Request<Role> updateRoleById(Role role, UUID id);
    Request<Void> deleteRoleById(UUID id);
    Request<List<Institution>> getAllInstitutions();
    Request<Institution> getInstitutionById(UUID id);
    Request<Institution> saveInstitution(Institution institution);
    Request<Institution> updateInstitutionById(Institution institution, UUID id);
    Request<Void> deleteInstitutionById(UUID id);
    Request<List<User>> getAllUsers();
    Request<User> getUserById(UUID id);
    Request<User> saveUser(User user);
    Request<User> updateUserById(User user, UUID id);
    Request<Void> deleteUserById(UUID id);
    Request<List<Section>> getAllSections();
    Request<Section> getSectionById(UUID id);
    Request<Section> saveSection(Section section);
    Request<Section> updateSectionById(Section section, UUID id);
    Request<Void> deleteSectionById(UUID id);
    Request<List<Category>> getAllCategories();
    Request<Category> getCategoryById(UUID id);
    Request<Category> getCategoryById(UUID id, UUID userId);
    Request<Category> saveCategory(Category category);
    Request<Category> updateCategoryById(Category category, UUID id);
    Request<Void> deleteCategoryById(UUID id);
    Request<List<Category>> getCategoriesByParentIdAndSectionIdAndName(UUID parentId, UUID sectionId, String name);
    Request<List<Category>> getFavoriteCategories(UUID userId);
    Request<Category> saveFavorite(UUID userId, UUID categoryId);
    Request<Category> deleteFavorite(UUID userId, UUID categoryId);
    Request<List<Category>> getFavoritesByName(UUID userId, String name);
}
