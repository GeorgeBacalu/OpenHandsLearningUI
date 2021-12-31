package com.example.llt_project_separate.video_section.colors;

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

public class ColorsActivity extends AppCompatActivity {
    private RecyclerView colorsRecyclerView;
    private ColorsRecyclerViewAdapter colorsAdapter;
    private FloatingActionButton toHomePageFabButton;
    private EditText searchBarInput;
    private ImageView searchBarIcon;

    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colors);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initializeViews();

        toHomePageFabButton.setOnClickListener(v -> {
            Intent intent = new Intent(ColorsActivity.this, MainActivity.class);
            startActivity(intent);
        });

        colorsRecyclerView.setAdapter(colorsAdapter);
        colorsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        List<Category> colors = new ArrayList<>();
        colors.add(new Category(43, getStringResource(R.string.ROSU), R.drawable.rosu));
        colors.add(new Category(44, getStringResource(R.string.PORTOCALIU), R.drawable.portocaliu));
        colors.add(new Category(45, getStringResource(R.string.GALBEN), R.drawable.galben));
        colors.add(new Category(46, getStringResource(R.string.VERDE), R.drawable.verde));
        colors.add(new Category(47, getStringResource(R.string.ALBASTRU), R.drawable.albastru));
        // colors.add(new Category(48, getStringResource(R.string.INDIGO), R.drawable.indigo));
        // colors.add(new Category(49, getStringResource(R.string.VIOLET), R.drawable.violet));
        colors.add(new Category(48, getStringResource(R.string.ALB), R.drawable.alb));
        colors.add(new Category(49, getStringResource(R.string.GRI), R.drawable.gri));
        colors.add(new Category(50, getStringResource(R.string.MARO), R.drawable.maro));
        colors.add(new Category(51, getStringResource(R.string.ROZ), R.drawable.roz));
        // colors.add(new Category(52, getStringResource(R.string.TURCOAZ), R.drawable.turcoaz));
        colors.add(new Category(52, getStringResource(R.string.NEGRU), R.drawable.negru));
        colorsAdapter.setColors(colors);

        searchBarIcon.setOnClickListener(v -> {
            String searchBarInputText = searchBarInput.getText().toString().toLowerCase();
            List<Category> filteredColors = colors.stream().filter(category  -> category.getName().toLowerCase().startsWith(searchBarInputText)).collect(Collectors.toList());
            searchBarInput.setText("");
            colorsAdapter.setColors(filteredColors);
            colorsAdapter.notifyDataSetChanged();
        });
    }

    private void initializeViews() {
        colorsRecyclerView = findViewById(R.id.colorsRecyclerView);
        colorsAdapter = new ColorsRecyclerViewAdapter(this);
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