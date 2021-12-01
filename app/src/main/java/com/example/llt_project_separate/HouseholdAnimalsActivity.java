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

public class HouseholdAnimalsActivity extends AppCompatActivity {
    private RecyclerView householdAnimalsRecyclerView;
    private HouseholdAnimalsRecyclerViewAdapter householdAnimalsAdapter;
    private ImageView toPrevPageButton;
    private FloatingActionButton toHomePageFabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_household_animals);

        householdAnimalsRecyclerView = findViewById(R.id.householdAnimalsRecyclerView);
        householdAnimalsAdapter = new HouseholdAnimalsRecyclerViewAdapter(this);

        toPrevPageButton = findViewById(R.id.toPrevPageButton);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);

        toPrevPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HouseholdAnimalsActivity.this, AnimalsActivity.class);
                startActivity(intent);
            }
        });

        toHomePageFabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HouseholdAnimalsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        householdAnimalsRecyclerView.setAdapter(householdAnimalsAdapter);
        householdAnimalsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        ArrayList<Category> householdAnimals = new ArrayList<>();
        householdAnimals.add(new Category(1, "CÂINE", R.drawable.caine));
        householdAnimals.add(new Category(2, "PISICĂ", R.drawable.pisica));
        householdAnimals.add(new Category(3, "PORC", R.drawable.caine));
        householdAnimals.add(new Category(4, "CAPRĂ", R.drawable.pisica));
        householdAnimals.add(new Category(5, "GĂINĂ", R.drawable.caine));
        householdAnimals.add(new Category(6, "IEPURE", R.drawable.pisica));
        householdAnimals.add(new Category(7, "PUI", R.drawable.caine));
        householdAnimals.add(new Category(8, "VACĂ", R.drawable.pisica));
        householdAnimals.add(new Category(9, "CAL", R.drawable.caine));
        householdAnimals.add(new Category(10, "COCOŞ", R.drawable.pisica));
        householdAnimals.add(new Category(11, "GÂSCĂ", R.drawable.caine));
        householdAnimals.add(new Category(12, "OAIE", R.drawable.pisica));
        householdAnimals.add(new Category(13, "RAŢĂ", R.drawable.pisica));
        householdAnimalsAdapter.setHouseholdAnimals(householdAnimals);

    }
}