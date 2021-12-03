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

public class ProductsActivity extends AppCompatActivity {
    private RecyclerView productsRecyclerView;
    private ProductsRecyclerViewAdapter productsAdapter;
    private FloatingActionButton toHomePageFabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        productsRecyclerView = findViewById(R.id.productsRecyclerView);
        productsAdapter = new ProductsRecyclerViewAdapter(this);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);

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
        products.add(new Category(113, "SUC", R.drawable.caine));
        products.add(new Category(114, "PRĂJITURĂ", R.drawable.caine));
        products.add(new Category(115, "MORCOV", R.drawable.caine));
        products.add(new Category(116, "CIOCOLATĂ", R.drawable.caine));
        products.add(new Category(117, "PÂINE", R.drawable.caine));
        products.add(new Category(118, "CEAPĂ", R.drawable.caine));
        products.add(new Category(119, "ULEI", R.drawable.caine));
        products.add(new Category(120, "CASTRAVEŢI", R.drawable.caine));
        products.add(new Category(121, "CARTOFI", R.drawable.caine));
        products.add(new Category(122, "CAŞCAVAL", R.drawable.caine));
        products.add(new Category(123, "ARDEI", R.drawable.caine));
        products.add(new Category(124, "BRÂNZĂ", R.drawable.caine));
        productsAdapter.setProducts(products);
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