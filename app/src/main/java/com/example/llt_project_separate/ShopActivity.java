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

public class ShopActivity extends AppCompatActivity {
    private RecyclerView shopRecyclerView;
    private ShopRecyclerViewAdapter shopAdapter;
    private FloatingActionButton toHomePageFabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        shopRecyclerView = findViewById(R.id.shopRecyclerView);
        shopAdapter = new ShopRecyclerViewAdapter(this);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);

        toHomePageFabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShopActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        shopRecyclerView.setAdapter(shopAdapter);
        shopRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        ArrayList<Category> shopObjects = new ArrayList<>();
        shopObjects.add(new Category(189, "BANI", R.drawable.caine));
        shopObjects.add(new Category(190, "PRODUSE", R.drawable.caine));
        shopAdapter.setShopObjects(shopObjects);
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