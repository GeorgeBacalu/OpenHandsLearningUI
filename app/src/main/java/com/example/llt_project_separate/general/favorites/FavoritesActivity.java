package com.example.llt_project_separate.general.favorites;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.llt_project_separate.MainActivity;
import com.example.llt_project_separate.R;
import com.example.llt_project_separate.general.shared_preferences.Utils;
import com.example.llt_project_separate.general.standard_classes.Category;
import com.example.llt_project_separate.video_section.single_category.CategoryRecyclerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FavoritesActivity extends AppCompatActivity {
    private RecyclerView favoriteRecyclerView;
    private CategoryRecyclerViewAdapter favoritesAdapter;
    private FloatingActionButton toHomePageFabButton;
    private EditText searchBarInput;
    private ImageView searchBarIcon;

    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initializeViews();

        toHomePageFabButton.setOnClickListener(v -> {
            Intent intent = new Intent(FavoritesActivity.this, MainActivity.class);
            startActivity(intent);
        });

        favoriteRecyclerView.setAdapter(favoritesAdapter);
        favoriteRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        favoritesAdapter.setCategories(Utils.getInstance(this).getFavoriteCategories());

        searchBarIcon.setOnClickListener(v -> {
            String searchBarInputText = searchBarInput.getText().toString().toLowerCase();
            List<Category> filteredCategories = Utils.getInstance(this).getFavoriteCategories().stream().filter(category-> category.getName().toLowerCase().startsWith(searchBarInputText)).collect(Collectors.toList());
            favoritesAdapter.setCategories(filteredCategories);
            favoritesAdapter.notifyDataSetChanged();
        });
    }

    private void initializeViews() {
        favoriteRecyclerView = findViewById(R.id.favoriteRecyclerView);
        favoritesAdapter = new CategoryRecyclerViewAdapter(this, "favoriteCategories");
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);
        searchBarInput = findViewById(R.id.searchBarInput);
        searchBarIcon = findViewById(R.id.searchBarIcon);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}