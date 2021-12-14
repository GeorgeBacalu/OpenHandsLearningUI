package com.example.llt_project_separate.general.on_boarding;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.llt_project_separate.R;
import com.example.llt_project_separate.general.new_category.NewCategoryActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class OnBoardingFragment4 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_on_boarding_4, container, false);

        FloatingActionButton nextPageButton = root.findViewById(R.id.nextPageButton);

        nextPageButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), NewCategoryActivity.class);
            startActivity(intent);
        });

        return root;
    }
}
