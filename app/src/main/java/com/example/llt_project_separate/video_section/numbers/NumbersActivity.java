package com.example.llt_project_separate.video_section.numbers;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.llt_project_separate.general.standard_classes.Category;
import com.example.llt_project_separate.MainActivity;
import com.example.llt_project_separate.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class NumbersActivity extends AppCompatActivity {
    private RecyclerView numbersRecyclerView;
    private NumbersRecyclerViewAdapter numbersAdapter;
    private FloatingActionButton toHomePageFabButton;
    private EditText searchBarInput;
    private ImageView searchBarIcon;

    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initializeViews();

        toHomePageFabButton.setOnClickListener(v -> {
            Intent intent = new Intent(NumbersActivity.this, MainActivity.class);
            startActivity(intent);
        });

        numbersRecyclerView.setAdapter(numbersAdapter);
        numbersRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        List<Category> numbers = new ArrayList<>();
        // numbers.add(new Category(32, getStringResource(R.string.ZERO), R.drawable.zero));
        numbers.add(new Category(33, getStringResource(R.string.UNU), R.drawable.unu));
        numbers.add(new Category(34, getStringResource(R.string.DOI), R.drawable.doi));
        numbers.add(new Category(35, getStringResource(R.string.TREI), R.drawable.trei));
        numbers.add(new Category(36, getStringResource(R.string.PATRU), R.drawable.patru));
        numbers.add(new Category(37, getStringResource(R.string.CINCI), R.drawable.cinci));
        numbers.add(new Category(38, getStringResource(R.string.SASE), R.drawable.sase));
        numbers.add(new Category(39, getStringResource(R.string.SAPTE), R.drawable.sapte));
        numbers.add(new Category(40, getStringResource(R.string.OPT), R.drawable.opt));
        numbers.add(new Category(41, getStringResource(R.string.NOUA), R.drawable.noua));
        numbers.add(new Category(42, getStringResource(R.string.ZECE), R.drawable.zece));
        numbersAdapter.setNumbers(numbers);

        searchBarIcon.setOnClickListener(v -> {
            String searchBarInputText = searchBarInput.getText().toString().toLowerCase();
            List<Category> filteredNumbers = numbers.stream().filter(category-> category.getName().toLowerCase().startsWith(searchBarInputText)).collect(Collectors.toList());
            searchBarInput.setText("");
            numbersAdapter.setNumbers(filteredNumbers);
            numbersAdapter.notifyDataSetChanged();
        });
    }

    private void initializeViews() {
        numbersRecyclerView = findViewById(R.id.numbersRecyclerView);
        numbersAdapter = new NumbersRecyclerViewAdapter(this);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);
        searchBarInput = findViewById(R.id.searchBarInput);
        searchBarIcon = findViewById(R.id.searchBarIcon);
    }

    String getStringResource(int intResource) { return getResources().getString(intResource); }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}