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
    private RecyclerView homeRecyclerView;
    private HomeRecyclerViewAdapter homeAdapter;
    private ImageView toPrevPageButton;
    private FloatingActionButton toHomePageFabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        homeRecyclerView = findViewById(R.id.homeRecyclerView);
        homeAdapter = new HomeRecyclerViewAdapter(this);

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

        homeRecyclerView.setAdapter(homeAdapter);
        homeRecyclerView.setLayoutManager(new GridLayoutManager(this,2));

        ArrayList<Category> homeObjects = new ArrayList<>();
        homeObjects.add(new Category(1, "PAT", R.drawable.caine));
        homeObjects.add(new Category(2, "TELEVIZOR", R.drawable.caine));
        homeObjects.add(new Category(3, "CALCULATOR", R.drawable.caine));
        homeObjects.add(new Category(4, "DULAP", R.drawable.caine));
        homeObjects.add(new Category(5, "LINGURĂ", R.drawable.caine));
        homeObjects.add(new Category(6, "SCAUN", R.drawable.caine));
        homeObjects.add(new Category(7, "MASĂ", R.drawable.caine));
        homeObjects.add(new Category(8, "FRIGIDER", R.drawable.caine));
        homeObjects.add(new Category(9, "CUŢIT", R.drawable.caine));
        homeAdapter.setHomeObjects(homeObjects);
    }
}