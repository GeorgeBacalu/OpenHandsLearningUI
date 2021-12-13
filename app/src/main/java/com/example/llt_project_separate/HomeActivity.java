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

public class HomeActivity extends AppCompatActivity {
    private RecyclerView homeRecyclerView;
    private HomeRecyclerViewAdapter homeAdapter;
    private FloatingActionButton toHomePageFabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initializeViews();

        toHomePageFabButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(intent);
        });

        homeRecyclerView.setAdapter(homeAdapter);
        homeRecyclerView.setLayoutManager(new GridLayoutManager(this,2));

        List<Category> homeObjects = new ArrayList<>();
        homeObjects.add(new Category(74, "PAT", R.drawable.pat));
        homeObjects.add(new Category(75, "TELEVIZOR", R.drawable.televizor));
        homeObjects.add(new Category(76, "CALCULATOR", R.drawable.calculator));
        homeObjects.add(new Category(77, "DULAP", R.drawable.dulap));
        homeObjects.add(new Category(78, "LINGURĂ", R.drawable.lingura));
        homeObjects.add(new Category(79, "SCAUN", R.drawable.scaun));
        homeObjects.add(new Category(80, "MASĂ", R.drawable.masa));
        homeObjects.add(new Category(81, "FRIGIDER", R.drawable.frigider));
        homeObjects.add(new Category(82, "CUŢIT", R.drawable.cutit));
        homeAdapter.setHomeObjects(homeObjects);
    }

    private void initializeViews() {
        homeRecyclerView = findViewById(R.id.homeRecyclerView);
        homeAdapter = new HomeRecyclerViewAdapter(this);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}