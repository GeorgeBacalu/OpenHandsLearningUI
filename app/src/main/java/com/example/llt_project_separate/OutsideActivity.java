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

public class OutsideActivity extends AppCompatActivity {
    private RecyclerView outsideRecyclerView;
    private OutsideRecyclerViewAdapter outsideAdapter;
    private FloatingActionButton toHomePageFabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outside);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initializeViews();

        toHomePageFabButton.setOnClickListener(v -> {
            Intent intent = new Intent(OutsideActivity.this, MainActivity.class);
            startActivity(intent);
        });

        outsideRecyclerView.setAdapter(outsideAdapter);
        outsideRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        List<Category> outsideObjects = new ArrayList<>();
        outsideObjects.add(new Category(83, "TOPOR", R.drawable.topor));
        outsideObjects.add(new Category(84, "POARTĂ", R.drawable.poarta));
        outsideObjects.add(new Category(85, "GEAM", R.drawable.geam));
        outsideObjects.add(new Category(86, "FURCĂ", R.drawable.furca));
        outsideObjects.add(new Category(87, "COPAC", R.drawable.copac));
        outsideObjects.add(new Category(88, "GARD", R.drawable.gard));
        outsideObjects.add(new Category(89, "COASĂ", R.drawable.coasa));
        outsideObjects.add(new Category(90, "BEC", R.drawable.bec));
        outsideObjects.add(new Category(91, "LOPATĂ", R.drawable.lopata));
        outsideObjects.add(new Category(92, "UŞĂ", R.drawable.usa));
        outsideAdapter.setOutsideObjects(outsideObjects);
    }

    private void initializeViews() {
        outsideRecyclerView = findViewById(R.id.outsideRecyclerView);
        outsideAdapter = new OutsideRecyclerViewAdapter(this);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}