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
        classObjects.add(new Category(94, getStringResource(R.string.MANUAL), R.drawable.manual));
        classObjects.add(new Category(95, getStringResource(R.string.PIX), R.drawable.pix));
        classObjects.add(new Category(96, getStringResource(R.string.DICTIONAR), R.drawable.dictionar));
        classObjects.add(new Category(97, getStringResource(R.string.CARTE), R.drawable.carte));
        classObjects.add(new Category(98, getStringResource(R.string.BANCA), R.drawable.banca));
        classObjects.add(new Category(99, getStringResource(R.string.TEMA), R.drawable.tema));
        classObjects.add(new Category(100, getStringResource(R.string.VIDEO_PROIECTOR), R.drawable.video_proiector));
        classObjects.add(new Category(101, getStringResource(R.string.TEST), R.drawable.test));
        classObjects.add(new Category(102, getStringResource(R.string.STILOU), R.drawable.stilou));
        classObjects.add(new Category(103, getStringResource(R.string.TEST_GRILA), R.drawable.test_grila));
        classObjects.add(new Category(104, getStringResource(R.string.RADIERA), R.drawable.radiera));
        classObjects.add(new Category(105, getStringResource(R.string.CRETA), R.drawable.creta));
        classObjects.add(new Category(106, getStringResource(R.string.CATEDRA), R.drawable.catedra));
        classObjects.add(new Category(107, getStringResource(R.string.CREION), R.drawable.creion));
        classObjects.add(new Category(108, getStringResource(R.string.CAIET), R.drawable.caiet));
        classAdapter.setClassObjects(classObjects);

        searchBarIcon.setOnClickListener(v -> {
            String searchBarInputText = searchBarInput.getText().toString().toLowerCase();
            List<Category> filteredClassObjects = classObjects.stream().filter(category-> category.getName().toLowerCase().startsWith(searchBarInputText)).collect(Collectors.toList());
            searchBarInput.setText("");
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

    String getStringResource(int intResource) { return getResources().getString(intResource); }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}