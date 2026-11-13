package com.project.ohl.presentation.video_section.objects.outside;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.project.ohl.R;
import com.project.ohl.domain.model.Category;
import com.project.ohl.presentation.CategoryNavigation;
import com.project.ohl.presentation.LearningViewModel;
import com.project.ohl.presentation.MainActivity;
import com.project.ohl.presentation.MediaResources;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

import static android.R.id.home;

public class OutsideActivity extends AppCompatActivity {
    private RecyclerView outsideRecyclerView;
    private OutsideRecyclerViewAdapter outsideAdapter;
    private FloatingActionButton toHomePageFabButton;
    private EditText searchBarInput;
    private ImageView searchBarIcon;

    @SuppressLint("NotifyDataSetChanged")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UUID parentId = CategoryNavigation.parentId(this);
        setContentView(R.layout.activity_outside);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initializeViews();

        toHomePageFabButton.setOnClickListener(v -> {
            Intent intent = new Intent(OutsideActivity.this, MainActivity.class);
            startActivity(intent);
        });

        outsideRecyclerView.setAdapter(outsideAdapter);
        outsideRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        LearningViewModel.of(this).<List<Category>>load(this, "categories", repository -> repository.getCategoriesByParentIdAndName(parentId, ""), result -> {
            if (result.error() != null) {
                Toast.makeText(OutsideActivity.this, "Ceva nu a mers bine! Încearcă din nou!", Toast.LENGTH_SHORT).show();

            } else {
                    List<Category> outsideCategories = result.value();
                    MediaResources.bindImages(this, outsideCategories);
                    outsideAdapter.setOutsideObjects(outsideCategories);

            }
        });

        searchBarIcon.setOnClickListener(v -> {
            String searchBarInputText = searchBarInput.getText().toString().toLowerCase(Locale.ROOT);
            LearningViewModel.of(this).<List<Category>>load(this, "categories", repository -> repository.getCategoriesByParentIdAndName(parentId, searchBarInputText), result -> {
            if (result.error() != null) {
                    Toast.makeText(OutsideActivity.this, "Ceva nu a mers bine! Încearcă din nou!", Toast.LENGTH_SHORT).show();

            } else {
                        List<Category> filteredOutsideCategories = result.value();
                        MediaResources.bindImages(this, filteredOutsideCategories);
                        searchBarInput.setText("");
                        outsideAdapter.setOutsideObjects(filteredOutsideCategories);

            }
        });
        });
    }

    private void initializeViews() {
        outsideRecyclerView = findViewById(R.id.outsideRecyclerView);
        outsideAdapter = new OutsideRecyclerViewAdapter(this);
        toHomePageFabButton = findViewById(R.id.toHomePageFabButton);
        searchBarInput = findViewById(R.id.searchBarInput);
        searchBarIcon = findViewById(R.id.searchBarIcon);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == home) getOnBackPressedDispatcher().onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
