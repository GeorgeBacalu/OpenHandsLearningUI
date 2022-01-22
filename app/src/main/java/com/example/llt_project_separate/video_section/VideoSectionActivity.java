package com.example.llt_project_separate.video_section;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.llt_project_separate.general.standard_classes.Category;
import com.example.llt_project_separate.general.favorites.FavoritesActivity;
import com.example.llt_project_separate.MainActivity;
import com.example.llt_project_separate.general.new_category.NewCategoryActivity;
import com.example.llt_project_separate.R;
import com.example.llt_project_separate.general.shared_preferences.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class VideoSectionActivity extends AppCompatActivity {
    private RecyclerView mainCategoriesRecyclerView;
    private VideoSectionRecyclerViewAdapter mainCategoriesAdapter;
    private Button favoriteButton;
    private FloatingActionButton homePageButton, addCategoryButton;
    private EditText searchBarInput;
    private ImageView searchBarIcon;

    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_section);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initializeViews();

        favoriteButton.setOnClickListener(v -> {
            Intent intent = new Intent(VideoSectionActivity.this, FavoritesActivity.class);
            startActivity(intent);
        });

        addCategoryButton.setOnClickListener(v -> {
            Intent intent = new Intent(VideoSectionActivity.this, NewCategoryActivity.class);
            startActivity(intent);
        });

        homePageButton.setOnClickListener(v -> {
            Intent intent = new Intent(VideoSectionActivity.this, MainActivity.class);
            startActivity(intent);
        });

        Utils.getInstance(this);

        mainCategoriesRecyclerView.setAdapter(mainCategoriesAdapter);
        mainCategoriesRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1001, getStringResource(R.string.ALFABET), R.drawable.alfabet));
        categories.add(new Category(1002, getStringResource(R.string.NUMERE), R.drawable.numere));
        categories.add(new Category(1003, getStringResource(R.string.CULORI), R.drawable.culori));
        categories.add(new Category(1004, getStringResource(R.string.ANIMALE), R.drawable.animale));
        categories.add(new Category(1005, getStringResource(R.string.OBIECTE), R.drawable.obiecte));
        categories.add(new Category(1006, getStringResource(R.string.PERSOANE), R.drawable.persoane));
        categories.add(new Category(1007, getStringResource(R.string.EMOTII), R.drawable.emotii));
        categories.add(new Category(1008, getStringResource(R.string.VERBE), R.drawable.verbe));
        categories.add(new Category(1009, getStringResource(R.string.FORMULE_DE_ADRESARE), R.drawable.formule_de_adresare));
        mainCategoriesAdapter.setMainCategories(categories);

        searchBarIcon.setOnClickListener(v -> {
            String searchBarInputText = searchBarInput.getText().toString().toLowerCase();
            List<Category> filteredCategories = categories.stream().filter(category-> category.getName().toLowerCase().startsWith(searchBarInputText)).collect(Collectors.toList());
            searchBarInput.setText("");
            mainCategoriesAdapter.setMainCategories(filteredCategories);
            mainCategoriesAdapter.notifyDataSetChanged();
        });
    }

    private void initializeViews() {
        mainCategoriesRecyclerView = findViewById(R.id.mainCategoriesRecyclerView);
        mainCategoriesAdapter = new VideoSectionRecyclerViewAdapter(this);
        favoriteButton = findViewById(R.id.favoriteButton);
        homePageButton = findViewById(R.id.homePageButton);
        addCategoryButton = findViewById(R.id.addCategoryButton);
        searchBarInput = findViewById(R.id.searchBarInput);
        searchBarIcon = findViewById(R.id.searchBarIcon);
    }

    String getStringResource(int intResource) { return getResources().getString(intResource); }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}