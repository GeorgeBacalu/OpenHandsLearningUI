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

public class NewSubcategoryTabFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_new_subcategory_tab, container, false);

        EditText subcategoryName = root.findViewById(R.id.subcategoryName);
        Button subcategoryImage = root.findViewById(R.id.subcategoryImage);
        Button subcategoryVideo = root.findViewById(R.id.subcategoryVideo);
        Button saveSubcategoryButton = root.findViewById(R.id.saveSubcategoryButton);
        float v = 0;

        subcategoryName.setTranslationX(800);
        subcategoryImage.setTranslationX(800);
        subcategoryVideo.setTranslationX(800);
        saveSubcategoryButton.setTranslationX(800);

        subcategoryName.setAlpha(v);
        subcategoryImage.setAlpha(v);
        subcategoryVideo.setAlpha(v);
        saveSubcategoryButton.setAlpha(v);

        subcategoryName.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        subcategoryImage.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        subcategoryVideo.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(500).start();
        saveSubcategoryButton.animate().translationX(0).alpha(1).setDuration(800).setStartDelay(700).start();

        return root;
    }
}
