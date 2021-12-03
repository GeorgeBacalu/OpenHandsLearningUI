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

public class VerbsActivity extends AppCompatActivity {
    private RecyclerView verbsRecyclerView;
    private VerbsRecyclerViewAdapter verbsAdapter;
    private FloatingActionButton toHomePageFabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verbs);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        verbsRecyclerView = findViewById(R.id.verbsRecyclerView);
        verbsAdapter = new VerbsRecyclerViewAdapter(this);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);

        toHomePageFabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VerbsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        verbsRecyclerView.setAdapter(verbsAdapter);
        verbsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        ArrayList<Category> verbs = new ArrayList<>();
        verbs.add(new Category(164, "CONSTRUI", R.drawable.caine));
        verbs.add(new Category(165, "SCRIE", R.drawable.caine));
        verbs.add(new Category(166, "TRAGE", R.drawable.caine));
        verbs.add(new Category(167, "CITI", R.drawable.caine));
        verbs.add(new Category(168, "STA JOS", R.drawable.caine));
        verbs.add(new Category(169, "LĂSA", R.drawable.caine));
        verbs.add(new Category(170, "APĂSA", R.drawable.caine));
        verbs.add(new Category(171, "PUNE", R.drawable.caine));
        verbs.add(new Category(172, "STRÂNGE", R.drawable.caine));
        verbs.add(new Category(173, "SCOATE", R.drawable.caine));
        verbs.add(new Category(174, "SPUNE", R.drawable.caine));
        verbs.add(new Category(175, "SE UITA", R.drawable.caine));
        verbsAdapter.setVerbs(verbs);
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