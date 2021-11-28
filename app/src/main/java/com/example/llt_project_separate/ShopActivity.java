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

public class ShopActivity extends AppCompatActivity {
    private RecyclerView shopObjectsRecyclerView;
    private ShopRecyclerViewAdapter shopObjectsAdapter;
    private ImageView toPrevPageButton;
    private FloatingActionButton toHomePageFabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        shopObjectsRecyclerView = findViewById(R.id.shopObjectsRecyclerView);
        shopObjectsAdapter = new ShopRecyclerViewAdapter(this);

        toPrevPageButton = findViewById(R.id.toPrevPageButton);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);

        toPrevPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShopActivity.this, ObjectsActivity.class);
                startActivity(intent);
            }
        });

        toHomePageFabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShopActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        shopObjectsRecyclerView.setAdapter(shopObjectsAdapter);
        shopObjectsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        ArrayList<Category> shopObjects = new ArrayList<>();
        shopObjects.add(new Category(1, "BANI", R.drawable.caine));
        shopObjects.add(new Category(2, "PRODUSE", R.drawable.caine));
        shopObjectsAdapter.setShopObjects(shopObjects);
    }
}