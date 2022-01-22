package com.example.llt_project_separate.video_section.objects.shop;

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

public class ShopActivity extends AppCompatActivity {
    private RecyclerView shopRecyclerView;
    private ShopRecyclerViewAdapter shopAdapter;
    private FloatingActionButton toHomePageFabButton;
    private EditText searchBarInput;
    private ImageView searchBarIcon;

    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(api = Build.VERSION_CODES.N)
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
        shopObjects.add(new Category(187, "BANI", R.drawable.bani));
        shopObjects.add(new Category(188, "PRODUSE", R.drawable.produse));
        shopAdapter.setShopObjects(shopObjects);

        searchBarIcon.setOnClickListener(v -> {
            String searchBarInputText = searchBarInput.getText().toString().toLowerCase();
            List<Category> filteredShopObjects = shopObjects.stream().filter(category-> category.getName().toLowerCase().startsWith(searchBarInputText)).collect(Collectors.toList());
            searchBarInput.setText("");
            shopAdapter.setShopObjects(filteredShopObjects);
            shopAdapter.notifyDataSetChanged();
        });
    }

    private void initializeViews() {
        shopRecyclerView = findViewById(R.id.shopRecyclerView);
        shopAdapter = new ShopRecyclerViewAdapter(this);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);
        searchBarInput = findViewById(R.id.searchBarInput);
        searchBarIcon = findViewById(R.id.searchBarIcon);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}