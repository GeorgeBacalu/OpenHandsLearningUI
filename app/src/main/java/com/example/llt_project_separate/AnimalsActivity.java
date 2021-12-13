package com.example.llt_project_separate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AnimalsActivity extends AppCompatActivity {
    private RecyclerView animalsRecyclerView;
    private AnimalsRecyclerViewAdapter animalsAdapter;
    private FloatingActionButton toHomePageFabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animals);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initializeViews();

        toHomePageFabButton.setOnClickListener(v -> {
            Intent intent = new Intent(AnimalsActivity.this, MainActivity.class);
            startActivity(intent);
        });

        animalsRecyclerView.setAdapter(animalsAdapter);
        animalsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        List<Category> animalsCategories = new ArrayList<>();
        animalsCategories.add(new Category(180, "DOMESTICE", R.drawable.animale_domestice));
        animalsCategories.add(new Category(181, "SĂLBATICE", R.drawable.animale_salbatice));
        animalsAdapter.setAnimals(animalsCategories);
    }

    private void initializeViews() {
        animalsRecyclerView = findViewById(R.id.animalsRecyclerView);
        animalsAdapter = new AnimalsRecyclerViewAdapter(this);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}