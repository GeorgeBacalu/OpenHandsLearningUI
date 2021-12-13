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

public class ShopActivity extends AppCompatActivity {
    private RecyclerView shopRecyclerView;
    private ShopRecyclerViewAdapter shopAdapter;
    private FloatingActionButton toHomePageFabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initializeViews();

        toHomePageFabButton.setOnClickListener(v -> {
            Intent intent = new Intent(ShopActivity.this, MainActivity.class);
            startActivity(intent);
        });

        shopRecyclerView.setAdapter(shopAdapter);
        shopRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        List<Category> shopObjects = new ArrayList<>();
        shopObjects.add(new Category(189, "BANI", R.drawable.bani));
        shopObjects.add(new Category(190, "PRODUSE", R.drawable.produse));
        shopAdapter.setShopObjects(shopObjects);
    }

    private void initializeViews() {
        shopRecyclerView = findViewById(R.id.shopRecyclerView);
        shopAdapter = new ShopRecyclerViewAdapter(this);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}