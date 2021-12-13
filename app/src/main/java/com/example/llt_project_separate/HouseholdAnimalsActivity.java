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

public class HouseholdAnimalsActivity extends AppCompatActivity {
    private RecyclerView householdAnimalsRecyclerView;
    private HouseholdAnimalsRecyclerViewAdapter householdAnimalsAdapter;
    private FloatingActionButton toHomePageFabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_household_animals);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initializeViews();

        toHomePageFabButton.setOnClickListener(v -> {
            Intent intent = new Intent(HouseholdAnimalsActivity.this, MainActivity.class);
            startActivity(intent);
        });

        householdAnimalsRecyclerView.setAdapter(householdAnimalsAdapter);
        householdAnimalsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        List<Category> householdAnimals = new ArrayList<>();
        householdAnimals.add(new Category(52, "CÂINE", R.drawable.caine));
        householdAnimals.add(new Category(53, "PISICĂ", R.drawable.pisica));
        householdAnimals.add(new Category(54, "PORC", R.drawable.porc));
        householdAnimals.add(new Category(55, "CAPRĂ", R.drawable.capra));
        householdAnimals.add(new Category(56, "GĂINĂ", R.drawable.gaina));
        householdAnimals.add(new Category(57, "IEPURE", R.drawable.iepure));
        householdAnimals.add(new Category(58, "PUI", R.drawable.pui));
        householdAnimals.add(new Category(59, "VACĂ", R.drawable.vaca));
        householdAnimals.add(new Category(60, "CAL", R.drawable.cal));
        householdAnimals.add(new Category(61, "COCOŞ", R.drawable.cocos));
        householdAnimals.add(new Category(62, "GÂSCĂ", R.drawable.gasca));
        householdAnimals.add(new Category(63, "OAIE", R.drawable.oaie));
        householdAnimals.add(new Category(64, "RAŢĂ", R.drawable.rata));
        householdAnimalsAdapter.setHouseholdAnimals(householdAnimals);
    }

    private void initializeViews() {
        householdAnimalsRecyclerView = findViewById(R.id.householdAnimalsRecyclerView);
        householdAnimalsAdapter = new HouseholdAnimalsRecyclerViewAdapter(this);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}