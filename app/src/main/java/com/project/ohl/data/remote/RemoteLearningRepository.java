package com.project.ohl.data.remote;

import com.project.ohl.domain.model.Category;
import com.project.ohl.domain.model.Institution;
import com.project.ohl.domain.model.Role;
import com.project.ohl.domain.model.Section;
import com.project.ohl.domain.model.TextSignPair;
import com.project.ohl.domain.model.User;
import java.util.List;
import com.project.ohl.domain.repository.LearningRepository;
import com.project.ohl.domain.repository.Request;
import com.project.ohl.domain.repository.Result;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;

public final class RemoteLearningRepository implements LearningRepository {
    private final CategoryApi categoryApi;
    private final RoleApi roleApi;
    private final InstitutionApi institutionApi;
    private final UserApi userApi;
    private final SectionApi sectionApi;
    private final TextSignPairApi textSignPairApi;

    public RemoteLearningRepository(Retrofit retrofit) {
        categoryApi = retrofit.create(CategoryApi.class);
        roleApi = retrofit.create(RoleApi.class);
        institutionApi = retrofit.create(InstitutionApi.class);
        userApi = retrofit.create(UserApi.class);
        sectionApi = retrofit.create(SectionApi.class);
        textSignPairApi = retrofit.create(TextSignPairApi.class);
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
    public Request<List<TextSignPair>> getAllTextSignPairs() {
        return adapt(textSignPairApi.getAllTextSignPairs());
    }

    @Override
    public Request<TextSignPair> getTextSignPairById(Long id) {
        return adapt(textSignPairApi.getTextSignPairById(id));
    }

    @Override
    public Request<TextSignPair> saveTextSignPair(TextSignPair textSignPair) {
        return adapt(textSignPairApi.saveTextSignPair(textSignPair));
    }

    @Override
    public Request<TextSignPair> updateTextSignPairById(TextSignPair textSignPair, Long id) {
        return adapt(textSignPairApi.updateTextSignPairById(textSignPair, id));
    }

    @Override
    public Request<Void> deleteTextSignPairById(Long id) {
        return adapt(textSignPairApi.deleteTextSignPairById(id));
    }

    @Override
    public Request<List<Role>> getAllRoles() {
        return adapt(roleApi.getAllRoles());
    }

    @Override
    public Request<Role> getRoleById(Long id) {
        return adapt(roleApi.getRoleById(id));
    }

    @Override
    public Request<Role> saveRole(Role role) {
        return adapt(roleApi.saveRole(role));
    }

    @Override
    public Request<Role> updateRoleById(Role role, Long id) {
        return adapt(roleApi.updateRoleById(role, id));
    }

    @Override
    public Request<Void> deleteRoleById(Long id) {
        return adapt(roleApi.deleteRoleById(id));
    }

    @Override
    public Request<List<Institution>> getAllInstitutions() {
        return adapt(institutionApi.getAllInstitutions());
    }

    @Override
    public Request<Institution> getInstitutionById(Long id) {
        return adapt(institutionApi.getInstitutionById(id));
    }

    @Override
    public Request<Institution> saveInstitution(Institution institution) {
        return adapt(institutionApi.saveInstitution(institution));
    }

    @Override
    public Request<Institution> updateInstitutionById(Institution institution, Long id) {
        return adapt(institutionApi.updateInstitutionById(institution, id));
    }

    @Override
    public Request<Void> deleteInstitutionById(Long id) {
        return adapt(institutionApi.deleteInstitutionById(id));
    }

    @Override
    public Request<List<User>> getAllUsers() {
        return adapt(userApi.getAllUsers());
    }

    @Override
    public Request<User> getUserById(Long id) {
        return adapt(userApi.getUserById(id));
    }

    @Override
    public Request<User> saveUser(User user) {
        return adapt(userApi.saveUser(user));
    }

    @Override
    public Request<User> updateUserById(User user, Long id) {
        return adapt(userApi.updateUserById(user, id));
    }

    @Override
    public Request<Void> deleteUserById(Long id) {
        return adapt(userApi.deleteUserById(id));
    }

    @Override
    public Request<List<Section>> getAllSections() {
        return adapt(sectionApi.getAllSections());
    }

    @Override
    public Request<Section> getSectionById(int id) {
        return adapt(sectionApi.getSectionById(id));
    }

    @Override
    public Request<Section> saveSection(Section section) {
        return adapt(sectionApi.saveSection(section));
    }

    @Override
    public Request<Section> updateSectionById(Section section, int id) {
        return adapt(sectionApi.updateSectionById(section, id));
    }

    @Override
    public Request<Void> deleteSectionById(int id) {
        return adapt(sectionApi.deleteSectionById(id));
    }

    @Override
    public Request<List<Category>> getAllCategories() {
        return adapt(categoryApi.getAllCategories());
    }

    @Override
    public Request<Category> getCategoryById(Integer id) {
        return adapt(categoryApi.getCategoryById(id));
    }

    @Override
    public Request<Category> saveCategory(Category category) {
        return adapt(categoryApi.saveCategory(category));
    }

    @Override
    public Request<Category> updateCategoryById(Category category, Integer id) {
        return adapt(categoryApi.updateCategoryById(category, id));
    }

    @Override
    public Request<Void> deleteCategoryById(Integer id) {
        return adapt(categoryApi.deleteCategoryById(id));
    }

    @Override
    public Request<List<Category>> getCategoriesByParentIdAndSectionIdAndName(Integer parentId, Integer sectionId, String name) {
        return adapt(categoryApi.getCategoriesByParentIdAndSectionIdAndName(parentId, sectionId, name));
    }

    @Override
    public Request<List<Category>> getFavoriteCategories() {
        return adapt(categoryApi.getFavoriteCategories());
    }

    @Override
    public Request<Category> saveFavorite(Integer categoryId) {
        return adapt(categoryApi.saveFavorite(categoryId));
    }

    @Override
    public Request<Category> deleteFavorite(Integer categoryId) {
        return adapt(categoryApi.deleteFavorite(categoryId));
    }

    @Override
    public Request<List<Category>> getFavoritesByName(String name) {
        return adapt(categoryApi.getFavoritesByName(name));
    }
}
