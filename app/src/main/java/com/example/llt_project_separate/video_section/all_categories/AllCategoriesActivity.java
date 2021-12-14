package com.example.llt_project_separate.video_section.all_categories;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.llt_project_separate.R;
import com.example.llt_project_separate.general.shared_preferences.Utils;

import java.util.Objects;

public class AllCategoriesActivity extends AppCompatActivity {
    private RecyclerView categoriesRecyclerView;
    private AllCategoriesRecyclerViewAdapter categoriesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_categories);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initializeViews();

        categoriesRecyclerView.setAdapter(categoriesAdapter);
        categoriesRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        categoriesAdapter.setCategories(Utils.getInstance(this).getAllCategories());
    }

    private void initializeViews() {
        categoriesRecyclerView = findViewById(R.id.categoriesRecyclerView);
        categoriesAdapter = new AllCategoriesRecyclerViewAdapter(this);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}