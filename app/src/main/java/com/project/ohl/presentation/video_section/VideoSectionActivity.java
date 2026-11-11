package com.project.ohl.presentation.video_section;

import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.project.ohl.domain.model.Category;
import com.project.ohl.presentation.general.favorites.FavoritesActivity;
import com.project.ohl.presentation.MainActivity;
import com.project.ohl.presentation.general.new_category.NewCategoryActivity;
import com.project.ohl.R;
import com.project.ohl.presentation.LearningViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Objects;

public class VideoSectionActivity extends AppCompatActivity {
   private RecyclerView mainCategoriesRecyclerView;
   private VideoSectionRecyclerViewAdapter mainCategoriesAdapter;
   private Button favoriteButton;
   private FloatingActionButton homePageButton, addCategoryButton;
   private EditText searchBarInput;
   private ImageView searchBarIcon;

   @SuppressLint("NotifyDataSetChanged")

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_video_section);
      Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

      initializeViews();

      favoriteButton.setOnClickListener(v -> {
         Intent intent = new Intent(VideoSectionActivity.this, FavoritesActivity.class);
         startActivity(intent);
      });

      addCategoryButton.setOnClickListener(v -> {
         Intent intent = new Intent(VideoSectionActivity.this, NewCategoryActivity.class);
         startActivity(intent);
      });

      homePageButton.setOnClickListener(v -> {
         Intent intent = new Intent(VideoSectionActivity.this, MainActivity.class);
         startActivity(intent);
      });

      mainCategoriesRecyclerView.setAdapter(mainCategoriesAdapter);
      mainCategoriesRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

      LearningViewModel.of(this).<List<Category>>load(this, "categories", repository -> repository.getCategoriesByParentIdAndSectionIdAndName(null, 1, ""), result -> {
            if (result.error() != null) {
            Toast.makeText(VideoSectionActivity.this, "Ceva nu a mers bine! Încearcă din nou!", Toast.LENGTH_SHORT).show();

            } else {
               List<Category> parentCategories = result.value();
               com.project.ohl.presentation.MediaResources.bindImages(this, parentCategories);
               mainCategoriesAdapter.setMainCategories(parentCategories);

            }
        });

      searchBarIcon.setOnClickListener(v -> {
         String searchBarInputText = searchBarInput.getText().toString().toLowerCase(java.util.Locale.ROOT);
         LearningViewModel.of(this).<List<Category>>load(this, "categories", repository -> repository.getCategoriesByParentIdAndSectionIdAndName(null, 1, searchBarInputText), result -> {
            if (result.error() != null) {
               Toast.makeText(VideoSectionActivity.this, "Ceva nu a mers bine! Încearcă din nou!", Toast.LENGTH_SHORT).show();

            } else {
                  List<Category> filteredParentCategories = result.value();
                  com.project.ohl.presentation.MediaResources.bindImages(this, filteredParentCategories);
                  searchBarInput.setText("");
                  mainCategoriesAdapter.setMainCategories(filteredParentCategories);

            }
        });
      });
   }

   private void initializeViews() {
      mainCategoriesRecyclerView = findViewById(R.id.mainCategoriesRecyclerView);
      mainCategoriesAdapter = new VideoSectionRecyclerViewAdapter(this);
      favoriteButton = findViewById(R.id.favoriteButton);
      homePageButton = findViewById(R.id.homePageButton);
      addCategoryButton = findViewById(R.id.addCategoryButton);
      searchBarInput = findViewById(R.id.searchBarInput);
      searchBarIcon = findViewById(R.id.searchBarIcon);
   }

   @Override
   public boolean onOptionsItemSelected(@NonNull MenuItem item) {
      if (item.getItemId() == android.R.id.home) getOnBackPressedDispatcher().onBackPressed();
      return super.onOptionsItemSelected(item);
   }
}