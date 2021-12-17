package com.example.llt_project_separate.video_section.forms_of_address;

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

public class FormsOfAddressActivity extends AppCompatActivity {
    private RecyclerView formsOfAddressRecyclerView;
    private FormsOfAddressRecyclerViewAdapter formsOfAddressAdapter;
    private FloatingActionButton toHomePageFabButton;
    private EditText searchBarInput;
    private ImageView searchBarIcon;

    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forms_of_address);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initializeViews();

        toHomePageFabButton.setOnClickListener(v -> {
            Intent intent = new Intent(FormsOfAddressActivity.this, MainActivity.class);
            startActivity(intent);
        });

        formsOfAddressRecyclerView.setAdapter(formsOfAddressAdapter);
        formsOfAddressRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        List<Category> formsOfAddress = new ArrayList<>();
        formsOfAddress.add(new Category(176, getStringResource(R.string.VA_ROG), R.drawable.va_rog));
        formsOfAddress.add(new Category(177, getStringResource(R.string.MULTUMESC), R.drawable.multumesc));
        formsOfAddress.add(new Category(178, getStringResource(R.string.SCUZE), R.drawable.scuze));
        formsOfAddress.add(new Category(179, getStringResource(R.string.HAI), R.drawable.hai));
        formsOfAddressAdapter.setFormsOfAddress(formsOfAddress);

        searchBarIcon.setOnClickListener(v -> {
            String searchBarInputText = searchBarInput.getText().toString().toLowerCase();
            List<Category> filteredFormsOfAddress = formsOfAddress.stream().filter(category-> category.getName().toLowerCase().startsWith(searchBarInputText)).collect(Collectors.toList());
            formsOfAddressAdapter.setFormsOfAddress(filteredFormsOfAddress);
            formsOfAddressAdapter.notifyDataSetChanged();
        });
    }

    private void initializeViews() {
        formsOfAddressRecyclerView = findViewById(R.id.formsOfAddressRecyclerView);
        formsOfAddressAdapter = new FormsOfAddressRecyclerViewAdapter(this);
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