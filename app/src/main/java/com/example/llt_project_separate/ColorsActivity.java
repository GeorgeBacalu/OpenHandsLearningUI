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

public class ColorsActivity extends AppCompatActivity {
    private RecyclerView colorsRecyclerView;
    private ColorsRecyclerViewAdapter colorsAdapter;
    private ImageView toPrevPageButton;
    private FloatingActionButton toHomePageFabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colors);

        colorsRecyclerView = findViewById(R.id.colorsRecyclerView);
        colorsAdapter = new ColorsRecyclerViewAdapter(this);

        toPrevPageButton = findViewById(R.id.toPrevPageButton);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);

        toPrevPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ColorsActivity.this, VideoSectionActivity.class);
                startActivity(intent);
            }
        });

        toHomePageFabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ColorsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        colorsRecyclerView.setAdapter(colorsAdapter);
        colorsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        ArrayList<Category> colors = new ArrayList<>();
        colors.add(new Category(1, "ROSU", R.drawable.caine));
        colors.add(new Category(2, "PORTOCALIU", R.drawable.caine));
        colors.add(new Category(3, "GALBEN", R.drawable.caine));
        colors.add(new Category(4, "VERDE", R.drawable.caine));
        colors.add(new Category(5, "ALBASTRU", R.drawable.caine));
        colors.add(new Category(6, "INDIGO", R.drawable.caine));
        colors.add(new Category(7, "VIOLET", R.drawable.caine));
        colors.add(new Category(8, "MARO", R.drawable.caine));
        colors.add(new Category(9, "ROZ", R.drawable.caine));
        colors.add(new Category(10, "TURCOAZ", R.drawable.caine));
        colorsAdapter.setColors(colors);
    }
}