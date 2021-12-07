package com.example.llt_project_separate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class NewCategoryFormFragment extends Fragment {
    private EditText categoryName;
    private Button categoryImage, categoryVideo, saveButton;
    float v = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_new_category_form, container, false);

        categoryName = root.findViewById(R.id.categoryName);
        categoryImage = root.findViewById(R.id.categoryImage);
        categoryVideo = root.findViewById(R.id.categoryVideo);
        saveButton = root.findViewById(R.id.saveButton);

        categoryName.setTranslationX(800);
        categoryImage.setTranslationX(800);
        categoryVideo.setTranslationX(800);
        saveButton.setTranslationY(200);

        categoryName.setAlpha(v);
        categoryImage.setAlpha(v);
        categoryVideo.setAlpha(v);
        saveButton.setAlpha(v);

        categoryName.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        categoryImage.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        categoryVideo.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        saveButton.animate().translationY(0).alpha(1).setDuration(800).setStartDelay(300).start();

        return root;
    }
}
