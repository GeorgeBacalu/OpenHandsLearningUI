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

public class HomeActivity extends AppCompatActivity {
    private RecyclerView homeRecyclerView;
    private HomeRecyclerViewAdapter homeAdapter;
    private FloatingActionButton toHomePageFabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        homeRecyclerView = findViewById(R.id.homeRecyclerView);
        homeAdapter = new HomeRecyclerViewAdapter(this);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);

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
        homeObjects.add(new Category(74, "PAT", R.drawable.caine));
        homeObjects.add(new Category(75, "TELEVIZOR", R.drawable.caine));
        homeObjects.add(new Category(76, "CALCULATOR", R.drawable.caine));
        homeObjects.add(new Category(77, "DULAP", R.drawable.caine));
        homeObjects.add(new Category(78, "LINGURĂ", R.drawable.caine));
        homeObjects.add(new Category(79, "SCAUN", R.drawable.caine));
        homeObjects.add(new Category(80, "MASĂ", R.drawable.caine));
        homeObjects.add(new Category(81, "FRIGIDER", R.drawable.caine));
        homeObjects.add(new Category(82, "CUŢIT", R.drawable.caine));
        homeAdapter.setHomeObjects(homeObjects);
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