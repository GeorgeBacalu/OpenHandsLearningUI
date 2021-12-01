package com.example.llt_project_separate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import java.util.ArrayList;

public class AllCategoriesActivity extends AppCompatActivity {
    private RecyclerView categoriesRecyclerView;
    private AllCategoriesRecyclerViewAdapter categoriesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_categories);

        categoriesRecyclerView = findViewById(R.id.categoriesRecyclerView);
        categoriesAdapter = new AllCategoriesRecyclerViewAdapter(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        categoriesRecyclerView.setAdapter(categoriesAdapter);
        categoriesRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));


        categoriesAdapter.setCategories(Utils.getInstance(this).getAllCategories());
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