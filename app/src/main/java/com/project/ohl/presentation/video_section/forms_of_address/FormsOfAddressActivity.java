package com.project.ohl.presentation.video_section.forms_of_address;

import com.project.ohl.domain.model.CatalogIds;
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

import com.project.ohl.domain.model.Category;
import com.project.ohl.presentation.MainActivity;
import com.project.ohl.R;
import com.project.ohl.presentation.LearningViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Objects;


public class FormsOfAddressActivity extends AppCompatActivity {
   private RecyclerView formsOfAddressRecyclerView;
   private FormsOfAddressRecyclerViewAdapter formsOfAddressAdapter;
   private FloatingActionButton toHomePageFabButton;
   private EditText searchBarInput;
   private ImageView searchBarIcon;

   @SuppressLint("NotifyDataSetChanged")

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_forms_of_address);
      Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

      initializeViews();

      toHomePageFabButton.setOnClickListener(v -> {
         Intent intent = new Intent(FormsOfAddressActivity.this, MainActivity.class);
         startActivity(intent);
      });

      formsOfAddressRecyclerView.setAdapter(formsOfAddressAdapter);
      formsOfAddressRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

      LearningViewModel.of(this).<List<Category>>load(this, "categories", repository -> repository.getCategoriesByParentIdAndSectionIdAndName(CatalogIds.FORMS_OF_ADDRESS, CatalogIds.SECTION_VIDEO, ""), result -> {
            if (result.error() != null) {
            Toast.makeText(FormsOfAddressActivity.this, "Ceva nu a mers bine! Încearcă din nou!", Toast.LENGTH_SHORT).show();

            } else {
               List<Category> formsOfAddress = result.value();
               com.project.ohl.presentation.MediaResources.bindImages(this, formsOfAddress);
               formsOfAddressAdapter.setFormsOfAddress(formsOfAddress);

            }
        });

      searchBarIcon.setOnClickListener(v -> {
         String searchBarInputText = searchBarInput.getText().toString().toLowerCase(java.util.Locale.ROOT);
         LearningViewModel.of(this).<List<Category>>load(this, "categories", repository -> repository.getCategoriesByParentIdAndSectionIdAndName(CatalogIds.FORMS_OF_ADDRESS, CatalogIds.SECTION_VIDEO, searchBarInputText), result -> {
            if (result.error() != null) {
               Toast.makeText(FormsOfAddressActivity.this, "Ceva nu a mers bine! Încearcă din nou!", Toast.LENGTH_SHORT).show();

            } else {
                  List<Category> filteredFormsOfAddress = result.value();
                  com.project.ohl.presentation.MediaResources.bindImages(this, filteredFormsOfAddress);
                  searchBarInput.setText("");
                  formsOfAddressAdapter.setFormsOfAddress(filteredFormsOfAddress);

            }
        });
      });
   }

   private void initializeViews() {
      formsOfAddressRecyclerView = findViewById(R.id.formsOfAddressRecyclerView);
      formsOfAddressAdapter = new FormsOfAddressRecyclerViewAdapter(this);
      toHomePageFabButton = findViewById(R.id.toHomePageFabButton);
      searchBarInput = findViewById(R.id.searchBarInput);
      searchBarIcon = findViewById(R.id.searchBarIcon);
   }

   @Override
   public boolean onOptionsItemSelected(@NonNull MenuItem item) {
      if (item.getItemId() == android.R.id.home) getOnBackPressedDispatcher().onBackPressed();
      return super.onOptionsItemSelected(item);
   }
}