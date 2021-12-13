package com.example.llt_project_separate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FormsOfAddressActivity extends AppCompatActivity {
    private RecyclerView formsOfAddressRecyclerView;
    private FormsOfAddressRecyclerViewAdapter formsOfAddressAdapter;
    private FloatingActionButton toHomePageFabButton;

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
        formsOfAddress.add(new Category(176, "VĂ ROG", R.drawable.va_rog));
        formsOfAddress.add(new Category(177, "MULŢUMESC", R.drawable.multumesc));
        formsOfAddress.add(new Category(178, "SCUZE", R.drawable.scuze));
        formsOfAddress.add(new Category(179, "HAI", R.drawable.hai));
        formsOfAddressAdapter.setFormsOfAddress(formsOfAddress);
    }

    private void initializeViews() {
        formsOfAddressRecyclerView = findViewById(R.id.formsOfAddressRecyclerView);
        formsOfAddressAdapter = new FormsOfAddressRecyclerViewAdapter(this);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}