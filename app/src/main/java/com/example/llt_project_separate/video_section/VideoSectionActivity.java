package com.example.llt_project_separate.video_section;

import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.llt_project_separate.general.standard_classes.Category;
import com.example.llt_project_separate.general.favorites.FavoritesActivity;
import com.example.llt_project_separate.MainActivity;
import com.example.llt_project_separate.general.new_category.NewCategoryActivity;
import com.example.llt_project_separate.R;
import com.example.llt_project_separate.general.shared_preferences.Utils;
import com.example.llt_project_separate.retrofit.UtilsRetrofit;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Objects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoSectionActivity extends AppCompatActivity {
   private RecyclerView mainCategoriesRecyclerView;
   private VideoSectionRecyclerViewAdapter mainCategoriesAdapter;
   private Button favoriteButton;
   private FloatingActionButton homePageButton, addCategoryButton;
   private EditText searchBarInput;
   private ImageView searchBarIcon;

   @SuppressLint("NotifyDataSetChanged")
   @RequiresApi(api = Build.VERSION_CODES.N)
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

      UtilsRetrofit.getInstance(this).getCategoriesByParentIdAndSectionIdAndName(new Callback<List<Category>>() {
         @Override
         public void onResponse(@NonNull Call<List<Category>> call, @NonNull Response<List<Category>> response) {
            if (response.isSuccessful()) {
               List<Category> parentCategories = response.body();
               for (Category category : parentCategories) {
                  String name = category.getName();
                  int drawableId = getResources().getIdentifier(name.replace(" ", "_").toLowerCase(), "drawable", getPackageName());
                  category.setImageSource(drawableId);
               }
               mainCategoriesAdapter.setMainCategories(parentCategories);
            } else {
               Toast.makeText(VideoSectionActivity.this, "Obtinerea subcategoriilor animale a esuat!", Toast.LENGTH_SHORT).show();
            }
         }

         @Override
         public void onFailure(@NonNull Call<List<Category>> call, @NonNull Throwable throwable) {
            Toast.makeText(VideoSectionActivity.this, "Ceva nu a mers bine! Încearcă din nou!", Toast.LENGTH_SHORT).show();
         }
      }, null, 1, "");

      searchBarIcon.setOnClickListener(v -> {
         String searchBarInputText = searchBarInput.getText().toString().toLowerCase();
         UtilsRetrofit.getInstance(this).getCategoriesByParentIdAndSectionIdAndName(new Callback<List<Category>>() {
            @Override
            public void onResponse(@NonNull Call<List<Category>> call, @NonNull Response<List<Category>> response) {
               if (response.isSuccessful()) {
                  List<Category> filteredParentCategories = response.body();
                  for (Category category : filteredParentCategories) {
                     String name = category.getName();
                     int drawableId = getResources().getIdentifier(name.replace(" ", "_").toLowerCase(), "drawable", getPackageName());
                     category.setImageSource(drawableId);
                  }
                  searchBarInput.setText("");
                  mainCategoriesAdapter.setMainCategories(filteredParentCategories);
                  mainCategoriesAdapter.notifyDataSetChanged();
               } else {
                  Toast.makeText(VideoSectionActivity.this, "Obtinerea subcategoriilor animale filtrate a esuat!", Toast.LENGTH_SHORT).show();
               }
            }

            @Override
            public void onFailure(@NonNull Call<List<Category>> call, @NonNull Throwable throwable) {
               Toast.makeText(VideoSectionActivity.this, "Ceva nu a mers bine! Încearcă din nou!", Toast.LENGTH_SHORT).show();
            }
         }, null, 1, searchBarInputText);
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
      if (item.getItemId() == android.R.id.home) onBackPressed();
      return super.onOptionsItemSelected(item);
   }
}