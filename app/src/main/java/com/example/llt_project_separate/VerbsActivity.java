package com.example.llt_project_separate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class VerbsActivity extends AppCompatActivity {
    private RecyclerView verbsRecyclerView;
    private VerbsRecyclerViewAdapter verbsAdapter;
    private ImageView toPrevPageButton;
    private FloatingActionButton toHomePageFabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verbs);

        verbsRecyclerView = findViewById(R.id.verbsRecyclerView);
        verbsAdapter = new VerbsRecyclerViewAdapter(this);

        toPrevPageButton = findViewById(R.id.toPrevPageButton);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);

        toPrevPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VerbsActivity.this, VideoSectionActivity.class);
                startActivity(intent);
            }
        });

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
        verbs.add(new Category(1, "CONSTRUI", R.drawable.caine));
        verbs.add(new Category(2, "SCRIE", R.drawable.caine));
        verbs.add(new Category(3, "TRAGE", R.drawable.caine));
        verbs.add(new Category(4, "CITI", R.drawable.caine));
        verbs.add(new Category(5, "STA JOS", R.drawable.caine));
        verbs.add(new Category(6, "LĂSA", R.drawable.caine));
        verbs.add(new Category(7, "APĂSA", R.drawable.caine));
        verbs.add(new Category(8, "PUNE", R.drawable.caine));
        verbs.add(new Category(9, "STRÂNGE", R.drawable.caine));
        verbs.add(new Category(10, "SCOATE", R.drawable.caine));
        verbs.add(new Category(11, "SPUNE", R.drawable.caine));
        verbs.add(new Category(12, "SE UITA", R.drawable.caine));
        verbsAdapter.setVerbs(verbs);
    }
}