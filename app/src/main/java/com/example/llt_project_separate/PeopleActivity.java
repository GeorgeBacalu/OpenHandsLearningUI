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

public class PeopleActivity extends AppCompatActivity {
    private RecyclerView peopleRecyclerView;
    private PeopleRecyclerViewAdapter peopleAdapter;
    private FloatingActionButton toHomePageFabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initializeViews();

        toHomePageFabButton.setOnClickListener(v -> {
            Intent intent = new Intent(PeopleActivity.this, MainActivity.class);
            startActivity(intent);
        });

        peopleRecyclerView.setAdapter(peopleAdapter);
        peopleRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        List<Category> people = new ArrayList<>();
        people.add(new Category(187, "MEMBRII FAMILIEI", R.drawable.membrii_familiei));
        people.add(new Category(188, "PRONUME", R.drawable.pronume));
        peopleAdapter.setPeople(people);
    }

    private void initializeViews() {
        peopleRecyclerView = findViewById(R.id.peopleRecyclerView);
        peopleAdapter = new PeopleRecyclerViewAdapter(this);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}