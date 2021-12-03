package com.example.llt_project_separate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class VideoSectionActivity extends AppCompatActivity {
    private RecyclerView mainCategoriesRecyclerView;
    private VideoSectionRecyclerViewAdapter mainCategoriesAdapter;
    private Button showFavoriteButton, allCategoriesButton;
    private FloatingActionButton toHomePageFabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_section);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mainCategoriesRecyclerView = findViewById(R.id.mainCategoriesRecyclerView);
        mainCategoriesAdapter = new VideoSectionRecyclerViewAdapter(this);
        showFavoriteButton = findViewById(R.id.showFavoriteButton);
        allCategoriesButton = findViewById(R.id.allCategoriesButton);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);

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

        toHomePageFabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VideoSectionActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Utils.getInstance(this);

        mainCategoriesRecyclerView.setAdapter(mainCategoriesAdapter);
        mainCategoriesRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        ArrayList<Category> categories = new ArrayList<>();
        categories.add(new Category(1001, "ALFABET", R.drawable.alfabet));
        categories.add(new Category(1002, "NUMERE", R.drawable.numere));
        categories.add(new Category(1003, "CULORI", R.drawable.culori));
        categories.add(new Category(1004, "ANIMALE", R.drawable.animale));
        categories.add(new Category(1005, "OBIECTE", R.drawable.animale));
        categories.add(new Category(1006, "PERSOANE", R.drawable.animale));
        categories.add(new Category(1007, "EMOŢII", R.drawable.animale));
        categories.add(new Category(1008, "VERBE", R.drawable.animale));
        categories.add(new Category(1009, "FORMULE DE ADRESARE", R.drawable.animale));
        mainCategoriesAdapter.setMainCategories(categories);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}