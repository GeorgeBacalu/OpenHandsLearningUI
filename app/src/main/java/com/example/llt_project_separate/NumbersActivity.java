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

public class NumbersActivity extends AppCompatActivity {
    private RecyclerView numbersRecyclerView;
    private NumbersRecyclerViewAdapter numbersAdapter;
    private FloatingActionButton toHomePageFabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initializeViews();

        toHomePageFabButton.setOnClickListener(v -> {
            Intent intent = new Intent(NumbersActivity.this, MainActivity.class);
            startActivity(intent);
        });

        numbersRecyclerView.setAdapter(numbersAdapter);
        numbersRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        List<Category> numbers = new ArrayList<>();
        numbers.add(new Category(32, "ZERO", R.drawable.zero));
        numbers.add(new Category(32, "UNU", R.drawable.unu));
        numbers.add(new Category(33, "DOI", R.drawable.doi));
        numbers.add(new Category(34, "TREI", R.drawable.trei));
        numbers.add(new Category(35, "PATRU", R.drawable.patru));
        numbers.add(new Category(36, "CINCI", R.drawable.cinci));
        numbers.add(new Category(37, "ŞASE", R.drawable.sase));
        numbers.add(new Category(38, "ŞAPTE", R.drawable.sapte));
        numbers.add(new Category(39, "OPT", R.drawable.opt));
        numbers.add(new Category(40, "NOUĂ", R.drawable.noua));
        numbers.add(new Category(41, "ZECE", R.drawable.zece));
        numbersAdapter.setNumbers(numbers);
    }

    private void initializeViews() {
        numbersRecyclerView = findViewById(R.id.numbersRecyclerView);
        numbersAdapter = new NumbersRecyclerViewAdapter(this);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}