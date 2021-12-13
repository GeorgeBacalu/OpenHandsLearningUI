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

public class MoneyActivity extends AppCompatActivity {
    private RecyclerView moneyRecyclerView;
    private MoneyRecyclerViewAdapter moneyAdapter;
    private FloatingActionButton toHomePageFabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initializeViews();

        toHomePageFabButton.setOnClickListener(v -> {
            Intent intent = new Intent(MoneyActivity.this, MainActivity.class);
            startActivity(intent);
        });

        moneyRecyclerView.setAdapter(moneyAdapter);
        moneyRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        List<Category> moneyUnits = new ArrayList<>();
        moneyUnits.add(new Category(108, "EURO", R.drawable.euro));
        moneyUnits.add(new Category(109, "DOLAR", R.drawable.dolar));
        moneyUnits.add(new Category(110, "MONEDĂ", R.drawable.moneda));
        moneyUnits.add(new Category(111, "BANI", R.drawable.bani));
        moneyUnits.add(new Category(112, "BANCNOTĂ", R.drawable.bancnota));
        moneyAdapter.setMoneyUnits(moneyUnits);
    }

    private void initializeViews() {
        moneyRecyclerView = findViewById(R.id.moneyRecyclerView);
        moneyAdapter = new MoneyRecyclerViewAdapter(this);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}