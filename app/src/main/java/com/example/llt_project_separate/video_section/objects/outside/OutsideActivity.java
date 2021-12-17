package com.example.llt_project_separate.video_section.objects.outside;

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

public class OutsideActivity extends AppCompatActivity {
    private RecyclerView outsideRecyclerView;
    private OutsideRecyclerViewAdapter outsideAdapter;
    private FloatingActionButton toHomePageFabButton;
    private EditText searchBarInput;
    private ImageView searchBarIcon;

    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outside);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initializeViews();

        toHomePageFabButton.setOnClickListener(v -> {
            Intent intent = new Intent(OutsideActivity.this, MainActivity.class);
            startActivity(intent);
        });

        outsideRecyclerView.setAdapter(outsideAdapter);
        outsideRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        List<Category> outsideObjects = new ArrayList<>();
        outsideObjects.add(new Category(84, getStringResource(R.string.TOPOR), R.drawable.topor));
        outsideObjects.add(new Category(85, getStringResource(R.string.POARTA), R.drawable.poarta));
        outsideObjects.add(new Category(86, getStringResource(R.string.GEAM), R.drawable.geam));
        outsideObjects.add(new Category(87, getStringResource(R.string.FURCA), R.drawable.furca));
        outsideObjects.add(new Category(88, getStringResource(R.string.COPAC), R.drawable.copac));
        outsideObjects.add(new Category(89, getStringResource(R.string.GARD), R.drawable.gard));
        outsideObjects.add(new Category(90, getStringResource(R.string.COASA), R.drawable.coasa));
        outsideObjects.add(new Category(91, getStringResource(R.string.BEC), R.drawable.bec));
        outsideObjects.add(new Category(92, getStringResource(R.string.LOPATA), R.drawable.lopata));
        outsideObjects.add(new Category(93, getStringResource(R.string.USA), R.drawable.usa));
        outsideAdapter.setOutsideObjects(outsideObjects);

        searchBarIcon.setOnClickListener(v -> {
            String searchBarInputText = searchBarInput.getText().toString().toLowerCase();
            List<Category> filteredOutsideObjects = outsideObjects.stream().filter(category-> category.getName().toLowerCase().startsWith(searchBarInputText)).collect(Collectors.toList());
            outsideAdapter.setOutsideObjects(filteredOutsideObjects);
            outsideAdapter.notifyDataSetChanged();
        });
    }

    private void initializeViews() {
        outsideRecyclerView = findViewById(R.id.outsideRecyclerView);
        outsideAdapter = new OutsideRecyclerViewAdapter(this);
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