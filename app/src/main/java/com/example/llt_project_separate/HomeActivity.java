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

public class HomeActivity extends AppCompatActivity {
    private RecyclerView homeObjectsRecyclerView;
    private HomeRecyclerViewAdapter homeObjectsAdapter;
    private ImageView toPrevPageButton;
    private FloatingActionButton toHomePageFabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        homeObjectsRecyclerView = findViewById(R.id.homeObjectsRecyclerView);
        homeObjectsAdapter = new HomeRecyclerViewAdapter(this);

        toPrevPageButton = findViewById(R.id.toPrevPageButton);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);

        toPrevPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ObjectsActivity.class);
                startActivity(intent);
            }
        });

        toHomePageFabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        homeObjectsRecyclerView.setAdapter(homeObjectsAdapter);
        homeObjectsRecyclerView.setLayoutManager(new GridLayoutManager(this,2));

        ArrayList<Category> homeObjects = new ArrayList<>();
        homeObjects.add(new Category(1, "PAT", R.drawable.caine));
        homeObjects.add(new Category(2, "TELEVIZOR", R.drawable.caine));
        homeObjects.add(new Category(3, "CALCULATOR", R.drawable.caine));
        homeObjects.add(new Category(4, "DULAP", R.drawable.caine));
        homeObjects.add(new Category(5, "LINGURA", R.drawable.caine));
        homeObjects.add(new Category(6, "SCAUN", R.drawable.caine));
        homeObjects.add(new Category(7, "MASA", R.drawable.caine));
        homeObjects.add(new Category(8, "FRIGIDER", R.drawable.caine));
        homeObjects.add(new Category(9, "CUTIT", R.drawable.caine));
        homeObjectsAdapter.setHomeObjects(homeObjects);
    }
}