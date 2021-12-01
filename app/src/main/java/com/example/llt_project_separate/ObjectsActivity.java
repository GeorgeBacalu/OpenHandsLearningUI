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

public class ObjectsActivity extends AppCompatActivity {
    private RecyclerView objectsRecyclerView;
    private ObjectsRecyclerViewAdapter objectsAdapter;
    private ImageView toPrevPageButton;
    private FloatingActionButton toHomePageFabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objects);

        objectsRecyclerView = findViewById(R.id.objectsRecyclerView);
        objectsAdapter = new ObjectsRecyclerViewAdapter(this);

        toPrevPageButton = findViewById(R.id.toPrevPageButton);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);

        toPrevPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ObjectsActivity.this, VideoSectionActivity.class);
                startActivity(intent);
            }
        });

        toHomePageFabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ObjectsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        objectsRecyclerView.setAdapter(objectsAdapter);
        objectsRecyclerView.setLayoutManager(new GridLayoutManager(this,2));

        ArrayList<Category> objects = new ArrayList<>();
        objects.add(new Category(1, "ACASĂ", R.drawable.animale_domestice));
        objects.add(new Category(2, "AFARĂ", R.drawable.animale_domestice));
        objects.add(new Category(3, "CLASĂ", R.drawable.animale_domestice));
        objects.add(new Category(4, "MAGAZIN", R.drawable.animale_domestice));
        objects.add(new Category(5, "ORAŞ", R.drawable.animale_domestice));
        objectsAdapter.setObjects(objects);
    }
}