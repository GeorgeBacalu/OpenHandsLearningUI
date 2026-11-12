package com.project.ohl.presentation.video_section.all_categories;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.project.ohl.R;
import com.project.ohl.domain.model.Category;
import com.project.ohl.presentation.CategoryNavigation;
import com.project.ohl.presentation.LearningViewModel;
import com.project.ohl.presentation.MediaResources;
import com.project.ohl.presentation.video_section.VideoPlayerActivity;
import java.util.UUID;

import java.util.List;
import java.util.Objects;
import static com.project.ohl.presentation.video_section.VideoPlayerActivity.CATEGORY_ID;
import static com.project.ohl.presentation.video_section.VideoPlayerActivity.CATEGORY_IMAGE;
import static com.project.ohl.presentation.video_section.VideoPlayerActivity.CATEGORY_NAME;

import static android.R.id.home;

public class AllCategoriesActivity extends AppCompatActivity {
   private RecyclerView categoriesRecyclerView;
   private AllCategoriesRecyclerViewAdapter categoriesAdapter;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      UUID parentId = CategoryNavigation.parentId(this);

      setContentView(R.layout.activity_all_categories);
      Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

      initializeViews();

      categoriesRecyclerView.setAdapter(categoriesAdapter);
      categoriesRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
      LearningViewModel.of(this).<List<Category>>load(this, "categories", repository -> repository.getCategoriesByParentIdAndName(parentId, ""), result -> {
            if (result.error() != null) {
            Toast.makeText(AllCategoriesActivity.this, "Ceva nu a mers bine! Încearcă din nou!", Toast.LENGTH_SHORT).show();

            } else {
               if (result.value().isEmpty()) {
                  Intent video = new Intent(this, VideoPlayerActivity.class);
                  video.putExtra(CATEGORY_ID, parentId.toString());
                  video.putExtra(CATEGORY_NAME, getIntent().getStringExtra(CATEGORY_NAME));
                  video.putExtra(CATEGORY_IMAGE, getIntent().getIntExtra(CATEGORY_IMAGE, 0));
                  startActivity(video);
                  finish();
                  return;
               }
               MediaResources.bindImages(this, result.value());
               categoriesAdapter.setCategories(result.value());

            }
        });
   }

   private void initializeViews() {
      categoriesRecyclerView = findViewById(R.id.categoriesRecyclerView);
      categoriesAdapter = new AllCategoriesRecyclerViewAdapter(this);
   }

   @Override
   public boolean onOptionsItemSelected(@NonNull MenuItem item) {
      if (item.getItemId() == home) getOnBackPressedDispatcher().onBackPressed();
      return super.onOptionsItemSelected(item);
   }
}