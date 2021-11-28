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

public class OutsideActivity extends AppCompatActivity {
    private RecyclerView outsideObjectsRecyclerView;
    private OutsideRecyclerViewAdapter outsideObjectsAdapter;
    private ImageView toPrevPageButton;
    private FloatingActionButton toHomePageFabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outside);

        outsideObjectsRecyclerView = findViewById(R.id.outsideObjectsRecyclerView);
        outsideObjectsAdapter = new OutsideRecyclerViewAdapter(this);

        toPrevPageButton = findViewById(R.id.toPrevPageButton);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);

        toPrevPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OutsideActivity.this, ObjectsActivity.class);
                startActivity(intent);
            }
        });

        toHomePageFabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OutsideActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        outsideObjectsRecyclerView.setAdapter(outsideObjectsAdapter);
        outsideObjectsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        ArrayList<Category> outsideObjects = new ArrayList<>();
        outsideObjects.add(new Category(1, "TOPOR", R.drawable.caine));
        outsideObjects.add(new Category(2, "POARTA", R.drawable.caine));
        outsideObjects.add(new Category(3, "GEAM", R.drawable.caine));
        outsideObjects.add(new Category(4, "FURCA", R.drawable.caine));
        outsideObjects.add(new Category(5, "COPAC", R.drawable.caine));
        outsideObjects.add(new Category(6, "GARD", R.drawable.caine));
        outsideObjects.add(new Category(7, "COASA", R.drawable.caine));
        outsideObjects.add(new Category(8, "BEC", R.drawable.caine));
        outsideObjects.add(new Category(9, "LOPATA", R.drawable.caine));
        outsideObjects.add(new Category(10, "USA", R.drawable.caine));
        outsideObjectsAdapter.setOutsideObjects(outsideObjects);

    }
}