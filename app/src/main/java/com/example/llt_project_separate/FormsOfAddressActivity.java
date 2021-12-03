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

public class FormsOfAddressActivity extends AppCompatActivity {
    private RecyclerView formsOfAddressRecyclerView;
    private FormsOfAddressRecyclerViewAdapter formsOfAddressAdapter;
    private FloatingActionButton toHomePageFabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forms_of_address);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        formsOfAddressRecyclerView = findViewById(R.id.formsOfAddressRecyclerView);
        formsOfAddressAdapter = new FormsOfAddressRecyclerViewAdapter(this);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);

        toHomePageFabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FormsOfAddressActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        formsOfAddressRecyclerView.setAdapter(formsOfAddressAdapter);
        formsOfAddressRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        ArrayList<Category> formsOfAddress = new ArrayList<>();
        formsOfAddress.add(new Category(176, "VĂ ROG", R.drawable.caine));
        formsOfAddress.add(new Category(177, "MULŢUMESC", R.drawable.caine));
        formsOfAddress.add(new Category(178, "SCUZE", R.drawable.caine));
        formsOfAddress.add(new Category(179, "HAI", R.drawable.caine));
        formsOfAddressAdapter.setFormsOfAddress(formsOfAddress);
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