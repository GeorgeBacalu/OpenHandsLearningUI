package com.example.llt_project_separate;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TextToSignSectionAdapter extends FragmentPagerAdapter {
    private Context context;

    public TextToSignSectionAdapter(@NonNull FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                TextToSignSectionFragment textToSignSectionFragment = new TextToSignSectionFragment();
                return textToSignSectionFragment;
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return 1;
    }
}
