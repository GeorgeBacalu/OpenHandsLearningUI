package com.example.llt_project_separate.general.new_category;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.llt_project_separate.R;

public class NewCategoryTabFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_new_category_tab, container, false);

        EditText categoryName = root.findViewById(R.id.categoryName);
        Button categoryImage = root.findViewById(R.id.categoryImage);
        Button saveCategoryButton = root.findViewById(R.id.saveCategoryButton);
        float v = 0;

        categoryName.setTranslationX(800);
        categoryImage.setTranslationX(800);
        saveCategoryButton.setTranslationY(200);

        categoryName.setAlpha(v);
        categoryImage.setAlpha(v);
        saveCategoryButton.setAlpha(v);

        categoryName.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        categoryImage.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();
        saveCategoryButton.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(300).start();

        return root;
    }
}
