package com.project.ohl.presentation.video_section.people.pronouns;

import com.project.ohl.domain.model.CatalogIds;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import com.project.ohl.domain.model.Category;
import com.project.ohl.presentation.MainActivity;
import com.project.ohl.R;
import com.project.ohl.presentation.LearningViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Objects;


public class PronounsActivity extends AppCompatActivity {
    private RecyclerView pronounsRecyclerView;
    private PronounsRecyclerViewAdapter pronounsAdapter;
    private FloatingActionButton toHomePageFabButton;
    private EditText searchBarInput;
    private ImageView searchBarIcon;

    @SuppressLint("NotifyDataSetChanged")

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

        LearningViewModel.of(this).<List<Category>>load(this, "categories", repository -> repository.getCategoriesByParentIdAndSectionIdAndName(CatalogIds.PRONOUNS, CatalogIds.SECTION_VIDEO, ""), result -> {
            if (result.error() != null) {
                Toast.makeText(PronounsActivity.this, "Ceva nu a mers bine! Încearcă din nou!", Toast.LENGTH_SHORT).show();

            } else {
                    List<Category> pronouns = result.value();
                    com.project.ohl.presentation.MediaResources.bindImages(this, pronouns);
                    pronounsAdapter.setPronouns(pronouns);

            }
        });

        searchBarIcon.setOnClickListener(v -> {
            String searchBarInputText = searchBarInput.getText().toString().toLowerCase(java.util.Locale.ROOT);
            LearningViewModel.of(this).<List<Category>>load(this, "categories", repository -> repository.getCategoriesByParentIdAndSectionIdAndName(CatalogIds.PRONOUNS, CatalogIds.SECTION_VIDEO, searchBarInputText), result -> {
            if (result.error() != null) {
                    Toast.makeText(PronounsActivity.this, "Ceva nu a mers bine! Încearcă din nou!", Toast.LENGTH_SHORT).show();

            } else {
                        List<Category> filteredPronouns = result.value();
                        com.project.ohl.presentation.MediaResources.bindImages(this, filteredPronouns);
                        searchBarInput.setText("");
                        pronounsAdapter.setPronouns(filteredPronouns);

            }
        });
        });
    }

    private void initializeViews() {
        pronounsRecyclerView = findViewById(R.id.pronounsRecyclerView);
        pronounsAdapter = new PronounsRecyclerViewAdapter(this);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);
        searchBarInput = findViewById(R.id.searchBarInput);
        searchBarIcon = findViewById(R.id.searchBarIcon);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) getOnBackPressedDispatcher().onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}