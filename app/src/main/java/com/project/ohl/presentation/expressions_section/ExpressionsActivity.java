package com.project.ohl.presentation.expressions_section;

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

import com.project.ohl.presentation.MainActivity;
import com.project.ohl.R;
import com.project.ohl.domain.model.Category;
import com.project.ohl.presentation.LearningViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Objects;


public class ExpressionsActivity extends AppCompatActivity {
   private RecyclerView recyclerView;
   private ExpressionsRecyclerViewAdapter adapter;
   private FloatingActionButton homePageButton;
   private EditText searchBar;
   private ImageView searchIcon;

   @SuppressLint("NotifyDataSetChanged")
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_expressions_section);
      Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
      initializeViews();

      homePageButton.setOnClickListener(v -> {
         Intent intent = new Intent(ExpressionsActivity.this, MainActivity.class);
         startActivity(intent);
      });

      recyclerView.setAdapter(adapter);
      recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

      LearningViewModel.of(this).<List<Category>>load(this, "categories", repository -> repository.getCategoriesByParentIdAndSectionIdAndName(null, 4, ""), result -> {
            if (result.error() != null) {
            Toast.makeText(ExpressionsActivity.this, "Ceva nu a mers bine! Încearcă din nou!", Toast.LENGTH_SHORT).show();

            } else {
               List<Category> expressions = result.value();
               com.project.ohl.presentation.MediaResources.bindImages(this, expressions);
               adapter.setExpressions(expressions);

            }
        });

      searchIcon.setOnClickListener(v -> {
         String searchBarText = searchBar.getText().toString().toLowerCase(java.util.Locale.ROOT);
         LearningViewModel.of(this).<List<Category>>load(this, "categories", repository -> repository.getCategoriesByParentIdAndSectionIdAndName(null, 4, searchBarText), result -> {
            if (result.error() != null) {
               Toast.makeText(ExpressionsActivity.this, "Ceva nu a mers bine! Încearcă din nou!", Toast.LENGTH_SHORT).show();

            } else {
                  List<Category> filteredExpressions = result.value();
                  com.project.ohl.presentation.MediaResources.bindImages(this, filteredExpressions);
                  searchBar.setText("");
                  adapter.setExpressions(filteredExpressions);
                  adapter.notifyDataSetChanged();

            }
        });
      });
   }

   private void initializeViews() {
      recyclerView = findViewById(R.id.recycler_view);
      adapter = new ExpressionsRecyclerViewAdapter(this);
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