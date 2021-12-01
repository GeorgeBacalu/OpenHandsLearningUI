package com.example.llt_project_separate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity {
    private RecyclerView favoriteRecyclerView;
    private AllCategoriesRecyclerViewAdapter favoritesAdapter;
    private ImageView toPrevPageButton;
    private FloatingActionButton toHomePageFabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        favoriteRecyclerView = findViewById(R.id.favoriteRecyclerView);
        favoritesAdapter = new AllCategoriesRecyclerViewAdapter(this);

        toPrevPageButton = findViewById(R.id.toPrevPageButton);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);

        toPrevPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FavoritesActivity.this, VideoSectionActivity.class);
                startActivity(intent);
            }
        });

        toHomePageFabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FavoritesActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        favoriteRecyclerView.setAdapter(favoritesAdapter);
        favoriteRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        ArrayList<Category> favorites = new ArrayList<>();
        favorites.add(new Category(1, "CÂINE", R.drawable.caine));
        favorites.add(new Category(2, "PISICĂ", R.drawable.pisica));
        favorites.add(new Category(3, "URS POLAR", R.drawable.urs_polar));
        favorites.add(new Category(4, "ELEFANT", R.drawable.elefant));
        favoritesAdapter.setCategories(favorites);
    }
}