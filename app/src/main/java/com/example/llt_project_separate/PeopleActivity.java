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

public class PeopleActivity extends AppCompatActivity {
    private RecyclerView peopleRecyclerView;
    private PeopleRecyclerViewAdapter peopleAdapter;
    private ImageView toPrevPageButton;
    private FloatingActionButton toHomePageFabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);

        peopleRecyclerView = findViewById(R.id.peopleRecyclerView);
        peopleAdapter = new PeopleRecyclerViewAdapter(this);

        toPrevPageButton = findViewById(R.id.toPrevPageButton);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);

        toPrevPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PeopleActivity.this, VideoSectionActivity.class);
                startActivity(intent);
            }
        });

        toHomePageFabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PeopleActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        peopleRecyclerView.setAdapter(peopleAdapter);
        peopleRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        ArrayList<Category> people = new ArrayList<>();
        people.add(new Category(1, "MEMBRII FAMILIEI", R.drawable.caine));
        people.add(new Category(2, "PRONUME", R.drawable.caine));
        peopleAdapter.setPeople(people);

    }
}