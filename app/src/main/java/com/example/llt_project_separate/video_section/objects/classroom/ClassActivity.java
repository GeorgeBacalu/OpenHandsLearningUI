package com.example.llt_project_separate.video_section.objects.classroom;

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

public class ClassActivity extends AppCompatActivity {
    private RecyclerView classRecyclerView;
    private ClassRecyclerViewAdapter classAdapter;
    private FloatingActionButton toHomePageFabButton;
    private EditText searchBarInput;
    private ImageView searchBarIcon;

    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initializeViews();

        toHomePageFabButton.setOnClickListener(v -> {
            Intent intent = new Intent(ClassActivity.this, MainActivity.class);
            startActivity(intent);
        });

        classRecyclerView.setAdapter(classAdapter);
        classRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        List<Category> classObjects = new ArrayList<>();
        classObjects.add(new Category(94, "MANUAL", R.drawable.manual));
        classObjects.add(new Category(95, "PIX", R.drawable.pix));
        classObjects.add(new Category(96, "DICŢIONAR", R.drawable.dictionar));
        classObjects.add(new Category(97, "CARTE", R.drawable.carte));
        classObjects.add(new Category(98, "BANCĂ", R.drawable.banca));
        classObjects.add(new Category(99, "TEMĂ", R.drawable.tema));
        classObjects.add(new Category(100, "VIDEO PROIECTOR", R.drawable.video_proiector));
        classObjects.add(new Category(101, "TEST", R.drawable.test));
        classObjects.add(new Category(102, "STILOU", R.drawable.stilou));
        classObjects.add(new Category(103, "TEST GRILĂ", R.drawable.test_grila));
        classObjects.add(new Category(104, "RADIERĂ", R.drawable.radiera));
        classObjects.add(new Category(105, "CRETĂ", R.drawable.creta));
        classObjects.add(new Category(106, "CATEDRĂ", R.drawable.catedra));
        classObjects.add(new Category(107, "CREION", R.drawable.creion));
        classObjects.add(new Category(108, "CAIET", R.drawable.caiet));
        classAdapter.setClassObjects(classObjects);

        searchBarIcon.setOnClickListener(v -> {
            String searchBarInputText = searchBarInput.getText().toString().toLowerCase();
            List<Category> filteredClassObjects = classObjects.stream().filter(category-> category.getName().toLowerCase().startsWith(searchBarInputText)).collect(Collectors.toList());
            classAdapter.setClassObjects(filteredClassObjects);
            classAdapter.notifyDataSetChanged();
        });
    }

    private void initializeViews() {
        classRecyclerView = findViewById(R.id.classRecyclerView);
        classAdapter = new ClassRecyclerViewAdapter(this);
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