package com.example.llt_project_separate.video_section.people.family_members;

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

public class FamilyMembersActivity extends AppCompatActivity {
    private RecyclerView familyMembersRecyclerView;
    private FamilyMembersRecyclerViewAdapter familyMembersAdapter;
    private FloatingActionButton toHomePageFabButton;
    private EditText searchBarInput;
    private ImageView searchBarIcon;

    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(api = Build.VERSION_CODES.N)
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
        familyMembers.add(new Category(136, "TATĂ", R.drawable.tata));
        familyMembers.add(new Category(137, "MAMĂ", R.drawable.mama));
        familyMembers.add(new Category(138, "FRATE", R.drawable.frate));
        familyMembers.add(new Category(139, "SORĂ", R.drawable.sora));
        familyMembers.add(new Category(140, "UNCHI", R.drawable.unchi));
        familyMembers.add(new Category(141, "MĂTUŞĂ", R.drawable.matusa));
        familyMembers.add(new Category(142, "BUNIC/BUNICĂ", R.drawable.bunic));
        familyMembers.add(new Category(143, "NAŞ/NAŞĂ", R.drawable.nasa));
        familyMembersAdapter.setFamilyMembers(familyMembers);

        searchBarIcon.setOnClickListener(v -> {
            String searchBarInputText = searchBarInput.getText().toString().toLowerCase();
            List<Category> filteredFamilyMembers = familyMembers.stream().filter(category-> category.getName().toLowerCase().startsWith(searchBarInputText)).collect(Collectors.toList());
            familyMembersAdapter.setFamilyMembers(filteredFamilyMembers);
            familyMembersAdapter.notifyDataSetChanged();
        });
    }

    private void initializeViews() {
        familyMembersRecyclerView = findViewById(R.id.familyMembersRecyclerView);
        familyMembersAdapter = new FamilyMembersRecyclerViewAdapter(this);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);
        searchBarInput = findViewById(R.id.searchBarInput);
        searchBarIcon = findViewById(R.id.searchBarIcon);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}