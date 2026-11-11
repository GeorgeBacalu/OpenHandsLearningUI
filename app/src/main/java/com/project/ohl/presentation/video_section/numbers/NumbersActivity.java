package com.project.ohl.presentation.video_section.numbers;

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
import com.project.ohl.domain.model.CatalogCodes;
import com.project.ohl.domain.model.Category;
import com.project.ohl.presentation.LearningViewModel;
import com.project.ohl.presentation.MainActivity;
import com.project.ohl.presentation.MediaResources;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import static android.R.id.home;

public class NumbersActivity extends AppCompatActivity {
   private RecyclerView numbersRecyclerView;
   private NumbersRecyclerViewAdapter numbersAdapter;
   private FloatingActionButton toHomePageFabButton;
   private EditText searchBarInput;
   private ImageView searchBarIcon;

   @SuppressLint("NotifyDataSetChanged")

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_numbers);
      Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

      initializeViews();

      toHomePageFabButton.setOnClickListener(v -> {
         Intent intent = new Intent(NumbersActivity.this, MainActivity.class);
         startActivity(intent);
      });

      numbersRecyclerView.setAdapter(numbersAdapter);
      numbersRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

      LearningViewModel.of(this).<List<Category>>load(this, "categories", repository -> repository.getCategoriesByParentCodeAndSectionCodeAndName(CatalogCodes.NUMBERS, CatalogCodes.SECTION_VIDEO, ""), result -> {
            if (result.error() != null) {
            Toast.makeText(NumbersActivity.this, "Ceva nu a mers bine! Încearcă din nou!", Toast.LENGTH_SHORT).show();

            } else {
               List<Category> numbers = result.value();
               MediaResources.bindImages(this, numbers);
               numbersAdapter.setNumbers(numbers);

            }
        });

      searchBarIcon.setOnClickListener(v -> {
         String searchBarInputText = searchBarInput.getText().toString().toLowerCase(Locale.ROOT);
         LearningViewModel.of(this).<List<Category>>load(this, "categories", repository -> repository.getCategoriesByParentCodeAndSectionCodeAndName(CatalogCodes.NUMBERS, CatalogCodes.SECTION_VIDEO, searchBarInputText), result -> {
            if (result.error() != null) {
               Toast.makeText(NumbersActivity.this, "Ceva nu a mers bine! Încearcă din nou!", Toast.LENGTH_SHORT).show();

            } else {
                  List<Category> filteredNumbers = result.value();
                  MediaResources.bindImages(this, filteredNumbers);
                  searchBarInput.setText("");
                  numbersAdapter.setNumbers(filteredNumbers);

            }
        });
      });
   }

   private void initializeViews() {
      numbersRecyclerView = findViewById(R.id.numbersRecyclerView);
      numbersAdapter = new NumbersRecyclerViewAdapter(this);
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
