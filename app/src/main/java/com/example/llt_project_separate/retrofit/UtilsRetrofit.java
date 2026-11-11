package com.example.llt_project_separate.retrofit;

import android.content.Context;
import com.airbnb.lottie.L;
import com.example.llt_project_separate.general.standard_classes.Category;
import com.example.llt_project_separate.general.standard_classes.Institution;
import com.example.llt_project_separate.general.standard_classes.Role;
import com.example.llt_project_separate.general.standard_classes.Section;
import com.example.llt_project_separate.general.standard_classes.TextSignPair;
import com.example.llt_project_separate.general.standard_classes.User;
import java.util.List;
import org.w3c.dom.Text;
import retrofit2.Call;
import retrofit2.Callback;

public class UtilsRetrofit {

   private static UtilsRetrofit instance;
   private static CategoryApi categoryApi;
   private static RoleApi roleApi;
   private static InstitutionApi institutionApi;
   private static UserApi userApi;
   private static SectionApi sectionApi;
   private static TextSignPairApi textSignPairApi;

   private UtilsRetrofit(Context context) {
      categoryApi = new RetrofitService().getRetrofit().create(CategoryApi.class);
      roleApi = new RetrofitService().getRetrofit().create(RoleApi.class);
      institutionApi = new RetrofitService().getRetrofit().create(InstitutionApi.class);
      userApi = new RetrofitService().getRetrofit().create(UserApi.class);
      sectionApi = new RetrofitService().getRetrofit().create(SectionApi.class);
      textSignPairApi = new RetrofitService().getRetrofit().create(TextSignPairApi.class);
   }

   public static synchronized UtilsRetrofit getInstance(Context context) {
      if (instance == null)
         instance = new UtilsRetrofit(context);
      return instance;
   }

   public void getAllTextSignPairs(Callback<List<TextSignPair>> callback) {
      Call<List<TextSignPair>> call = textSignPairApi.getAllTextSignPairs();
      call.enqueue(callback);
   }

   public void getTextSignPairById(Callback<TextSignPair> callback, Long id) {
      Call<TextSignPair> call = textSignPairApi.getTextSignPairById(id);
      call.enqueue(callback);
   }

   public void saveTextSignPair(Callback<TextSignPair> callback, TextSignPair textSignPair) {
      Call<TextSignPair> call = textSignPairApi.saveTextSignPair(textSignPair);
      call.enqueue(callback);
   }

   public void updateTextSignPairById(Callback<TextSignPair> callback, TextSignPair textSignPair, Long id) {
      Call<TextSignPair> call = textSignPairApi.updateTextSignPairById(textSignPair, id);
      call.enqueue(callback);
   }

   public void deleteTextSignPairById(Callback<Void> callback, Long id) {
      Call<Void> call = textSignPairApi.deleteTextSignPairById(id);
      call.enqueue(callback);
   }

   public void getAllRoles(Callback<List<Role>> callback) {
      Call<List<Role>> call = roleApi.getAllRoles();
      call.enqueue(callback);
   }

   public void getRoleById(Callback<Role> callback, Long id) {
      Call<Role> call = roleApi.getRoleById(id);
      call.enqueue(callback);
   }

   public void saveRole(Callback<Role> callback, Role role) {
      Call<Role> call = roleApi.saveRole(role);
      call.enqueue(callback);
   }

   public void updateRoleById(Callback<Role> callback, Role role, Long id) {
      Call<Role> call = roleApi.updateRoleById(role, id);
      call.enqueue(callback);
   }

   public void deleteRoleById(Callback<Void> callback, Long id) {
      Call<Void> call = roleApi.deleteRoleById(id);
      call.enqueue(callback);
   }

   public void getAllInstitutions(Callback<List<Institution>> callback) {
      Call<List<Institution>> call = institutionApi.getAllInstitutions();
      call.enqueue(callback);
   }

   public void getInstitutionById(Callback<Institution> callback, Long id) {
      Call<Institution> call = institutionApi.getInstitutionById(id);
      call.enqueue(callback);
   }

   public void saveInstitution(Callback<Institution> callback, Institution institution) {
      Call<Institution> call = institutionApi.saveInstitution(institution);
      call.enqueue(callback);
   }

   public void updateInstitutionById(Callback<Institution> callback, Institution institution, Long id) {
      Call<Institution> call = institutionApi.updateInstitutionById(institution, id);
      call.enqueue(callback);
   }

   public void deleteInstitutionById(Callback<Void> callback, Long id) {
      Call<Void> call = institutionApi.deleteInstitutionById(id);
      call.enqueue(callback);
   }

   public void getAllUsers(Callback<List<User>> callback) {
      Call<List<User>> call = userApi.getAllUsers();
      call.enqueue(callback);
   }

   public void getUserById(Callback<User> callback, Long id) {
      Call<User> call = userApi.getUserById(id);
      call.enqueue(callback);
   }

   public void saveUser(Callback<User> callback, User user) {
      Call<User> call = userApi.saveUser(user);
      call.enqueue(callback);
   }

   public void updateUserById(Callback<User> callback, User user, Long id) {
      Call<User> call = userApi.updateUserById(user, id);
      call.enqueue(callback);
   }

   public void deleteUserById(Callback<Void> callback, Long id) {
      Call<Void> call = userApi.deleteUserById(id);
      call.enqueue(callback);
   }

   public void getAllSections(Callback<List<Section>> callback) {
      Call<List<Section>> call = sectionApi.getAllSections();
      call.enqueue(callback);
   }

   public void getSectionById(Callback<Section> callback, int id) {
      Call<Section> call = sectionApi.getSectionById(id);
      call.enqueue(callback);
   }

   public void saveSection(Callback<Section> callback, Section section) {
      Call<Section> call = sectionApi.saveSection(section);
      call.enqueue(callback);
   }

   public void updateSectionById(Callback<Section> callback, Section section, int id) {
      Call<Section> call = sectionApi.updateSectionById(section, id);
      call.enqueue(callback);
   }

   public void deleteSectionById(Callback<Void> callback, int id) {
      Call<Void> call = sectionApi.deleteSectionById(id);
      call.enqueue(callback);
   }

   public void getAllCategories(Callback<List<Category>> callback) {
      Call<List<Category>> call = categoryApi.getAllCategories();
      call.enqueue(callback);
   }

   public void getCategoryById(Callback<Category> callback, Integer id) {
      Call<Category> call = categoryApi.getCategoryById(id);
      call.enqueue(callback);
   }

   public void saveCategory(Callback<Category> callback, Category category) {
      Call<Category> call = categoryApi.saveCategory(category);
      call.enqueue(callback);
   }

   public void updateCategoryById(Callback<Category> callback, Category category, Integer id) {
      Call<Category> call = categoryApi.updateCategoryById(category, id);
      call.enqueue(callback);
   }

   public void deleteCategoryById(Callback<Void> callback, Integer id) {
      Call<Void> call = categoryApi.deleteCategoryById(id);
      call.enqueue(callback);
   }

   public void getCategoriesByParentIdAndSectionIdAndName(Callback<List<Category>> callback, Integer parentId, Integer sectionId, String name) {
      Call<List<Category>> call = categoryApi.getCategoriesByParentIdAndSectionIdAndName(parentId, sectionId, name);
      call.enqueue(callback);
   }

   public void getFavoriteCategories(Callback<List<Category>> callback) {
      Call<List<Category>> call = categoryApi.getFavoriteCategories();
      call.enqueue(callback);
   }

   public void saveFavorite(Callback<Category> callback, Integer categoryId) {
      Call<Category> call = categoryApi.saveFavorite(categoryId);
      call.enqueue(callback);
   }

   public void deleteFavorite(Callback<Category> callback, Integer categoryId) {
      Call<Category> call = categoryApi.deleteFavorite(categoryId);
      call.enqueue(callback);
   }

   public void getFavoritesByName(Callback<List<Category>> callback, String name) {
      Call<List<Category>> call = categoryApi.getFavoritesByName(name);
      call.enqueue(callback);
   }
}
