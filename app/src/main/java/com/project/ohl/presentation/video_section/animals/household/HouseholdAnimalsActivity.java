package com.project.ohl.presentation.video_section.animals.household;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.project.ohl.presentation.AuthenticatedActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.project.ohl.R;
import com.project.ohl.presentation.CategoryPageLayout;
import com.project.ohl.domain.model.Category;
import com.project.ohl.presentation.CategoryNavigation;
import com.project.ohl.presentation.LearningViewModel;
import com.project.ohl.presentation.CategorySearch;
import com.project.ohl.presentation.MediaResources;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static android.R.id.home;

public class HouseholdAnimalsActivity extends AuthenticatedActivity {
   private RecyclerView householdAnimalsRecyclerView;
   private HouseholdAnimalsRecyclerViewAdapter householdAnimalsAdapter;
   private EditText searchBarInput;
   private ImageView searchBarIcon;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      UUID parentId = CategoryNavigation.parentId(this);
      setContentView(R.layout.activity_household_animals);
      CategoryPageLayout.configure(this);
      Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

      initializeViews();


      householdAnimalsRecyclerView.setAdapter(householdAnimalsAdapter);
      householdAnimalsRecyclerView.setLayoutManager(new GridLayoutManager(this, getResources().getInteger(R.integer.video_category_columns)));

      LearningViewModel.of(this).<List<Category>>load(this, "categories", repository -> repository.getCategoriesByParentIdAndName(parentId, ""), result -> {
            if (result.error() != null) {
            Toast.makeText(HouseholdAnimalsActivity.this, "Ceva nu a mers bine! Încearcă din nou!", Toast.LENGTH_SHORT).show();

            } else {
               List<Category> householdAnimals = result.value();
               MediaResources.bindImages(this, householdAnimals);
               householdAnimalsAdapter.setHouseholdAnimals(householdAnimals);

            }
        });

      CategorySearch.bind(searchBarInput, searchBarIcon, () -> {
         String searchBarInputText = searchBarInput.getText().toString().strip();
         LearningViewModel.of(this).<List<Category>>load(this, "categories", repository -> repository.getCategoriesByParentIdAndName(parentId, searchBarInputText), result -> {
            if (result.error() != null) {
               Toast.makeText(HouseholdAnimalsActivity.this, "Ceva nu a mers bine! Încearcă din nou!", Toast.LENGTH_SHORT).show();

            } else {
                  List<Category> filteredHouseholdAnimals = result.value();
                  MediaResources.bindImages(this, filteredHouseholdAnimals);
                  householdAnimalsAdapter.setHouseholdAnimals(filteredHouseholdAnimals);

            }
        });
      });
   }

   private void initializeViews() {
      householdAnimalsRecyclerView = findViewById(R.id.householdAnimalsRecyclerView);
      householdAnimalsAdapter = new HouseholdAnimalsRecyclerViewAdapter(this);
      searchBarInput = findViewById(R.id.searchBarInput);
      searchBarIcon = findViewById(R.id.searchBarIcon);
   }

   @Override
   public boolean onOptionsItemSelected(@NonNull MenuItem item) {
      if (item.getItemId() == home) getOnBackPressedDispatcher().onBackPressed();
      return super.onOptionsItemSelected(item);
   }
}
