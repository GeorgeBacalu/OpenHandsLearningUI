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

public class CityActivity extends AppCompatActivity {
    private RecyclerView cityRecyclerView;
    private CityRecyclerViewAdapter cityAdapter;
    private FloatingActionButton toHomePageFabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cityRecyclerView = findViewById(R.id.cityRecyclerView);
        cityAdapter = new CityRecyclerViewAdapter(this);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);

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
        cityObjects.add(new Category(125, "ŞCOALĂ", R.drawable.caine));
        cityObjects.add(new Category(126, "LEAGĂN", R.drawable.caine));
        cityObjects.add(new Category(127, "SPITAL", R.drawable.caine));
        cityObjects.add(new Category(128, "AUTOBUZ", R.drawable.caine));
        cityObjects.add(new Category(129, "STÂLP", R.drawable.caine));
        cityObjects.add(new Category(130, "BLOC", R.drawable.caine));
        cityObjects.add(new Category(131, "TAXI", R.drawable.caine));
        cityObjects.add(new Category(132, "LOC DE PARCARE", R.drawable.caine));
        cityObjects.add(new Category(133, "TRAMVAI", R.drawable.caine));
        cityObjects.add(new Category(134, "POLIŢIE", R.drawable.caine));
        cityAdapter.setCityObjects(cityObjects);
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