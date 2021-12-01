package com.example.llt_project_separate;

import java.util.ArrayList;

public class Utils {
    private static Utils instance;

    private static ArrayList<Category> allCategories;
    private static ArrayList<Category> favoriteCategories;

    private Utils() {
        if(allCategories == null) {
            allCategories = new ArrayList<>();
            initData();
        }

        if(favoriteCategories == null) {
            favoriteCategories = new ArrayList<>();
        }
    }

    private void initData() {
        //TODO: add initial data
        allCategories.add(new Category(1, "CÂINE", R.drawable.caine));
        allCategories.add(new Category(2, "PISICĂ", R.drawable.pisica));
        allCategories.add(new Category(3, "URS POLAR", R.drawable.urs_polar));
        allCategories.add(new Category(4, "ELEFANT", R.drawable.elefant));
    }

    public static synchronized Utils getInstance() {
        if(instance == null) {
            instance = new Utils();
        }
        return instance;
    }

    public static ArrayList<Category> getAllCategories() { return allCategories; }

    public static ArrayList<Category> getFavoriteCategories() {
        return favoriteCategories;
    }

    public Category getCategoryById(int id) {
        for(Category category : allCategories) {
            if(category.getId() == id) {
                return category;
            }
        }
        return null;
    }
}
