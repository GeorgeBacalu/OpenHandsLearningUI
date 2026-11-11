package com.project.ohl.presentation.video_section.animals.household;

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

public class HouseholdAnimalsActivity extends AppCompatActivity {
   private RecyclerView householdAnimalsRecyclerView;
   private HouseholdAnimalsRecyclerViewAdapter householdAnimalsAdapter;
   private FloatingActionButton toHomePageFabButton;
   private EditText searchBarInput;
   private ImageView searchBarIcon;

   @SuppressLint("NotifyDataSetChanged")

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      UUID sectionId = CategoryNavigation.sectionId(this);
      UUID parentId = CategoryNavigation.parentId(this);
      setContentView(R.layout.activity_household_animals);
      Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

      initializeViews();

      toHomePageFabButton.setOnClickListener(v -> {
         Intent intent = new Intent(HouseholdAnimalsActivity.this, MainActivity.class);
         startActivity(intent);
      });

      householdAnimalsRecyclerView.setAdapter(householdAnimalsAdapter);
      householdAnimalsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

      LearningViewModel.of(this).<List<Category>>load(this, "categories", repository -> repository.getCategoriesByParentIdAndSectionIdAndName(parentId, sectionId, ""), result -> {
            if (result.error() != null) {
            Toast.makeText(HouseholdAnimalsActivity.this, "Ceva nu a mers bine! Încearcă din nou!", Toast.LENGTH_SHORT).show();

            } else {
               List<Category> householdAnimals = result.value();
               MediaResources.bindImages(this, householdAnimals);
               householdAnimalsAdapter.setHouseholdAnimals(householdAnimals);

            }
        });

      searchBarIcon.setOnClickListener(v -> {
         String searchBarInputText = searchBarInput.getText().toString().toLowerCase(Locale.ROOT);
         LearningViewModel.of(this).<List<Category>>load(this, "categories", repository -> repository.getCategoriesByParentIdAndSectionIdAndName(parentId, sectionId, searchBarInputText), result -> {
            if (result.error() != null) {
               Toast.makeText(HouseholdAnimalsActivity.this, "Ceva nu a mers bine! Încearcă din nou!", Toast.LENGTH_SHORT).show();

            } else {
                  List<Category> filteredHouseholdAnimals = result.value();
                  MediaResources.bindImages(this, filteredHouseholdAnimals);
                  searchBarInput.setText("");
                  householdAnimalsAdapter.setHouseholdAnimals(filteredHouseholdAnimals);

            }
        });
      });
   }

   private void initializeViews() {
      householdAnimalsRecyclerView = findViewById(R.id.householdAnimalsRecyclerView);
      householdAnimalsAdapter = new HouseholdAnimalsRecyclerViewAdapter(this);
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
