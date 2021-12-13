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

public class ClassActivity extends AppCompatActivity {
    private RecyclerView classRecyclerView;
    private ClassRecyclerViewAdapter classAdapter;
    private FloatingActionButton toHomePageFabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initializeViews();

        toHomePageFabButton.setOnClickListener(v -> {
            Intent intent = new Intent(ClassActivity.this, MainActivity.class);
            startActivity(intent);
        });

        classRecyclerView.setAdapter(classAdapter);
        classRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        List<Category> classObjects = new ArrayList<>();
        classObjects.add(new Category(93, "MANUAL", R.drawable.manual));
        classObjects.add(new Category(94, "PIX", R.drawable.pix));
        classObjects.add(new Category(95, "DICŢIONAR", R.drawable.dictionar));
        classObjects.add(new Category(96, "CARTE", R.drawable.carte));
        classObjects.add(new Category(97, "BANCĂ", R.drawable.banca));
        classObjects.add(new Category(98, "TEMĂ", R.drawable.tema));
        classObjects.add(new Category(99, "VIDEO PROIECTOR", R.drawable.video_proiector));
        classObjects.add(new Category(100, "TEST", R.drawable.test));
        classObjects.add(new Category(101, "STILOU", R.drawable.stilou));
        classObjects.add(new Category(102, "TEST GRILĂ", R.drawable.test_grila));
        classObjects.add(new Category(103, "RADIERĂ", R.drawable.radiera));
        classObjects.add(new Category(104, "CRETĂ", R.drawable.creta));
        classObjects.add(new Category(105, "CATEDRĂ", R.drawable.catedra));
        classObjects.add(new Category(106, "CREION", R.drawable.creion));
        classObjects.add(new Category(107, "CAIET", R.drawable.caiet));
        classAdapter.setClassObjects(classObjects);
    }

    private void initializeViews() {
        classRecyclerView = findViewById(R.id.classRecyclerView);
        classAdapter = new ClassRecyclerViewAdapter(this);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}