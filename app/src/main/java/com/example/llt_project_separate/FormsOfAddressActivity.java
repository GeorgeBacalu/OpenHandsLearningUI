package com.example.llt_project_separate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class FormsOfAddressActivity extends AppCompatActivity {
    private RecyclerView formsOfAddressRecyclerView;
    private FormsOfAddressRecyclerViewAdapter formsOfAddressAdapter;
    private ImageView toPrevPageButton;
    private FloatingActionButton toHomePageFabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forms_of_address);

        formsOfAddressRecyclerView = findViewById(R.id.formsOfAddressRecyclerView);
        formsOfAddressAdapter = new FormsOfAddressRecyclerViewAdapter(this);

        toPrevPageButton = findViewById(R.id.toPrevPageButton);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);

        toPrevPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FormsOfAddressActivity.this, VideoSectionActivity.class);
                startActivity(intent);
            }
        });

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
        formsOfAddress.add(new Category(1, "VĂ ROG", R.drawable.caine));
        formsOfAddress.add(new Category(2, "MULŢUMESC", R.drawable.caine));
        formsOfAddress.add(new Category(3, "SCUZE", R.drawable.caine));
        formsOfAddress.add(new Category(4, "HAI", R.drawable.caine));
        formsOfAddressAdapter.setFormsOfAddress(formsOfAddress);
    }
}