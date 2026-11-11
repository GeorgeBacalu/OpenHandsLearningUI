package com.project.ohl.presentation.video_section.all_categories;

import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.project.ohl.R;

import com.project.ohl.domain.model.Category;
import com.project.ohl.presentation.LearningViewModel;
import java.util.List;
import java.util.Objects;

public class AllCategoriesActivity extends AppCompatActivity {
   private RecyclerView categoriesRecyclerView;
   private AllCategoriesRecyclerViewAdapter categoriesAdapter;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_all_categories);
      Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

      initializeViews();

      categoriesRecyclerView.setAdapter(categoriesAdapter);
      categoriesRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
      LearningViewModel.of(this).<List<Category>>load(this, "categories", repository -> repository.getAllCategories(), result -> {
            if (result.error() != null) {
            Toast.makeText(AllCategoriesActivity.this, "Ceva nu a mers bine! Încearcă din nou!", Toast.LENGTH_SHORT).show();

            } else {
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
      if (item.getItemId() == android.R.id.home) getOnBackPressedDispatcher().onBackPressed();
      return super.onOptionsItemSelected(item);
   }
}