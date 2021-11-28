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

public class ClassActivity extends AppCompatActivity {
    private RecyclerView classObjectsRecyclerView;
    private ClassRecyclerViewAdapter classObjectsAdapter;
    private ImageView toPrevPageButton;
    private FloatingActionButton toHomePageFabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);

        classObjectsRecyclerView = findViewById(R.id.classObjectsRecyclerView);
        classObjectsAdapter = new ClassRecyclerViewAdapter(this);

        toPrevPageButton = findViewById(R.id.toPrevPageButton);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);

        toPrevPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassActivity.this, ObjectsActivity.class);
                startActivity(intent);
            }
        });

        toHomePageFabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        classObjectsRecyclerView.setAdapter(classObjectsAdapter);
        classObjectsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        ArrayList<Category> classObjects = new ArrayList<>();
        classObjects.add(new Category(1, "MANUAL", R.drawable.caine));
        classObjects.add(new Category(2, "PIX", R.drawable.caine));
        classObjects.add(new Category(3, "DICTIONAR", R.drawable.caine));
        classObjects.add(new Category(4, "CARTE", R.drawable.caine));
        classObjects.add(new Category(5, "BANCA", R.drawable.caine));
        classObjects.add(new Category(6, "TEMA", R.drawable.caine));
        classObjects.add(new Category(7, "VIDEO PROIECTOR", R.drawable.caine));
        classObjects.add(new Category(8, "TEST", R.drawable.caine));
        classObjects.add(new Category(9, "STILOU", R.drawable.caine));
        classObjects.add(new Category(10, "TEST GRILA", R.drawable.caine));
        classObjects.add(new Category(11, "RADIERA", R.drawable.caine));
        classObjects.add(new Category(12, "CRETA", R.drawable.caine));
        classObjects.add(new Category(13, "CATEDRA", R.drawable.caine));
        classObjects.add(new Category(14, "CREION", R.drawable.caine));
        classObjects.add(new Category(15, "CAIET", R.drawable.caine));
        classObjectsAdapter.setClassObjects(classObjects);
    }
}