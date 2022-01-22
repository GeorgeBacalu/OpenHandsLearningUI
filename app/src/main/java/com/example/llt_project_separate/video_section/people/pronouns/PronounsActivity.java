package com.example.llt_project_separate.video_section.people.pronouns;

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

public class PronounsActivity extends AppCompatActivity {
    private RecyclerView pronounsRecyclerView;
    private PronounsRecyclerViewAdapter pronounsAdapter;
    private FloatingActionButton toHomePageFabButton;
    private EditText searchBarInput;
    private ImageView searchBarIcon;

    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pronouns);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initializeViews();

        toHomePageFabButton.setOnClickListener(v -> {
            Intent intent = new Intent(PronounsActivity.this, MainActivity.class);
            startActivity(intent);
        });

        pronounsRecyclerView.setAdapter(pronounsAdapter);
        pronounsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        List<Category> pronouns = new ArrayList<>();
        pronouns.add(new Category(144, getStringResource(R.string.EU)));
        pronouns.add(new Category(145, getStringResource(R.string.TU)));
        pronouns.add(new Category(146, getStringResource(R.string.EL)));
        pronouns.add(new Category(147, getStringResource(R.string.EA)));
        pronouns.add(new Category(148, getStringResource(R.string.NOI)));
        pronouns.add(new Category(149, getStringResource(R.string.VOI)));
        pronouns.add(new Category(150, getStringResource(R.string.EI_ELE)));
        pronounsAdapter.setPronouns(pronouns);

        searchBarIcon.setOnClickListener(v -> {
            String searchBarInputText = searchBarInput.getText().toString().toLowerCase();
            List<Category> filteredPronouns = pronouns.stream().filter(category-> category.getName().toLowerCase().startsWith(searchBarInputText)).collect(Collectors.toList());
            searchBarInput.setText("");
            pronounsAdapter.setPronouns(filteredPronouns);
            pronounsAdapter.notifyDataSetChanged();
        });
    }

    private void initializeViews() {
        pronounsRecyclerView = findViewById(R.id.pronounsRecyclerView);
        pronounsAdapter = new PronounsRecyclerViewAdapter(this);
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