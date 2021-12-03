package com.example.llt_project_separate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AnimalsActivity extends AppCompatActivity {
    private RecyclerView animalsRecyclerView;
    private AnimalsRecyclerViewAdapter animalsAdapter;
    // private ImageView toPrevPageButton;
    private FloatingActionButton toHomePageFabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animals);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        animalsRecyclerView = findViewById(R.id.animalsRecyclerView);
        animalsAdapter = new AnimalsRecyclerViewAdapter(this);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);

        toHomePageFabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnimalsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        animalsRecyclerView.setAdapter(animalsAdapter);
        animalsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        ArrayList<Category> animalsCategories = new ArrayList<>();
        animalsCategories.add(new Category(180, "DOMESTICE", R.drawable.animale_domestice));
        animalsCategories.add(new Category(181, "SĂLBATICE", R.drawable.animale_salbatice));
        animalsAdapter.setAnimals(animalsCategories);
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