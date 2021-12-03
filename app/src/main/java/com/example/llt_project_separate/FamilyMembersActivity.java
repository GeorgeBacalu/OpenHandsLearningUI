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

public class FamilyMembersActivity extends AppCompatActivity {
    private RecyclerView familyMembersRecyclerView;
    private FamilyMembersRecyclerViewAdapter familyMembersAdapter;
    private FloatingActionButton toHomePageFabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_members);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        familyMembersRecyclerView = findViewById(R.id.familyMembersRecyclerView);
        familyMembersAdapter = new FamilyMembersRecyclerViewAdapter(this);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);

        toHomePageFabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FamilyMembersActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        familyMembersRecyclerView.setAdapter(familyMembersAdapter);
        familyMembersRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        ArrayList<Category> familyMembers = new ArrayList<>();
        familyMembers.add(new Category(135, "TATĂ", R.drawable.caine));
        familyMembers.add(new Category(136, "MAMĂ", R.drawable.caine));
        familyMembers.add(new Category(137, "FRATE", R.drawable.caine));
        familyMembers.add(new Category(138, "SORĂ", R.drawable.caine));
        familyMembers.add(new Category(139, "UNCHI", R.drawable.caine));
        familyMembers.add(new Category(140, "MĂTUŞĂ", R.drawable.caine));
        familyMembers.add(new Category(141, "BUNIC", R.drawable.caine));
        familyMembers.add(new Category(142, "NAŞĂ", R.drawable.caine));
        familyMembersAdapter.setFamilyMembers(familyMembers);
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