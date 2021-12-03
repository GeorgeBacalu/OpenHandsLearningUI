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

public class HouseholdAnimalsActivity extends AppCompatActivity {
    private RecyclerView householdAnimalsRecyclerView;
    private HouseholdAnimalsRecyclerViewAdapter householdAnimalsAdapter;
    private FloatingActionButton toHomePageFabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_household_animals);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        householdAnimalsRecyclerView = findViewById(R.id.householdAnimalsRecyclerView);
        householdAnimalsAdapter = new HouseholdAnimalsRecyclerViewAdapter(this);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);

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
        householdAnimals.add(new Category(52, "CÂINE", R.drawable.caine));
        householdAnimals.add(new Category(53, "PISICĂ", R.drawable.pisica));
        householdAnimals.add(new Category(54, "PORC", R.drawable.caine));
        householdAnimals.add(new Category(55, "CAPRĂ", R.drawable.pisica));
        householdAnimals.add(new Category(56, "GĂINĂ", R.drawable.caine));
        householdAnimals.add(new Category(57, "IEPURE", R.drawable.pisica));
        householdAnimals.add(new Category(58, "PUI", R.drawable.caine));
        householdAnimals.add(new Category(59, "VACĂ", R.drawable.pisica));
        householdAnimals.add(new Category(60, "CAL", R.drawable.caine));
        householdAnimals.add(new Category(61, "COCOŞ", R.drawable.pisica));
        householdAnimals.add(new Category(62, "GÂSCĂ", R.drawable.caine));
        householdAnimals.add(new Category(63, "OAIE", R.drawable.pisica));
        householdAnimals.add(new Category(64, "RAŢĂ", R.drawable.pisica));
        householdAnimalsAdapter.setHouseholdAnimals(householdAnimals);
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