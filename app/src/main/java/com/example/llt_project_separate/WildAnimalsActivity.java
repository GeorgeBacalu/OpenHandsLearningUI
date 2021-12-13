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

public class WildAnimalsActivity extends AppCompatActivity {
    private RecyclerView wildAnimalsRecyclerView;
    private WildAnimalsRecyclerViewAdapter wildAnimalsAdapter;
    private FloatingActionButton toHomePageFabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wild_animals);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initializeViews();

        toHomePageFabButton.setOnClickListener(v -> {
            Intent intent = new Intent(WildAnimalsActivity.this, MainActivity.class);
            startActivity(intent);
        });

        wildAnimalsRecyclerView.setAdapter(wildAnimalsAdapter);
        wildAnimalsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        List<Category> wildAnimals = new ArrayList<>();
        wildAnimals.add(new Category(65, "LUP", R.drawable.lup));
        wildAnimals.add(new Category(66, "VULPE", R.drawable.vulpe));
        wildAnimals.add(new Category(67, "ARICI", R.drawable.arici));
        wildAnimals.add(new Category(68, "LEU", R.drawable.leu));
        wildAnimals.add(new Category(69, "VEVERIŢĂ", R.drawable.veverita));
        wildAnimals.add(new Category(70, "ZIMBRU", R.drawable.zimbru));
        wildAnimals.add(new Category(71, "URS POLAR", R.drawable.urs_polar));
        wildAnimals.add(new Category(72, "ELEFANT", R.drawable.elefant));
        wildAnimals.add(new Category(73, "CROCODIL", R.drawable.crocodil));
        wildAnimalsAdapter.setWildAnimals(wildAnimals);
    }

    private void initializeViews() {
        wildAnimalsRecyclerView = findViewById(R.id.wildAnimalsRecyclerView);
        wildAnimalsAdapter = new WildAnimalsRecyclerViewAdapter(this);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}