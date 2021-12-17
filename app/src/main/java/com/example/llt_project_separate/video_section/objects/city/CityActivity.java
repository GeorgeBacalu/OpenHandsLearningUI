package com.example.llt_project_separate.video_section.objects.city;

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

public class CityActivity extends AppCompatActivity {
    private RecyclerView cityRecyclerView;
    private CityRecyclerViewAdapter cityAdapter;
    private FloatingActionButton toHomePageFabButton;
    private EditText searchBarInput;
    private ImageView searchBarIcon;

    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initializeViews();

        toHomePageFabButton.setOnClickListener(v -> {
            Intent intent = new Intent(CityActivity.this, MainActivity.class);
            startActivity(intent);
        });

        cityRecyclerView.setAdapter(cityAdapter);
        cityRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        List<Category> cityObjects = new ArrayList<>();
        cityObjects.add(new Category(126, getStringResource(R.string.SCOALA), R.drawable.scoala));
        cityObjects.add(new Category(127, getStringResource(R.string.LEAGAN), R.drawable.leagan));
        cityObjects.add(new Category(128, getStringResource(R.string.SPITAL), R.drawable.spital));
        cityObjects.add(new Category(129, getStringResource(R.string.AUTOBUZ), R.drawable.autobuz));
        cityObjects.add(new Category(130, getStringResource(R.string.STALP), R.drawable.stalp));
        cityObjects.add(new Category(131, getStringResource(R.string.BLOC), R.drawable.bloc));
        cityObjects.add(new Category(132, getStringResource(R.string.TAXI), R.drawable.taxi));
        cityObjects.add(new Category(133, getStringResource(R.string.LOC_DE_PARCARE), R.drawable.loc_de_parcare));
        cityObjects.add(new Category(134, getStringResource(R.string.TRAMVAI), R.drawable.tramvai));
        cityObjects.add(new Category(135, getStringResource(R.string.POLITIE), R.drawable.politie));
        cityAdapter.setCityObjects(cityObjects);

        searchBarIcon.setOnClickListener(v -> {
            String searchBarInputText = searchBarInput.getText().toString().toLowerCase();
            List<Category> filteredCityObjects = cityObjects.stream().filter(category-> category.getName().toLowerCase().startsWith(searchBarInputText)).collect(Collectors.toList());
            cityAdapter.setCityObjects(filteredCityObjects);
            cityAdapter.notifyDataSetChanged();
        });
    }

    private void initializeViews() {
        cityRecyclerView = findViewById(R.id.cityRecyclerView);
        cityAdapter = new CityRecyclerViewAdapter(this);
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