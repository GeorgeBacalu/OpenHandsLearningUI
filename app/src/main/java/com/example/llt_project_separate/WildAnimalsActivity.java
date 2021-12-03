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

public class WildAnimalsActivity extends AppCompatActivity {
    private RecyclerView wildAnimalsRecyclerView;
    private WildAnimalsRecyclerViewAdapter wildAnimalsAdapter;
    private FloatingActionButton toHomePageFabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wild_animals);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        wildAnimalsRecyclerView = findViewById(R.id.wildAnimalsRecyclerView);
        wildAnimalsAdapter = new WildAnimalsRecyclerViewAdapter(this);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);

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
        wildAnimals.add(new Category(65, "LUP", R.drawable.elefant));
        wildAnimals.add(new Category(66, "VULPE", R.drawable.elefant));
        wildAnimals.add(new Category(67, "ARICI", R.drawable.elefant));
        wildAnimals.add(new Category(68, "LEU", R.drawable.elefant));
        wildAnimals.add(new Category(69, "VEVERIŢĂ", R.drawable.elefant));
        wildAnimals.add(new Category(70, "ZIMBRU", R.drawable.elefant));
        wildAnimals.add(new Category(71, "URS POLAR", R.drawable.urs_polar));
        wildAnimals.add(new Category(72, "ELEFANT", R.drawable.elefant));
        wildAnimals.add(new Category(73, "CROCODIL", R.drawable.elefant));
        wildAnimalsAdapter.setWildAnimals(wildAnimals);
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