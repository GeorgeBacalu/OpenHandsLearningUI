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

public class PronounsActivity extends AppCompatActivity {
    private RecyclerView pronounsRecyclerView;
    private PronounsRecyclerViewAdapter pronounsAdapter;
    private FloatingActionButton toHomePageFabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pronouns);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initializeViews();

        toHomePageFabButton.setOnClickListener(v -> {
            Intent intent = new Intent(PronounsActivity.this, MainActivity.class);
            startActivity(intent);
        });

        pronounsRecyclerView.setAdapter(pronounsAdapter);
        pronounsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        List<Category> pronouns = new ArrayList<>();
        pronouns.add(new Category(143, "EU"));
        pronouns.add(new Category(144, "TU"));
        pronouns.add(new Category(145, "EL"));
        pronouns.add(new Category(146, "EA"));
        pronouns.add(new Category(147, "NOI"));
        pronouns.add(new Category(148, "VOI"));
        pronouns.add(new Category(149, "EI/ELE"));
        pronounsAdapter.setPronouns(pronouns);
    }

    private void initializeViews() {
        pronounsRecyclerView = findViewById(R.id.pronounsRecyclerView);
        pronounsAdapter = new PronounsRecyclerViewAdapter(this);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}