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

public class ClassActivity extends AppCompatActivity {
    private RecyclerView classRecyclerView;
    private ClassRecyclerViewAdapter classAdapter;
    private FloatingActionButton toHomePageFabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        classRecyclerView = findViewById(R.id.classRecyclerView);
        classAdapter = new ClassRecyclerViewAdapter(this);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);

        toHomePageFabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        classRecyclerView.setAdapter(classAdapter);
        classRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        ArrayList<Category> classObjects = new ArrayList<>();
        classObjects.add(new Category(93, "MANUAL", R.drawable.caine));
        classObjects.add(new Category(94, "PIX", R.drawable.caine));
        classObjects.add(new Category(95, "DICŢIONAR", R.drawable.caine));
        classObjects.add(new Category(96, "CARTE", R.drawable.caine));
        classObjects.add(new Category(97, "BANCĂ", R.drawable.caine));
        classObjects.add(new Category(98, "TEMĂ", R.drawable.caine));
        classObjects.add(new Category(99, "VIDEO PROIECTOR", R.drawable.caine));
        classObjects.add(new Category(100, "TEST", R.drawable.caine));
        classObjects.add(new Category(101, "STILOU", R.drawable.caine));
        classObjects.add(new Category(102, "TEST GRILĂ", R.drawable.caine));
        classObjects.add(new Category(103, "RADIERĂ", R.drawable.caine));
        classObjects.add(new Category(104, "CRETĂ", R.drawable.caine));
        classObjects.add(new Category(105, "CATEDRĂ", R.drawable.caine));
        classObjects.add(new Category(106, "CREION", R.drawable.caine));
        classObjects.add(new Category(107, "CAIET", R.drawable.caine));
        classAdapter.setClassObjects(classObjects);
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