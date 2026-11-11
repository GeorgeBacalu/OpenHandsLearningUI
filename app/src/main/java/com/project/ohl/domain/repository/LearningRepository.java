package com.project.ohl.domain.repository;

import com.project.ohl.domain.model.Category;
import com.project.ohl.domain.model.Institution;
import com.project.ohl.domain.model.Role;
import com.project.ohl.domain.model.Section;
import com.project.ohl.domain.model.TextSignPair;
import com.project.ohl.domain.model.User;
import java.util.List;

public interface LearningRepository {
    Request<List<TextSignPair>> getAllTextSignPairs();
    Request<TextSignPair> getTextSignPairById(Long id);
    Request<TextSignPair> saveTextSignPair(TextSignPair textSignPair);
    Request<TextSignPair> updateTextSignPairById(TextSignPair textSignPair, Long id);
    Request<Void> deleteTextSignPairById(Long id);
    Request<List<Role>> getAllRoles();
    Request<Role> getRoleById(Long id);
    Request<Role> saveRole(Role role);
    Request<Role> updateRoleById(Role role, Long id);
    Request<Void> deleteRoleById(Long id);
    Request<List<Institution>> getAllInstitutions();
    Request<Institution> getInstitutionById(Long id);
    Request<Institution> saveInstitution(Institution institution);
    Request<Institution> updateInstitutionById(Institution institution, Long id);
    Request<Void> deleteInstitutionById(Long id);
    Request<List<User>> getAllUsers();
    Request<User> getUserById(Long id);
    Request<User> saveUser(User user);
    Request<User> updateUserById(User user, Long id);
    Request<Void> deleteUserById(Long id);
    Request<List<Section>> getAllSections();
    Request<Section> getSectionById(int id);
    Request<Section> saveSection(Section section);
    Request<Section> updateSectionById(Section section, int id);
    Request<Void> deleteSectionById(int id);
    Request<List<Category>> getAllCategories();
    Request<Category> getCategoryById(Integer id);
    Request<Category> saveCategory(Category category);
    Request<Category> updateCategoryById(Category category, Integer id);
    Request<Void> deleteCategoryById(Integer id);
    Request<List<Category>> getCategoriesByParentIdAndSectionIdAndName(Integer parentId, Integer sectionId, String name);
    Request<List<Category>> getFavoriteCategories();
    Request<Category> saveFavorite(Integer categoryId);
    Request<Category> deleteFavorite(Integer categoryId);
    Request<List<Category>> getFavoritesByName(String name);
}
