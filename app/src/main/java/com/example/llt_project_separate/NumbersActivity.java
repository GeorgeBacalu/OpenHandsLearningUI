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

public class NumbersActivity extends AppCompatActivity {
    private RecyclerView numbersRecyclerView;
    private NumbersRecyclerViewAdapter numbersAdapter;
    private ImageView toPrevPageButton;
    private FloatingActionButton toHomePageFabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);

        numbersRecyclerView = findViewById(R.id.numbersRecyclerView);
        numbersAdapter = new NumbersRecyclerViewAdapter(this);

        toPrevPageButton = findViewById(R.id.toPrevPageButton);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);

        toPrevPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NumbersActivity.this, VideoSectionActivity.class);
                startActivity(intent);
            }
        });

        toHomePageFabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NumbersActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        numbersRecyclerView.setAdapter(numbersAdapter);
        numbersRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        ArrayList<Category> numbers = new ArrayList<>();
        numbers.add(new Category(1, "UNU", R.drawable.caine));
        numbers.add(new Category(2, "DOI", R.drawable.caine));
        numbers.add(new Category(3, "TREI", R.drawable.caine));
        numbers.add(new Category(4, "PATRU", R.drawable.caine));
        numbers.add(new Category(5, "CINCI", R.drawable.caine));
        numbers.add(new Category(6, "ŞASE", R.drawable.caine));
        numbers.add(new Category(7, "ŞAPTE", R.drawable.caine));
        numbers.add(new Category(8, "OPT", R.drawable.caine));
        numbers.add(new Category(9, "NOUĂ", R.drawable.caine));
        numbers.add(new Category(10, "ZERO", R.drawable.caine));
        numbersAdapter.setNumbers(numbers);
    }
}