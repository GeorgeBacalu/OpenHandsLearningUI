package com.project.ohl.presentation.general.favorites;

import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.project.ohl.presentation.MainActivity;
import com.project.ohl.R;
import com.project.ohl.domain.model.Category;
import com.project.ohl.presentation.LearningViewModel;
import com.project.ohl.presentation.video_section.VideoSectionActivity;
import com.project.ohl.presentation.video_section.single_category.CategoryRecyclerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Objects;


public class FavoritesActivity extends AppCompatActivity {
   private RecyclerView recyclerView;
   private CategoryRecyclerViewAdapter adapter;
   private FloatingActionButton homePageButton;
   private EditText searchBar;
   private ImageView searchIcon;

   @SuppressLint("NotifyDataSetChanged")

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_favorites);
      Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
      initializeViews();

      homePageButton.setOnClickListener(v -> {
         Intent intent = new Intent(FavoritesActivity.this, MainActivity.class);
         startActivity(intent);
      });

      recyclerView.setAdapter(adapter);
      recyclerView.setLayoutManager(new LinearLayoutManager(FavoritesActivity.this));

      LearningViewModel.of(this).<List<Category>>load(this, "favorites", repository -> repository.getFavoriteCategories(), result -> {
            if (result.error() != null) {
            Toast.makeText(FavoritesActivity.this, "Ceva nu a mers bine! Încearcă din nou!", Toast.LENGTH_SHORT).show();

            } else {
               List<Category> favoriteCategories = result.value();
               com.project.ohl.presentation.MediaResources.bindImages(this, favoriteCategories);
               adapter.setCategories(favoriteCategories);
               if (adapter.getItemCount() == 0) {
                  setContentView(R.layout.no_favorites);
                  Button browseCollectionsButton = findViewById(R.id.browseCollectionsButton);
                  browseCollectionsButton.setOnClickListener(v -> startActivity(new Intent(FavoritesActivity.this, VideoSectionActivity.class)));
               }

            }
        });

      searchIcon.setOnClickListener(v -> {
         String searchBarInputText = searchBar.getText().toString().toLowerCase(java.util.Locale.ROOT);
         LearningViewModel.of(this).<List<Category>>load(this, "favorites", repository -> repository.getFavoritesByName(searchBarInputText), result -> {
            if (result.error() != null) {
               Toast.makeText(FavoritesActivity.this, "Ceva nu a mers bine! Încearcă din nou!", Toast.LENGTH_SHORT).show();

            } else {
                  List<Category> filteredFavoriteCategories = result.value();
                  com.project.ohl.presentation.MediaResources.bindImages(this, filteredFavoriteCategories);
                  searchBar.setText("");
                  adapter.setCategories(filteredFavoriteCategories);
                  adapter.notifyDataSetChanged();

            }
        });
      });
   }

   private void initializeViews() {
      recyclerView = findViewById(R.id.recycler_view);
      adapter = new CategoryRecyclerViewAdapter(this, category ->
            LearningViewModel.of(this).<Category>load(this, "removeFavorite", repository -> repository.deleteFavorite(category.getId()), result -> {
               if (result.isSuccess()) recreate();
               else Toast.makeText(this, R.string.request_failed, Toast.LENGTH_SHORT).show();
            }));
      homePageButton = findViewById(R.id.home_page_button);
      searchBar = findViewById(R.id.search_bar);
      searchIcon = findViewById(R.id.search_icon);
   }

   @Override
   public boolean onOptionsItemSelected(@NonNull MenuItem item) {
      if (item.getItemId() == android.R.id.home) getOnBackPressedDispatcher().onBackPressed();
      return super.onOptionsItemSelected(item);
   }
}