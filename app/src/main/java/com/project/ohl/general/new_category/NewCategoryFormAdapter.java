package com.project.ohl.general.new_category;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class NewCategoryFormAdapter extends FragmentStateAdapter {
    private final int numberOfTabs;

    public NewCategoryFormAdapter(@NonNull FragmentActivity activity, int numberOfTabs) {
        super(activity);
        this.numberOfTabs = numberOfTabs;
    }

    @NonNull
    public Fragment createFragment(int position) {
        switch(position) {
            case 0: return new NewSubcategoryTabFragment();
            case 1: return new NewCategoryTabFragment();
            default: throw new IllegalArgumentException("Unknown category tab: " + position);
        }
    }

    @Override
    public int getItemCount() {
        return numberOfTabs;
    }
}
