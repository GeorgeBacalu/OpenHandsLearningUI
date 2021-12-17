package com.example.llt_project_separate.video_section.objects.shop.products;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.llt_project_separate.general.standard_classes.Category;
import com.example.llt_project_separate.MainActivity;
import com.example.llt_project_separate.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ProductsActivity extends AppCompatActivity {
    private RecyclerView productsRecyclerView;
    private ProductsRecyclerViewAdapter productsAdapter;
    private FloatingActionButton toHomePageFabButton;
    private EditText searchBarInput;
    private ImageView searchBarIcon;

    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(api = Build.VERSION_CODES.N)
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
        products.add(new Category(114, getStringResource(R.string.SUC), R.drawable.suc));
        products.add(new Category(115, getStringResource(R.string.PRAJITURA), R.drawable.prajitura));
        products.add(new Category(116, getStringResource(R.string.MORCOV), R.drawable.morcov));
        products.add(new Category(117, getStringResource(R.string.CIOCOLATA), R.drawable.ciocolata));
        products.add(new Category(118, getStringResource(R.string.PAINE), R.drawable.paine));
        products.add(new Category(119, getStringResource(R.string.CEAPA), R.drawable.ceapa));
        products.add(new Category(120, getStringResource(R.string.ULEI), R.drawable.ulei));
        products.add(new Category(121, getStringResource(R.string.CASTRAVETI), R.drawable.castraveti));
        products.add(new Category(122, getStringResource(R.string.CARTOFI), R.drawable.cartofi));
        products.add(new Category(123, getStringResource(R.string.CASCAVAL), R.drawable.cascaval));
        products.add(new Category(124, getStringResource(R.string.ARDEI), R.drawable.ardei));
        products.add(new Category(125, getStringResource(R.string.BRANZA), R.drawable.branza));
        productsAdapter.setProducts(products);

        searchBarIcon.setOnClickListener(v -> {
            String searchBarInputText = searchBarInput.getText().toString().toLowerCase();
            List<Category> filteredProducts = products.stream().filter(category-> category.getName().toLowerCase().startsWith(searchBarInputText)).collect(Collectors.toList());
            productsAdapter.setProducts(filteredProducts);
            productsAdapter.notifyDataSetChanged();
        });
    }

    private void initializeViews() {
        productsRecyclerView = findViewById(R.id.productsRecyclerView);
        productsAdapter = new ProductsRecyclerViewAdapter(this);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);
        searchBarInput = findViewById(R.id.searchBarInput);
        searchBarIcon = findViewById(R.id.searchBarIcon);
    }

    String getStringResource(int intResource) { return getResources().getString(intResource); }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}