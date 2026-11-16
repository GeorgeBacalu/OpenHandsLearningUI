package com.project.ohl.presentation.video_section;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.project.ohl.R;
import com.project.ohl.domain.model.Category;
import com.project.ohl.presentation.LearningViewModel;
import com.project.ohl.presentation.MediaResources;
import com.project.ohl.presentation.general.favorites.FavoritesActivity;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static android.R.id.home;

public class VideoSectionActivity extends AppCompatActivity {
   private RecyclerView mainCategoriesRecyclerView;
   private VideoSectionRecyclerViewAdapter mainCategoriesAdapter;
   private EditText searchBarInput;
   private ImageView searchBarIcon;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      setContentView(R.layout.activity_video_section);
      Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

      initializeViews();

      mainCategoriesRecyclerView.setAdapter(mainCategoriesAdapter);
      mainCategoriesRecyclerView.setLayoutManager(new GridLayoutManager(this, getResources().getInteger(R.integer.video_category_columns)));

      LearningViewModel.of(this).<List<Category>>load(this, "categories", repository -> repository.getCategoriesByParentIdAndName(null, ""), result -> {
            if (result.error() != null) {
            Toast.makeText(VideoSectionActivity.this, "Ceva nu a mers bine! Încearcă din nou!", Toast.LENGTH_SHORT).show();

            } else {
               List<Category> parentCategories = result.value();
               MediaResources.bindImages(this, parentCategories);
               mainCategoriesAdapter.setMainCategories(parentCategories);

            }
        });

      searchBarIcon.setOnClickListener(v -> {
         String searchBarInputText = searchBarInput.getText().toString().toLowerCase(Locale.ROOT);
         LearningViewModel.of(this).<List<Category>>load(this, "categories", repository -> repository.getCategoriesByParentIdAndName(null, searchBarInputText), result -> {
            if (result.error() != null) {
               Toast.makeText(VideoSectionActivity.this, "Ceva nu a mers bine! Încearcă din nou!", Toast.LENGTH_SHORT).show();

            } else {
                  List<Category> filteredParentCategories = result.value();
                  MediaResources.bindImages(this, filteredParentCategories);
                  searchBarInput.setText("");
                  mainCategoriesAdapter.setMainCategories(filteredParentCategories);

            }
        });
      });
   }

   private void initializeViews() {
      mainCategoriesRecyclerView = findViewById(R.id.mainCategoriesRecyclerView);
      mainCategoriesAdapter = new VideoSectionRecyclerViewAdapter(this);
      searchBarInput = findViewById(R.id.searchBarInput);
      searchBarIcon = findViewById(R.id.searchBarIcon);
   }

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.video_section_menu, menu);
      return true;
   }

   @Override
   public boolean onOptionsItemSelected(@NonNull MenuItem item) {
      if (item.getItemId() == R.id.openFavorites) {
         startActivity(new Intent(this, FavoritesActivity.class));
         return true;
      }
      if (item.getItemId() == home) {
         getOnBackPressedDispatcher().onBackPressed();
         return true;
      }
      return super.onOptionsItemSelected(item);
   }
}
