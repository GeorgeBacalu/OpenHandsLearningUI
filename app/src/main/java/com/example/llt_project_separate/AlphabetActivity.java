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
        letters.add(new Category(2, "Ă", R.drawable.caine));
        letters.add(new Category(3, "Â", R.drawable.caine));
        letters.add(new Category(4, "B", R.drawable.caine));
        letters.add(new Category(5, "C", R.drawable.caine));
        letters.add(new Category(6, "D", R.drawable.caine));
        letters.add(new Category(7, "E", R.drawable.caine));
        letters.add(new Category(8, "F", R.drawable.caine));
        letters.add(new Category(9, "G", R.drawable.caine));
        letters.add(new Category(10, "H", R.drawable.caine));
        letters.add(new Category(11, "I", R.drawable.caine));
        letters.add(new Category(12, "Î", R.drawable.caine));
        letters.add(new Category(13, "J", R.drawable.caine));
        letters.add(new Category(14, "K", R.drawable.caine));
        letters.add(new Category(15, "L", R.drawable.caine));
        letters.add(new Category(16, "M", R.drawable.caine));
        letters.add(new Category(17, "N", R.drawable.caine));
        letters.add(new Category(18, "O", R.drawable.caine));
        letters.add(new Category(19, "P", R.drawable.caine));
        letters.add(new Category(20, "Q", R.drawable.caine));
        letters.add(new Category(21, "R", R.drawable.caine));
        letters.add(new Category(22, "S", R.drawable.caine));
        letters.add(new Category(23, "Ş", R.drawable.caine));
        letters.add(new Category(24, "T", R.drawable.caine));
        letters.add(new Category(25, "Ţ", R.drawable.caine));
        letters.add(new Category(26, "U", R.drawable.caine));
        letters.add(new Category(27, "V", R.drawable.caine));
        letters.add(new Category(28, "W", R.drawable.caine));
        letters.add(new Category(29, "X", R.drawable.caine));
        letters.add(new Category(30, "Y", R.drawable.caine));
        letters.add(new Category(31, "Z", R.drawable.caine));
        alphabetAdapter.setLetters(letters);
    }
}