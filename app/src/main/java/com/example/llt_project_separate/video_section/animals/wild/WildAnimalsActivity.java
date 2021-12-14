package com.example.llt_project_separate.video_section.animals.wild;

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

public class WildAnimalsActivity extends AppCompatActivity {
    private RecyclerView wildAnimalsRecyclerView;
    private WildAnimalsRecyclerViewAdapter wildAnimalsAdapter;
    private FloatingActionButton toHomePageFabButton;
    private EditText searchBarInput;
    private ImageView searchBarIcon;

    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wild_animals);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initializeViews();

        toHomePageFabButton.setOnClickListener(v -> {
            Intent intent = new Intent(WildAnimalsActivity.this, MainActivity.class);
            startActivity(intent);
        });

        wildAnimalsRecyclerView.setAdapter(wildAnimalsAdapter);
        wildAnimalsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        List<Category> wildAnimals = new ArrayList<>();
        wildAnimals.add(new Category(66, "LUP", R.drawable.lup));
        wildAnimals.add(new Category(67, "VULPE", R.drawable.vulpe));
        wildAnimals.add(new Category(68, "ARICI", R.drawable.arici));
        wildAnimals.add(new Category(69, "LEU", R.drawable.leu));
        wildAnimals.add(new Category(70, "VEVERIŢĂ", R.drawable.veverita));
        wildAnimals.add(new Category(71, "ZIMBRU", R.drawable.zimbru));
        wildAnimals.add(new Category(72, "URS POLAR", R.drawable.urs_polar));
        wildAnimals.add(new Category(73, "ELEFANT", R.drawable.elefant));
        wildAnimals.add(new Category(74, "CROCODIL", R.drawable.crocodil));
        wildAnimalsAdapter.setWildAnimals(wildAnimals);

        searchBarIcon.setOnClickListener(v -> {
            String searchBarInputText = searchBarInput.getText().toString().toLowerCase();
            List<Category> filteredWildAnimals = wildAnimals.stream().filter(category-> category.getName().toLowerCase().startsWith(searchBarInputText)).collect(Collectors.toList());
            wildAnimalsAdapter.setWildAnimals(filteredWildAnimals);
            wildAnimalsAdapter.notifyDataSetChanged();
        });
    }

    private void initializeViews() {
        wildAnimalsRecyclerView = findViewById(R.id.wildAnimalsRecyclerView);
        wildAnimalsAdapter = new WildAnimalsRecyclerViewAdapter(this);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);
        searchBarInput = findViewById(R.id.searchBarInput);
        searchBarIcon = findViewById(R.id.searchBarIcon);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}