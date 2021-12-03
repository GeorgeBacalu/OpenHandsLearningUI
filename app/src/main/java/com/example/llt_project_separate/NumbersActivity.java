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

public class NumbersActivity extends AppCompatActivity {
    private RecyclerView numbersRecyclerView;
    private NumbersRecyclerViewAdapter numbersAdapter;
    private FloatingActionButton toHomePageFabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        numbersRecyclerView = findViewById(R.id.numbersRecyclerView);
        numbersAdapter = new NumbersRecyclerViewAdapter(this);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);

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
        numbers.add(new Category(32, "UNU", R.drawable.caine));
        numbers.add(new Category(33, "DOI", R.drawable.caine));
        numbers.add(new Category(34, "TREI", R.drawable.caine));
        numbers.add(new Category(35, "PATRU", R.drawable.caine));
        numbers.add(new Category(36, "CINCI", R.drawable.caine));
        numbers.add(new Category(37, "ŞASE", R.drawable.caine));
        numbers.add(new Category(38, "ŞAPTE", R.drawable.caine));
        numbers.add(new Category(39, "OPT", R.drawable.caine));
        numbers.add(new Category(40, "NOUĂ", R.drawable.caine));
        numbers.add(new Category(41, "ZERO", R.drawable.caine));
        numbersAdapter.setNumbers(numbers);
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