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

public class ObjectsActivity extends AppCompatActivity {
    private RecyclerView objectsRecyclerView;
    private ObjectsRecyclerViewAdapter objectsAdapter;
    private FloatingActionButton toHomePageFabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objects);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initializeViews();

        toHomePageFabButton.setOnClickListener(v -> {
            Intent intent = new Intent(ObjectsActivity.this, MainActivity.class);
            startActivity(intent);
        });

        objectsRecyclerView.setAdapter(objectsAdapter);
        objectsRecyclerView.setLayoutManager(new GridLayoutManager(this,2));

        List<Category> objects = new ArrayList<>();
        objects.add(new Category(182, "ACASĂ", R.drawable.acasa));
        objects.add(new Category(183, "AFARĂ", R.drawable.afara));
        objects.add(new Category(184, "CLASĂ", R.drawable.clasa));
        objects.add(new Category(185, "MAGAZIN", R.drawable.magazin));
        objects.add(new Category(186, "ORAŞ", R.drawable.oras));
        objectsAdapter.setObjects(objects);
    }

    private void initializeViews() {
        objectsRecyclerView = findViewById(R.id.objectsRecyclerView);
        objectsAdapter = new ObjectsRecyclerViewAdapter(this);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}