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

public class AlphabetActivity extends AppCompatActivity {
    private RecyclerView alphabetRecyclerView;
    private AlphabetRecyclerViewAdapter alphabetAdapter;
    private ImageView toPrevPageButton;
    private FloatingActionButton toHomePageFabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alphabet);

        alphabetRecyclerView = findViewById(R.id.alphabetRecyclerView);
        alphabetAdapter = new AlphabetRecyclerViewAdapter(this);

        toPrevPageButton = findViewById(R.id.toPrevPageButton);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);

        toPrevPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlphabetActivity.this, VideoSectionActivity.class);
                startActivity(intent);
            }
        });

        toHomePageFabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlphabetActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        alphabetRecyclerView.setAdapter(alphabetAdapter);
        alphabetRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        ArrayList<Category> letters = new ArrayList<>();
        letters.add(new Category(1, "A", R.drawable.caine));
        letters.add(new Category(2, "B", R.drawable.caine));
        letters.add(new Category(3, "C", R.drawable.caine));
        letters.add(new Category(4, "D", R.drawable.caine));
        letters.add(new Category(5, "E", R.drawable.caine));
        letters.add(new Category(6, "F", R.drawable.caine));
        letters.add(new Category(7, "G", R.drawable.caine));
        letters.add(new Category(8, "H", R.drawable.caine));
        letters.add(new Category(9, "I", R.drawable.caine));
        letters.add(new Category(10, "J", R.drawable.caine));
        letters.add(new Category(11, "K", R.drawable.caine));
        letters.add(new Category(12, "L", R.drawable.caine));
        letters.add(new Category(13, "M", R.drawable.caine));
        letters.add(new Category(14, "N", R.drawable.caine));
        letters.add(new Category(15, "O", R.drawable.caine));
        letters.add(new Category(16, "P", R.drawable.caine));
        letters.add(new Category(17, "Q", R.drawable.caine));
        letters.add(new Category(18, "R", R.drawable.caine));
        letters.add(new Category(19, "S", R.drawable.caine));
        letters.add(new Category(20, "T", R.drawable.caine));
        letters.add(new Category(21, "U", R.drawable.caine));
        letters.add(new Category(22, "V", R.drawable.caine));
        letters.add(new Category(23, "W", R.drawable.caine));
        letters.add(new Category(24, "X", R.drawable.caine));
        letters.add(new Category(25, "Y", R.drawable.caine));
        letters.add(new Category(26, "Z", R.drawable.caine));
        alphabetAdapter.setLetters(letters);
    }
}