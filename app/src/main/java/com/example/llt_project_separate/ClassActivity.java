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
    private RecyclerView classRecyclerView;
    private ClassRecyclerViewAdapter classAdapter;
    private ImageView toPrevPageButton;
    private FloatingActionButton toHomePageFabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);

        classRecyclerView = findViewById(R.id.classRecyclerView);
        classAdapter = new ClassRecyclerViewAdapter(this);

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

        classRecyclerView.setAdapter(classAdapter);
        classRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        ArrayList<Category> classObjects = new ArrayList<>();
        classObjects.add(new Category(1, "MANUAL", R.drawable.caine));
        classObjects.add(new Category(2, "PIX", R.drawable.caine));
        classObjects.add(new Category(3, "DICŢIONAR", R.drawable.caine));
        classObjects.add(new Category(4, "CARTE", R.drawable.caine));
        classObjects.add(new Category(5, "BANCĂ", R.drawable.caine));
        classObjects.add(new Category(6, "TEMĂ", R.drawable.caine));
        classObjects.add(new Category(7, "VIDEO PROIECTOR", R.drawable.caine));
        classObjects.add(new Category(8, "TEST", R.drawable.caine));
        classObjects.add(new Category(9, "STILOU", R.drawable.caine));
        classObjects.add(new Category(10, "TEST GRILĂ", R.drawable.caine));
        classObjects.add(new Category(11, "RADIERĂ", R.drawable.caine));
        classObjects.add(new Category(12, "CRETĂ", R.drawable.caine));
        classObjects.add(new Category(13, "CATEDRĂ", R.drawable.caine));
        classObjects.add(new Category(14, "CREION", R.drawable.caine));
        classObjects.add(new Category(15, "CAIET", R.drawable.caine));
        classAdapter.setClassObjects(classObjects);
    }
}