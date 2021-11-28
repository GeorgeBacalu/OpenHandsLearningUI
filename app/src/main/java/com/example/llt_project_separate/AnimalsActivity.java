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

public class AnimalsActivity extends AppCompatActivity {
    private RecyclerView animalsCategoriesRecyclerView;
    private AnimalsRecyclerViewAdapter animalsCategoriesAdapter;
    private ImageView toPrevPageButton;
    private FloatingActionButton toHomePageFabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animals);

        animalsCategoriesRecyclerView = findViewById(R.id.animalsCategoriesRecyclerView);
        animalsCategoriesAdapter = new AnimalsRecyclerViewAdapter(this);

        toPrevPageButton = findViewById(R.id.toPrevPageButton);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);

        toPrevPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnimalsActivity.this, VideoSectionActivity.class);
                startActivity(intent);
            }
        });

        toHomePageFabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnimalsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        animalsCategoriesRecyclerView.setAdapter(animalsCategoriesAdapter);
        animalsCategoriesRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        ArrayList<Category> animalsCategories = new ArrayList<>();
        animalsCategories.add(new Category(1, "DOMESTICE", R.drawable.animale_domestice));
        animalsCategories.add(new Category(2, "SALBATICE", R.drawable.animale_salbatice));
        animalsCategoriesAdapter.setAnimalsCategories(animalsCategories);
    }
}