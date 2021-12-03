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

public class OutsideActivity extends AppCompatActivity {
    private RecyclerView outsideRecyclerView;
    private OutsideRecyclerViewAdapter outsideAdapter;
    private FloatingActionButton toHomePageFabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outside);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        outsideRecyclerView = findViewById(R.id.outsideRecyclerView);
        outsideAdapter = new OutsideRecyclerViewAdapter(this);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);

        toHomePageFabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OutsideActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        outsideRecyclerView.setAdapter(outsideAdapter);
        outsideRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        ArrayList<Category> outsideObjects = new ArrayList<>();
        outsideObjects.add(new Category(83, "TOPOR", R.drawable.caine));
        outsideObjects.add(new Category(84, "POARTĂ", R.drawable.caine));
        outsideObjects.add(new Category(85, "GEAM", R.drawable.caine));
        outsideObjects.add(new Category(86, "FURCĂ", R.drawable.caine));
        outsideObjects.add(new Category(87, "COPAC", R.drawable.caine));
        outsideObjects.add(new Category(88, "GARD", R.drawable.caine));
        outsideObjects.add(new Category(89, "COASĂ", R.drawable.caine));
        outsideObjects.add(new Category(90, "BEC", R.drawable.caine));
        outsideObjects.add(new Category(91, "LOPATĂ", R.drawable.caine));
        outsideObjects.add(new Category(92, "UŞĂ", R.drawable.caine));
        outsideAdapter.setOutsideObjects(outsideObjects);
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