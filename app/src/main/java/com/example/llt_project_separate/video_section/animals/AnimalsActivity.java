package com.example.llt_project_separate.video_section.animals;

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

public class AnimalsActivity extends AppCompatActivity {
    private RecyclerView animalsRecyclerView;
    private AnimalsRecyclerViewAdapter animalsAdapter;
    private FloatingActionButton toHomePageFabButton;
    private EditText searchBarInput;
    private ImageView searchBarIcon;

    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animals);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initializeViews();

        toHomePageFabButton.setOnClickListener(v -> {
            Intent intent = new Intent(AnimalsActivity.this, MainActivity.class);
            startActivity(intent);
        });

        animalsRecyclerView.setAdapter(animalsAdapter);
        animalsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        List<Category> animalsCategories = new ArrayList<>();
        animalsCategories.add(new Category(180, getStringResource(R.string.DOMESTICE), R.drawable.animale_domestice));
        animalsCategories.add(new Category(181, getStringResource(R.string.SALBATICE), R.drawable.animale_salbatice));
        animalsAdapter.setAnimals(animalsCategories);

        searchBarIcon.setOnClickListener(v -> {
            String searchBarInputText = searchBarInput.getText().toString().toLowerCase();
            List<Category> filteredAnimals = animalsCategories.stream().filter(category-> category.getName().toLowerCase().startsWith(searchBarInputText)).collect(Collectors.toList());
            animalsAdapter.setAnimals(filteredAnimals);
            animalsAdapter.notifyDataSetChanged();
        });
    }

    private void initializeViews() {
        animalsRecyclerView = findViewById(R.id.animalsRecyclerView);
        animalsAdapter = new AnimalsRecyclerViewAdapter(this);
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