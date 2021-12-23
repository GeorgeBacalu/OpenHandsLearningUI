package com.example.llt_project_separate.video_section.emotions;

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

public class EmotionsActivity extends AppCompatActivity {
    private RecyclerView emotionsRecyclerView;
    private EmotionsRecyclerViewAdapter emotionsAdapter;
    private FloatingActionButton toHomePageFabButton;
    private EditText searchBarInput;
    private ImageView searchBarIcon;

    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emotions);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initializeViews();

        toHomePageFabButton.setOnClickListener(v -> {
            Intent intent = new Intent(EmotionsActivity.this, MainActivity.class);
            startActivity(intent);
        });

        emotionsRecyclerView.setAdapter(emotionsAdapter);
        emotionsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        List<Category> emotions = new ArrayList<>();
        emotions.add(new Category(151, getStringResource(R.string.VESEL), R.drawable.vesel));
        emotions.add(new Category(152, getStringResource(R.string.SATURAT), R.drawable.saturat));
        emotions.add(new Category(153, getStringResource(R.string.INCRUNTAT), R.drawable.incruntat));
        emotions.add(new Category(154, getStringResource(R.string.TRIST), R.drawable.trist));
        emotions.add(new Category(155, getStringResource(R.string.DEZAMAGIT), R.drawable.dezamagit));
        emotions.add(new Category(156, getStringResource(R.string.NERVOS), R.drawable.nervos));
        emotions.add(new Category(157, getStringResource(R.string.FERICIT), R.drawable.fericit));
        emotions.add(new Category(158, getStringResource(R.string.SUPARAT), R.drawable.suparat));
        emotions.add(new Category(159, getStringResource(R.string.INDURERAT), R.drawable.indurerat));
        emotions.add(new Category(160, getStringResource(R.string.ENTUZIASMAT), R.drawable.entuziasmat));
        emotions.add(new Category(161, getStringResource(R.string.INDRAGOSTIT), R.drawable.indragostit));
        emotions.add(new Category(162, getStringResource(R.string.LINISTIT), R.drawable.linistit));
        emotions.add(new Category(163, getStringResource(R.string.CUMINTE), R.drawable.cuminte));
        emotionsAdapter.setEmotions(emotions);

        searchBarIcon.setOnClickListener(v -> {
            String searchBarInputText = searchBarInput.getText().toString().toLowerCase();
            List<Category> filteredEmotions = emotions.stream().filter(category-> category.getName().toLowerCase().startsWith(searchBarInputText)).collect(Collectors.toList());
            searchBarInput.setText("");
            emotionsAdapter.setEmotions(filteredEmotions);
            emotionsAdapter.notifyDataSetChanged();
        });
    }

    private void initializeViews() {
        emotionsRecyclerView = findViewById(R.id.emotionsRecyclerView);
        emotionsAdapter = new EmotionsRecyclerViewAdapter(this);
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