package com.example.llt_project_separate.video_section.objects.shop.money;

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

public class MoneyActivity extends AppCompatActivity {
    private RecyclerView moneyRecyclerView;
    private MoneyRecyclerViewAdapter moneyAdapter;
    private FloatingActionButton toHomePageFabButton;
    private EditText searchBarInput;
    private ImageView searchBarIcon;

    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(api = Build.VERSION_CODES.N)
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
        moneyUnits.add(new Category(109, getStringResource(R.string.EURO), R.drawable.euro));
        moneyUnits.add(new Category(110, getStringResource(R.string.DOLAR), R.drawable.dolar));
        moneyUnits.add(new Category(111, getStringResource(R.string.MONEDA), R.drawable.moneda));
        // moneyUnits.add(new Category(112, getStringResource(R.string.BANI), R.drawable.bani));
        moneyUnits.add(new Category(113, getStringResource(R.string.BANCNOTA), R.drawable.bancnota));
        moneyAdapter.setMoneyUnits(moneyUnits);

        searchBarIcon.setOnClickListener(v -> {
            String searchBarInputText = searchBarInput.getText().toString().toLowerCase();
            List<Category> filteredMoneyUnits = moneyUnits.stream().filter(category-> category.getName().toLowerCase().startsWith(searchBarInputText)).collect(Collectors.toList());
            searchBarInput.setText("");
            moneyAdapter.setMoneyUnits(filteredMoneyUnits);
            moneyAdapter.notifyDataSetChanged();
        });
    }

    private void initializeViews() {
        moneyRecyclerView = findViewById(R.id.moneyRecyclerView);
        moneyAdapter = new MoneyRecyclerViewAdapter(this);
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