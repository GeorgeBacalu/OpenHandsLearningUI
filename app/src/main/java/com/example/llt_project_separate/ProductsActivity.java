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

public class ProductsActivity extends AppCompatActivity {
    private RecyclerView productsRecyclerView;
    private ProductsRecyclerViewAdapter productsAdapter;
    private ImageView toPrevPageButton;
    private FloatingActionButton toHomePageFabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        productsRecyclerView = findViewById(R.id.productsRecyclerView);
        productsAdapter = new ProductsRecyclerViewAdapter(this);

        toPrevPageButton = findViewById(R.id.toPrevPageButton);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);

        toPrevPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductsActivity.this, ShopActivity.class);
                startActivity(intent);
            }
        });

        toHomePageFabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        productsRecyclerView.setAdapter(productsAdapter);
        productsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        ArrayList<Category> products = new ArrayList<>();
        products.add(new Category(1, "SUC", R.drawable.caine));
        products.add(new Category(2, "PRĂJITURĂ", R.drawable.caine));
        products.add(new Category(3, "MORCOV", R.drawable.caine));
        products.add(new Category(4, "CIOCOLATĂ", R.drawable.caine));
        products.add(new Category(5, "PÂINE", R.drawable.caine));
        products.add(new Category(6, "CEAPĂ", R.drawable.caine));
        products.add(new Category(7, "ULEI", R.drawable.caine));
        products.add(new Category(8, "CASTRAVEŢI", R.drawable.caine));
        products.add(new Category(9, "CARTOFI", R.drawable.caine));
        products.add(new Category(10, "CAŞCAVAL", R.drawable.caine));
        products.add(new Category(11, "ARDEI", R.drawable.caine));
        products.add(new Category(12, "BRÂNZĂ", R.drawable.caine));
        productsAdapter.setProducts(products);
    }
}