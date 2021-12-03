package com.example.llt_project_separate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {
    private RecyclerView colorsRecyclerView;
    private ColorsRecyclerViewAdapter colorsAdapter;
    private FloatingActionButton toHomePageFabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colors);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        colorsRecyclerView = findViewById(R.id.colorsRecyclerView);
        colorsAdapter = new ColorsRecyclerViewAdapter(this);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);

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
        colors.add(new Category(42, "ROŞU", R.drawable.caine));
        colors.add(new Category(43, "PORTOCALIU", R.drawable.caine));
        colors.add(new Category(44, "GALBEN", R.drawable.caine));
        colors.add(new Category(45, "VERDE", R.drawable.caine));
        colors.add(new Category(46, "ALBASTRU", R.drawable.caine));
        colors.add(new Category(47, "INDIGO", R.drawable.caine));
        colors.add(new Category(48, "VIOLET", R.drawable.caine));
        colors.add(new Category(49, "MARO", R.drawable.caine));
        colors.add(new Category(50, "ROZ", R.drawable.caine));
        colors.add(new Category(51, "TURCOAZ", R.drawable.caine));
        colorsAdapter.setColors(colors);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}