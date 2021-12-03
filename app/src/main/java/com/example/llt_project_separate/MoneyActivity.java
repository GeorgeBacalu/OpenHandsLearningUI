package com.example.llt_project_separate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MoneyActivity extends AppCompatActivity {
    private RecyclerView moneyRecyclerView;
    private MoneyRecyclerViewAdapter moneyAdapter;
    private FloatingActionButton toHomePageFabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        moneyRecyclerView = findViewById(R.id.moneyRecyclerView);
        moneyAdapter = new MoneyRecyclerViewAdapter(this);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);

        toHomePageFabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MoneyActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        moneyRecyclerView.setAdapter(moneyAdapter);
        moneyRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        ArrayList<Category> moneyUnits = new ArrayList<>();
        moneyUnits.add(new Category(108, "EURO", R.drawable.caine));
        moneyUnits.add(new Category(109, "DOLAR", R.drawable.caine));
        moneyUnits.add(new Category(110, "MONEDĂ", R.drawable.caine));
        moneyUnits.add(new Category(111, "BANI", R.drawable.caine));
        moneyUnits.add(new Category(112, "BANCNOTĂ", R.drawable.caine));
        moneyAdapter.setMoneyUnits(moneyUnits);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}