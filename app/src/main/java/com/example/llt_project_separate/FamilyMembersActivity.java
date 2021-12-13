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

public class FamilyMembersActivity extends AppCompatActivity {
    private RecyclerView familyMembersRecyclerView;
    private FamilyMembersRecyclerViewAdapter familyMembersAdapter;
    private FloatingActionButton toHomePageFabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_members);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initializeViews();

        toHomePageFabButton.setOnClickListener(v -> {
            Intent intent = new Intent(FamilyMembersActivity.this, MainActivity.class);
            startActivity(intent);
        });

        familyMembersRecyclerView.setAdapter(familyMembersAdapter);
        familyMembersRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        List<Category> familyMembers = new ArrayList<>();
        familyMembers.add(new Category(135, "TATĂ", R.drawable.tata));
        familyMembers.add(new Category(136, "MAMĂ", R.drawable.mama));
        familyMembers.add(new Category(137, "FRATE", R.drawable.frate));
        familyMembers.add(new Category(138, "SORĂ", R.drawable.sora));
        familyMembers.add(new Category(139, "UNCHI", R.drawable.unchi));
        familyMembers.add(new Category(140, "MĂTUŞĂ", R.drawable.matusa));
        familyMembers.add(new Category(141, "BUNIC/BUNICĂ", R.drawable.bunic));
        familyMembers.add(new Category(142, "NAŞ/NAŞĂ", R.drawable.nasa));
        familyMembersAdapter.setFamilyMembers(familyMembers);
    }

    private void initializeViews() {
        familyMembersRecyclerView = findViewById(R.id.familyMembersRecyclerView);
        familyMembersAdapter = new FamilyMembersRecyclerViewAdapter(this);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}