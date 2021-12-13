package com.example.llt_project_separate;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class NewCategoryFormAdapter extends FragmentPagerAdapter {
    private final Context context;
    int numberOfTabs;

    public NewCategoryFormAdapter(FragmentManager fm, Context context, int numberOfTabs) {
        super(fm);
        this.context = context;
        this.numberOfTabs = numberOfTabs;
    }

    @NonNull
    public Fragment getItem(int position) {
        switch(position) {
            case 0: return new NewSubcategoryTabFragment();
            case 1: return new NewCategoryTabFragment();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
