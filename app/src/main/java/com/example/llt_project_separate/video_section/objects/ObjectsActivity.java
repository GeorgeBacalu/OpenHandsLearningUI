package com.example.llt_project_separate.video_section.objects;

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

public class ObjectsActivity extends AppCompatActivity {
    private RecyclerView objectsRecyclerView;
    private ObjectsRecyclerViewAdapter objectsAdapter;
    private FloatingActionButton toHomePageFabButton;
    private EditText searchBarInput;
    private ImageView searchBarIcon;

    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_objects);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initializeViews();

        toHomePageFabButton.setOnClickListener(v -> {
            Intent intent = new Intent(ObjectsActivity.this, MainActivity.class);
            startActivity(intent);
        });

        objectsRecyclerView.setAdapter(objectsAdapter);
        objectsRecyclerView.setLayoutManager(new GridLayoutManager(this,2));

        List<Category> objects = new ArrayList<>();
        objects.add(new Category(182, getStringResource(R.string.ACASA), R.drawable.acasa));
        objects.add(new Category(183, getStringResource(R.string.AFARA), R.drawable.afara));
        objects.add(new Category(184, getStringResource(R.string.CLASA), R.drawable.clasa));
        objects.add(new Category(185, getStringResource(R.string.MAGAZIN), R.drawable.magazin));
        objects.add(new Category(186, getStringResource(R.string.ORAS), R.drawable.oras));
        objectsAdapter.setObjects(objects);

        searchBarIcon.setOnClickListener(v -> {
            String searchBarInputText = searchBarInput.getText().toString().toLowerCase();
            List<Category> filteredObjects = objects.stream().filter(category-> category.getName().toLowerCase().startsWith(searchBarInputText)).collect(Collectors.toList());
            objectsAdapter.setObjects(filteredObjects);
            objectsAdapter.notifyDataSetChanged();
        });
    }

    private void initializeViews() {
        objectsRecyclerView = findViewById(R.id.objectsRecyclerView);
        objectsAdapter = new ObjectsRecyclerViewAdapter(this);
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