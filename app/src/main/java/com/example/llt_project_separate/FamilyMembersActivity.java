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

public class FamilyMembersActivity extends AppCompatActivity {
    private RecyclerView familyMembersRecyclerView;
    private FamilyMembersRecyclerViewAdapter familyMembersAdapter;
    private ImageView toPrevPageButton;
    private FloatingActionButton toHomePageFabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_members);

        familyMembersRecyclerView = findViewById(R.id.familyMembersRecyclerView);
        familyMembersAdapter = new FamilyMembersRecyclerViewAdapter(this);

        toPrevPageButton = findViewById(R.id.toPrevPageButton);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);

        toPrevPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FamilyMembersActivity.this, PeopleActivity.class);
                startActivity(intent);
            }
        });

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
        familyMembers.add(new Category(1, "TATĂ", R.drawable.caine));
        familyMembers.add(new Category(2, "MAMĂ", R.drawable.caine));
        familyMembers.add(new Category(3, "FRATE", R.drawable.caine));
        familyMembers.add(new Category(4, "SORĂ", R.drawable.caine));
        familyMembers.add(new Category(5, "UNCHI", R.drawable.caine));
        familyMembers.add(new Category(6, "MĂTUŞĂ", R.drawable.caine));
        familyMembers.add(new Category(7, "BUNIC", R.drawable.caine));
        familyMembers.add(new Category(8, "NAŞĂ", R.drawable.caine));
        familyMembersAdapter.setFamilyMembers(familyMembers);

    }
}