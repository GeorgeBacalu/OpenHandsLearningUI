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

public class VerbsActivity extends AppCompatActivity {
    private RecyclerView verbsRecyclerView;
    private VerbsRecyclerViewAdapter verbsAdapter;
    private FloatingActionButton toHomePageFabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verbs);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initializeViews();

        toHomePageFabButton.setOnClickListener(v -> {
            Intent intent = new Intent(VerbsActivity.this, MainActivity.class);
            startActivity(intent);
        });

        verbsRecyclerView.setAdapter(verbsAdapter);
        verbsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        List<Category> verbs = new ArrayList<>();
        verbs.add(new Category(164, "CONSTRUI", R.drawable.construi));
        verbs.add(new Category(165, "SCRIE", R.drawable.scrie));
        verbs.add(new Category(166, "TRAGE", R.drawable.trage));
        verbs.add(new Category(167, "CITI", R.drawable.citi));
        verbs.add(new Category(168, "STA JOS", R.drawable.sta_jos));
        // verbs.add(new Category(169, "LĂSA", R.drawable.lasa));
        verbs.add(new Category(170, "APĂSA", R.drawable.apasa));
        // verbs.add(new Category(171, "PUNE", R.drawable.pune));
        // verbs.add(new Category(172, "STRÂNGE", R.drawable.strange));
        // verbs.add(new Category(173, "SCOATE", R.drawable.scoate));
        verbs.add(new Category(174, "SPUNE", R.drawable.spune));
        verbs.add(new Category(175, "SE UITA", R.drawable.se_uita));
        verbsAdapter.setVerbs(verbs);
    }

    private void initializeViews() {
        verbsRecyclerView = findViewById(R.id.verbsRecyclerView);
        verbsAdapter = new VerbsRecyclerViewAdapter(this);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}