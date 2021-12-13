package com.example.llt_project_separate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CityActivity extends AppCompatActivity {
    private RecyclerView cityRecyclerView;
    private CityRecyclerViewAdapter cityAdapter;
    private FloatingActionButton toHomePageFabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initializeViews();

        toHomePageFabButton.setOnClickListener(v -> {
            Intent intent = new Intent(CityActivity.this, MainActivity.class);
            startActivity(intent);
        });

        cityRecyclerView.setAdapter(cityAdapter);
        cityRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        List<Category> cityObjects = new ArrayList<>();
        cityObjects.add(new Category(125, "ŞCOALĂ", R.drawable.scoala));
        cityObjects.add(new Category(126, "LEAGĂN", R.drawable.leagan));
        cityObjects.add(new Category(127, "SPITAL", R.drawable.spital));
        cityObjects.add(new Category(128, "AUTOBUZ", R.drawable.autobuz));
        cityObjects.add(new Category(129, "STÂLP", R.drawable.stalp));
        cityObjects.add(new Category(130, "BLOC", R.drawable.bloc));
        cityObjects.add(new Category(131, "TAXI", R.drawable.taxi));
        cityObjects.add(new Category(132, "LOC DE PARCARE", R.drawable.loc_de_parcare));
        cityObjects.add(new Category(133, "TRAMVAI", R.drawable.tramvai));
        cityObjects.add(new Category(134, "POLIŢIE", R.drawable.politie));
        cityAdapter.setCityObjects(cityObjects);
    }

    private void initializeViews() {
        cityRecyclerView = findViewById(R.id.cityRecyclerView);
        cityAdapter = new CityRecyclerViewAdapter(this);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}