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

public class PronounsActivity extends AppCompatActivity {
    private RecyclerView pronounsRecyclerView;
    private PronounsRecyclerViewAdapter pronounsAdapter;
    private FloatingActionButton toHomePageFabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pronouns);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pronounsRecyclerView = findViewById(R.id.pronounsRecyclerView);
        pronounsAdapter = new PronounsRecyclerViewAdapter(this);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);

        toHomePageFabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PronounsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        pronounsRecyclerView.setAdapter(pronounsAdapter);
        pronounsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        ArrayList<Category> pronouns = new ArrayList<>();
        pronouns.add(new Category(143, "EU", R.drawable.caine));
        pronouns.add(new Category(144, "TU", R.drawable.caine));
        pronouns.add(new Category(145, "EL", R.drawable.caine));
        pronouns.add(new Category(146, "EA", R.drawable.caine));
        pronouns.add(new Category(147, "NOI", R.drawable.caine));
        pronouns.add(new Category(148, "VOI", R.drawable.caine));
        pronouns.add(new Category(149, "EI", R.drawable.caine));
        pronouns.add(new Category(150, "ELE", R.drawable.caine));
        pronounsAdapter.setPronouns(pronouns);
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