package com.example.llt_project_separate.voice_to_sign_section;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class VoiceToSignTabAdapter extends FragmentPagerAdapter {
    String[] titles = {"Pagina Principala", "Inregistrari salvate"};

    public VoiceToSignTabAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0: return new RecordFragment();
            case 1: return new FileViewerFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
