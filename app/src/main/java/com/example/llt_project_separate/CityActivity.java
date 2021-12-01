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

public class CityActivity extends AppCompatActivity {
    private RecyclerView cityRecyclerView;
    private CityRecyclerViewAdapter cityAdapter;
    private ImageView toPrevPageButton;
    private FloatingActionButton toHomePageFabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        cityRecyclerView = findViewById(R.id.cityRecyclerView);
        cityAdapter = new CityRecyclerViewAdapter(this);

        toPrevPageButton = findViewById(R.id.toPrevPageButton);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);

        toPrevPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CityActivity.this, ObjectsActivity.class);
                startActivity(intent);
            }
        });

        toHomePageFabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CityActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        cityRecyclerView.setAdapter(cityAdapter);
        cityRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        ArrayList<Category> cityObjects = new ArrayList<>();
        cityObjects.add(new Category(1, "ŞCOALĂ", R.drawable.caine));
        cityObjects.add(new Category(2, "LEAGĂN", R.drawable.caine));
        cityObjects.add(new Category(3, "SPITAL", R.drawable.caine));
        cityObjects.add(new Category(4, "AUTOBUZ", R.drawable.caine));
        cityObjects.add(new Category(5, "STÂLP", R.drawable.caine));
        cityObjects.add(new Category(6, "BLOC", R.drawable.caine));
        cityObjects.add(new Category(7, "TAXI", R.drawable.caine));
        cityObjects.add(new Category(8, "LOC DE PARCARE", R.drawable.caine));
        cityObjects.add(new Category(9, "TRAMVAI", R.drawable.caine));
        cityObjects.add(new Category(10, "POLIŢIE", R.drawable.caine));
        cityAdapter.setCityObjects(cityObjects);
    }
}