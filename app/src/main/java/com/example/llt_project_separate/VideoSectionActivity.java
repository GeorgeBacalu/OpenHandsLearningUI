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

public class VideoSectionActivity extends AppCompatActivity {
    private RecyclerView categoriesRecyclerView;
    private VideoSectionRecyclerViewAdapter categoriesAdapter;
    private ImageView toPrevPageButton;
    private FloatingActionButton toHomePageFabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_section);

        categoriesRecyclerView = findViewById(R.id.categoriesRecyclerView);
        categoriesAdapter = new VideoSectionRecyclerViewAdapter(this);

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

        categoriesRecyclerView.setAdapter(categoriesAdapter);
        categoriesRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        ArrayList<Category> categories = new ArrayList<>();
        categories.add(new Category(1, "ALFABET", R.drawable.alfabet));
        categories.add(new Category(2, "NUMERE", R.drawable.numere));
        categories.add(new Category(3, "CULORI", R.drawable.culori));
        categories.add(new Category(4, "ANIMALE", R.drawable.animale));
        categories.add(new Category(5, "OBIECTE", R.drawable.animale));
        categories.add(new Category(6, "PERSOANE", R.drawable.animale));
        categories.add(new Category(7, "EMOTII", R.drawable.animale));
        categories.add(new Category(8, "VERBE", R.drawable.animale));
        categoriesAdapter.setCategories(categories);
    }
}