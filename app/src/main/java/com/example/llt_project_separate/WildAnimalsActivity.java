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

public class WildAnimalsActivity extends AppCompatActivity {
    private RecyclerView wildAnimalsRecyclerView;
    private WildAnimalsRecyclerViewAdapter wildAnimalsAdapter;
    private ImageView toPrevPageButton;
    private FloatingActionButton toHomePageFabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wild_animals);

        wildAnimalsRecyclerView = findViewById(R.id.wildAnimalsRecyclerView);
        wildAnimalsAdapter = new WildAnimalsRecyclerViewAdapter(this);

        toPrevPageButton = findViewById(R.id.toPrevPageButton);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);

        toPrevPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WildAnimalsActivity.this, AnimalsActivity.class);
                startActivity(intent);
            }
        });

        toHomePageFabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WildAnimalsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        wildAnimalsRecyclerView.setAdapter(wildAnimalsAdapter);
        wildAnimalsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        ArrayList<Category> wildAnimals = new ArrayList<>();
        wildAnimals.add(new Category(1, "LUP", R.drawable.elefant));
        wildAnimals.add(new Category(2, "URS", R.drawable.elefant));
        wildAnimals.add(new Category(3, "VULPE", R.drawable.elefant));
        wildAnimals.add(new Category(4, "ARICI", R.drawable.elefant));
        wildAnimals.add(new Category(5, "LEU", R.drawable.elefant));
        wildAnimals.add(new Category(6, "VEVERITA", R.drawable.elefant));
        wildAnimals.add(new Category(7, "ZIMBRU", R.drawable.elefant));
        wildAnimals.add(new Category(8, "URS POLAR", R.drawable.urs_polar));
        wildAnimals.add(new Category(9, "ELEFANT", R.drawable.elefant));
        wildAnimals.add(new Category(10, "CROCODIL", R.drawable.elefant));
        wildAnimalsAdapter.setWildAnimals(wildAnimals);
    }
}