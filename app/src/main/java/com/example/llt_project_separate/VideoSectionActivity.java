package com.example.llt_project_separate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class VideoSectionActivity extends AppCompatActivity {
    private RecyclerView mainCategoriesRecyclerView;
    private VideoSectionRecyclerViewAdapter mainCategoriesAdapter;
    private Button showFavoriteButton, allCategoriesButton;
    private ImageView toPrevPageButton;
    private FloatingActionButton toHomePageFabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_section);

        mainCategoriesRecyclerView = findViewById(R.id.mainCategoriesRecyclerView);
        mainCategoriesAdapter = new VideoSectionRecyclerViewAdapter(this);
        showFavoriteButton = findViewById(R.id.showFavoriteButton);
        allCategoriesButton = findViewById(R.id.allCategoriesButton);

        toPrevPageButton = findViewById(R.id.toPrevPageButton);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);

        toPrevPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VideoSectionActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        toHomePageFabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VideoSectionActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        showFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VideoSectionActivity.this, FavoritesActivity.class);
                startActivity(intent);
            }
        });

        allCategoriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VideoSectionActivity.this, AllCategoriesActivity.class);
                startActivity(intent);
            }
        });

        mainCategoriesRecyclerView.setAdapter(mainCategoriesAdapter);
        mainCategoriesRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        ArrayList<Category> categories = new ArrayList<>();
        categories.add(new Category(1, "ALFABET", R.drawable.alfabet));
        categories.add(new Category(2, "NUMERE", R.drawable.numere));
        categories.add(new Category(3, "CULORI", R.drawable.culori));
        categories.add(new Category(4, "ANIMALE", R.drawable.animale));
        categories.add(new Category(5, "OBIECTE", R.drawable.animale));
        categories.add(new Category(6, "PERSOANE", R.drawable.animale));
        categories.add(new Category(7, "EMOŢII", R.drawable.animale));
        categories.add(new Category(8, "VERBE", R.drawable.animale));
        categories.add(new Category(9, "FORMULE DE ADRESARE", R.drawable.animale));
        mainCategoriesAdapter.setCategories(categories);
    }
}