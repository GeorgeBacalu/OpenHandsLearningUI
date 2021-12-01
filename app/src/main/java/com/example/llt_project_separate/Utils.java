package com.example.llt_project_separate;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Utils {
    private static final String ALL_CATEGORIES = "all_categories";
    private static final String FAVORITE_CATEGORIES = "favorite_categories";

    private static Utils instance;
    private SharedPreferences sharedPreferences;

    // private static ArrayList<Category> allCategories;
    // private static ArrayList<Category> favoriteCategories;

    private Utils(Context context) {
        sharedPreferences = context.getSharedPreferences("alternate db", Context.MODE_PRIVATE);

        if(getAllCategories() == null) {
            initData();
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        if(getFavoriteCategories() == null) {
            editor.putString(FAVORITE_CATEGORIES, gson.toJson(new ArrayList<Category>()));
            editor.apply();
        }
    }

    private void initData() {
        ArrayList<Category> categories = new ArrayList<>();
        categories.add(new Category(1, "CÂINE", R.drawable.caine));
        categories.add(new Category(2, "PISICĂ", R.drawable.pisica));
        categories.add(new Category(3, "URS POLAR", R.drawable.urs_polar));
        categories.add(new Category(4, "ELEFANT", R.drawable.elefant));

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        editor.putString(ALL_CATEGORIES, gson.toJson(categories));
        editor.apply();
    }

    public static synchronized Utils getInstance(Context context) {
        if(instance == null) {
            instance = new Utils(context);
        }
        return instance;
    }

    public ArrayList<Category> getAllCategories() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Category>>(){}.getType();
        ArrayList<Category> categories = gson.fromJson(sharedPreferences.getString(ALL_CATEGORIES, null), type);
        return categories;
    }

    public ArrayList<Category> getFavoriteCategories() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Category>>(){}.getType();
        ArrayList<Category> categories = gson.fromJson(sharedPreferences.getString(FAVORITE_CATEGORIES, null), type);
        return categories;
    }

    public Category getCategoryById(int id) {
        ArrayList<Category> categories = getAllCategories();
        if(categories != null) {
            for(Category category : categories) {
                if(category.getId() == id) {
                    return category;
                }
            }
        }
        return null;
    }

    public boolean addedToFavorite(Category category) {
        ArrayList<Category> categories = getFavoriteCategories();
        if(categories != null) {
            if(categories.add(category)) {
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(FAVORITE_CATEGORIES);
                editor.putString(FAVORITE_CATEGORIES, gson.toJson(categories));
                editor.apply();
                return true;
            }
        }
        return false;
    }

    public boolean removeFromFavorite(Category category) {
        ArrayList<Category> categories = getFavoriteCategories();
        if(categories != null) {
            for(Category currentCategory : categories) {
                if(currentCategory.getId() == category.getId()) {
                    if(categories.remove(currentCategory)) {
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(FAVORITE_CATEGORIES);
                        editor.putString(FAVORITE_CATEGORIES, gson.toJson(categories));
                        editor.apply();
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
