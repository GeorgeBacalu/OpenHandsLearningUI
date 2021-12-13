package com.example.llt_project_separate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class FavoritesActivity extends AppCompatActivity {
    private RecyclerView favoriteRecyclerView;
    private CategoryRecyclerViewAdapter favoritesAdapter;
    private FloatingActionButton toHomePageFabButton;

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
    }

    private void initializeViews() {
        favoriteRecyclerView = findViewById(R.id.favoriteRecyclerView);
        favoritesAdapter = new CategoryRecyclerViewAdapter(this, "favoriteCategories");
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}