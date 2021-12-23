package com.example.llt_project_separate.video_section.people;

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

public class PeopleActivity extends AppCompatActivity {
    private RecyclerView peopleRecyclerView;
    private PeopleRecyclerViewAdapter peopleAdapter;
    private FloatingActionButton toHomePageFabButton;
    private EditText searchBarInput;
    private ImageView searchBarIcon;

    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initializeViews();

        toHomePageFabButton.setOnClickListener(v -> {
            Intent intent = new Intent(PeopleActivity.this, MainActivity.class);
            startActivity(intent);
        });

        peopleRecyclerView.setAdapter(peopleAdapter);
        peopleRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        List<Category> people = new ArrayList<>();
        people.add(new Category(189, getStringResource(R.string.MEMBRII_FAMILIEI), R.drawable.membrii_familiei));
        people.add(new Category(190, getStringResource(R.string.PRONUME), R.drawable.pronume));
        peopleAdapter.setPeople(people);

        searchBarIcon.setOnClickListener(v -> {
            String searchBarInputText = searchBarInput.getText().toString().toLowerCase();
            List<Category> filteredPeople = people.stream().filter(category-> category.getName().toLowerCase().startsWith(searchBarInputText)).collect(Collectors.toList());
            searchBarInput.setText("");
            peopleAdapter.setPeople(filteredPeople);
            peopleAdapter.notifyDataSetChanged();
        });
    }

    private void initializeViews() {
        peopleRecyclerView = findViewById(R.id.peopleRecyclerView);
        peopleAdapter = new PeopleRecyclerViewAdapter(this);
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