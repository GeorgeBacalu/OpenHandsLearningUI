package com.example.llt_project_separate.video_section.objects.home;

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

public class HomeActivity extends AppCompatActivity {
    private RecyclerView homeRecyclerView;
    private HomeRecyclerViewAdapter homeAdapter;
    private FloatingActionButton toHomePageFabButton;
    private EditText searchBarInput;
    private ImageView searchBarIcon;

    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initializeViews();

        toHomePageFabButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(intent);
        });

        homeRecyclerView.setAdapter(homeAdapter);
        homeRecyclerView.setLayoutManager(new GridLayoutManager(this,2));

        List<Category> homeObjects = new ArrayList<>();
        homeObjects.add(new Category(75, getStringResource(R.string.PAT), R.drawable.pat));
        homeObjects.add(new Category(76, getStringResource(R.string.TELEVIZOR), R.drawable.televizor));
        homeObjects.add(new Category(77, getStringResource(R.string.CALCULATOR), R.drawable.calculator));
        homeObjects.add(new Category(78, getStringResource(R.string.DULAP), R.drawable.dulap));
        homeObjects.add(new Category(79, getStringResource(R.string.LINGURA), R.drawable.lingura));
        homeObjects.add(new Category(80, getStringResource(R.string.SCAUN), R.drawable.scaun));
        homeObjects.add(new Category(81, getStringResource(R.string.MASA), R.drawable.masa));
        homeObjects.add(new Category(82, getStringResource(R.string.FRIGIDER), R.drawable.frigider));
        homeObjects.add(new Category(83, getStringResource(R.string.CUTIT), R.drawable.cutit));
        homeAdapter.setHomeObjects(homeObjects);

        searchBarIcon.setOnClickListener(v -> {
            String searchBarInputText = searchBarInput.getText().toString().toLowerCase();
            List<Category> filteredHomeObjects = homeObjects.stream().filter(category-> category.getName().toLowerCase().startsWith(searchBarInputText)).collect(Collectors.toList());
            homeAdapter.setHomeObjects(filteredHomeObjects);
            homeAdapter.notifyDataSetChanged();
        });
    }

    private void initializeViews() {
        homeRecyclerView = findViewById(R.id.homeRecyclerView);
        homeAdapter = new HomeRecyclerViewAdapter(this);
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