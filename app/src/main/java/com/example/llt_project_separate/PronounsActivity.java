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

public class PronounsActivity extends AppCompatActivity {
    private RecyclerView pronounsRecyclerView;
    private PronounsRecyclerViewAdapter pronounsAdapter;
    private ImageView toPrevPageButton;
    private FloatingActionButton toHomePageFabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pronouns);

        pronounsRecyclerView = findViewById(R.id.pronounsRecyclerView);
        pronounsAdapter = new PronounsRecyclerViewAdapter(this);

        toPrevPageButton = findViewById(R.id.toPrevPageButton);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);

        toPrevPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PronounsActivity.this, PeopleActivity.class);
                startActivity(intent);
            }
        });

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
        pronouns.add(new Category(1, "EU", R.drawable.caine));
        pronouns.add(new Category(2, "TU", R.drawable.caine));
        pronouns.add(new Category(3, "EL", R.drawable.caine));
        pronouns.add(new Category(4, "EA", R.drawable.caine));
        pronouns.add(new Category(5, "NOI", R.drawable.caine));
        pronouns.add(new Category(6, "VOI", R.drawable.caine));
        pronouns.add(new Category(7, "EI", R.drawable.caine));
        pronouns.add(new Category(8, "ELE", R.drawable.caine));
        pronounsAdapter.setPronouns(pronouns);
    }
}