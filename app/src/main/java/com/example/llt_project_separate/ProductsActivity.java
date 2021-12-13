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

public class ProductsActivity extends AppCompatActivity {
    private RecyclerView productsRecyclerView;
    private ProductsRecyclerViewAdapter productsAdapter;
    private FloatingActionButton toHomePageFabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initializeViews();

        toHomePageFabButton.setOnClickListener(v -> {
            Intent intent = new Intent(ProductsActivity.this, MainActivity.class);
            startActivity(intent);
        });

        productsRecyclerView.setAdapter(productsAdapter);
        productsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        List<Category> products = new ArrayList<>();
        products.add(new Category(113, "SUC", R.drawable.suc));
        products.add(new Category(114, "PRĂJITURĂ", R.drawable.prajitura));
        products.add(new Category(115, "MORCOV", R.drawable.morcov));
        products.add(new Category(116, "CIOCOLATĂ", R.drawable.ciocolata));
        products.add(new Category(117, "PÂINE", R.drawable.paine));
        products.add(new Category(118, "CEAPĂ", R.drawable.ceapa));
        products.add(new Category(119, "ULEI", R.drawable.ulei));
        products.add(new Category(120, "CASTRAVEŢI", R.drawable.castraveti));
        products.add(new Category(121, "CARTOFI", R.drawable.cartofi));
        products.add(new Category(122, "CAŞCAVAL", R.drawable.cascaval));
        products.add(new Category(123, "ARDEI", R.drawable.ardei));
        products.add(new Category(124, "BRÂNZĂ", R.drawable.branza));
        productsAdapter.setProducts(products);
    }

    private void initializeViews() {
        productsRecyclerView = findViewById(R.id.productsRecyclerView);
        productsAdapter = new ProductsRecyclerViewAdapter(this);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}