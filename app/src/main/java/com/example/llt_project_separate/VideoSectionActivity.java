package com.example.llt_project_separate;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class VideoSectionActivity extends AppCompatActivity {
    private RecyclerView mainCategoriesRecyclerView;
    private VideoSectionRecyclerViewAdapter mainCategoriesAdapter;
    private Button showFavoriteButton;
    private FloatingActionButton toHomePageFabButton, addCategoryButton;
    private EditText searchBarInput;
    private ImageView searchBarIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_section);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initializeViews();

        String searchBarInputText = searchBarInput.getText().toString();

        showFavoriteButton.setOnClickListener(v -> {
            Intent intent = new Intent(VideoSectionActivity.this, FavoritesActivity.class);
            startActivity(intent);
        });

        addCategoryButton.setOnClickListener(v -> {
            Intent intent = new Intent(VideoSectionActivity.this, NewCategoryActivity.class);
            startActivity(intent);
        });

        toHomePageFabButton.setOnClickListener(v -> {
            Intent intent = new Intent(VideoSectionActivity.this, MainActivity.class);
            startActivity(intent);
        });

        Utils.getInstance(this);

        mainCategoriesRecyclerView.setAdapter(mainCategoriesAdapter);
        mainCategoriesRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1001, "ALFABET", R.drawable.alfabet));
        categories.add(new Category(1002, "NUMERE", R.drawable.numere));
        categories.add(new Category(1003, "CULORI", R.drawable.culori));
        categories.add(new Category(1004, "ANIMALE", R.drawable.animale));
        categories.add(new Category(1005, "OBIECTE", R.drawable.obiecte));
        categories.add(new Category(1006, "PERSOANE", R.drawable.persoane));
        categories.add(new Category(1007, "EMOŢII", R.drawable.emotii));
        categories.add(new Category(1008, "VERBE", R.drawable.verbe));
        categories.add(new Category(1009, "FORMULE DE ADRESARE", R.drawable.formule_de_adresare));
        mainCategoriesAdapter.setMainCategories(categories);

        searchBarIcon.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                List<Category> filteredCategories = categories.stream().filter(category-> category.getName().contains(searchBarInputText)).collect(Collectors.toList());
                mainCategoriesRecyclerView.setAdapter(mainCategoriesAdapter);
                mainCategoriesRecyclerView.setLayoutManager(new GridLayoutManager(VideoSectionActivity.this, 2));
                mainCategoriesAdapter.setMainCategories(filteredCategories);
                notify();
            }
        });
    }

    private void initializeViews() {
        mainCategoriesRecyclerView = findViewById(R.id.mainCategoriesRecyclerView);
        mainCategoriesAdapter = new VideoSectionRecyclerViewAdapter(this);
        showFavoriteButton = findViewById(R.id.showFavoriteButton);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);
        addCategoryButton = findViewById(R.id.addCategoryButton);
        searchBarInput = findViewById(R.id.searchBarInput);
        searchBarIcon = findViewById(R.id.searchBarIcon);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}