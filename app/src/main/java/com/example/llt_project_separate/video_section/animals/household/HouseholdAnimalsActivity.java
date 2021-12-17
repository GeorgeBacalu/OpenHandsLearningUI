package com.example.llt_project_separate.video_section.animals.household;

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

public class HouseholdAnimalsActivity extends AppCompatActivity {
    private RecyclerView householdAnimalsRecyclerView;
    private HouseholdAnimalsRecyclerViewAdapter householdAnimalsAdapter;
    private FloatingActionButton toHomePageFabButton;
    private EditText searchBarInput;
    private ImageView searchBarIcon;

    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_household_animals);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initializeViews();

        toHomePageFabButton.setOnClickListener(v -> {
            Intent intent = new Intent(HouseholdAnimalsActivity.this, MainActivity.class);
            startActivity(intent);
        });

        householdAnimalsRecyclerView.setAdapter(householdAnimalsAdapter);
        householdAnimalsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        List<Category> householdAnimals = new ArrayList<>();
        householdAnimals.add(new Category(53, getStringResource(R.string.CAINE), R.drawable.caine));
        householdAnimals.add(new Category(54, getStringResource(R.string.PISICA), R.drawable.pisica));
        householdAnimals.add(new Category(55, getStringResource(R.string.PORC), R.drawable.porc));
        householdAnimals.add(new Category(56, getStringResource(R.string.CAPRA), R.drawable.capra));
        householdAnimals.add(new Category(57, getStringResource(R.string.GAINA), R.drawable.gaina));
        householdAnimals.add(new Category(58, getStringResource(R.string.IEPURE), R.drawable.iepure));
        householdAnimals.add(new Category(59, getStringResource(R.string.PUI), R.drawable.pui));
        householdAnimals.add(new Category(60, getStringResource(R.string.VACA), R.drawable.vaca));
        householdAnimals.add(new Category(61, getStringResource(R.string.CAL), R.drawable.cal));
        householdAnimals.add(new Category(62, getStringResource(R.string.COCOS), R.drawable.cocos));
        householdAnimals.add(new Category(63, getStringResource(R.string.GASCA), R.drawable.gasca));
        householdAnimals.add(new Category(64, getStringResource(R.string.OAIE), R.drawable.oaie));
        householdAnimals.add(new Category(65, getStringResource(R.string.RATA), R.drawable.rata));
        householdAnimalsAdapter.setHouseholdAnimals(householdAnimals);

        searchBarIcon.setOnClickListener(v -> {
            String searchBarInputText = searchBarInput.getText().toString().toLowerCase();
            List<Category> filteredHouseholdAnimals = householdAnimals.stream().filter(category-> category.getName().toLowerCase().startsWith(searchBarInputText)).collect(Collectors.toList());
            householdAnimalsAdapter.setHouseholdAnimals(filteredHouseholdAnimals);
            householdAnimalsAdapter.notifyDataSetChanged();
        });
    }

    private void initializeViews() {
        householdAnimalsRecyclerView = findViewById(R.id.householdAnimalsRecyclerView);
        householdAnimalsAdapter = new HouseholdAnimalsRecyclerViewAdapter(this);
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