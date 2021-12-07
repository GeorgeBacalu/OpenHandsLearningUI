package com.example.llt_project_separate;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class NewCategoryFormAdapter extends FragmentPagerAdapter {
    private Context context;

    public NewCategoryFormAdapter(@NonNull FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                NewCategoryFormFragment newCategoryFormFragment = new NewCategoryFormFragment();
                return newCategoryFormFragment;
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return 1;
    }
}
