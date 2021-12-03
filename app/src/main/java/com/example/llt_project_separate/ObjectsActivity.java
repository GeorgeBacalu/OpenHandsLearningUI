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

public class ObjectsActivity extends AppCompatActivity {
    private RecyclerView objectsRecyclerView;
    private ObjectsRecyclerViewAdapter objectsAdapter;
    private FloatingActionButton toHomePageFabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objects);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        objectsRecyclerView = findViewById(R.id.objectsRecyclerView);
        objectsAdapter = new ObjectsRecyclerViewAdapter(this);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);

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
        objects.add(new Category(182, "ACASĂ", R.drawable.animale_domestice));
        objects.add(new Category(183, "AFARĂ", R.drawable.animale_domestice));
        objects.add(new Category(184, "CLASĂ", R.drawable.animale_domestice));
        objects.add(new Category(185, "MAGAZIN", R.drawable.animale_domestice));
        objects.add(new Category(186, "ORAŞ", R.drawable.animale_domestice));
        objectsAdapter.setObjects(objects);
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