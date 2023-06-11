package com.example.llt_project_separate.general.favorites;

import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.llt_project_separate.MainActivity;
import com.example.llt_project_separate.R;
import com.example.llt_project_separate.general.shared_preferences.Utils;
import com.example.llt_project_separate.general.standard_classes.Category;
import com.example.llt_project_separate.retrofit.UtilsRetrofit;
import com.example.llt_project_separate.video_section.VideoPlayerActivity;
import com.example.llt_project_separate.video_section.VideoSectionActivity;
import com.example.llt_project_separate.video_section.single_category.CategoryRecyclerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoritesActivity extends AppCompatActivity {
   private RecyclerView recyclerView;
   private CategoryRecyclerViewAdapter adapter;
   private FloatingActionButton homePageButton;
   private EditText searchBar;
   private ImageView searchIcon;

   @SuppressLint("NotifyDataSetChanged")
   @RequiresApi(api = Build.VERSION_CODES.N)
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

      UtilsRetrofit.getInstance(this).getFavoriteCategories(new Callback<List<Category>>() {
         @Override
         public void onResponse(@NonNull Call<List<Category>> call, @NonNull Response<List<Category>> response) {
            if (response.isSuccessful()) {
               List<Category> favoriteCategories = response.body();
               for (Category category : favoriteCategories) {
                  String name = category.getName();
                  int drawableId = getResources().getIdentifier(name.replace(" ", "_").toLowerCase(), "drawable", getPackageName());
                  category.setImageSource(drawableId);
               }
               adapter.setCategories(favoriteCategories);
               if (adapter.getItemCount() == 0) {
                  setContentView(R.layout.no_favorites);
                  Button browseCollectionsButton = findViewById(R.id.browseCollectionsButton);
                  browseCollectionsButton.setOnClickListener(v -> startActivity(new Intent(FavoritesActivity.this, VideoSectionActivity.class)));
               }
            } else {
               Toast.makeText(FavoritesActivity.this, "Obtinerea categoriilor favorite a esuat!", Toast.LENGTH_SHORT).show();
            }
         }

         @Override
         public void onFailure(@NonNull Call<List<Category>> call, @NonNull Throwable throwable) {
            Toast.makeText(FavoritesActivity.this, "Ceva nu a mers bine! Încearcă din nou!", Toast.LENGTH_SHORT).show();
         }
      });

      searchIcon.setOnClickListener(v -> {
         String searchBarInputText = searchBar.getText().toString().toLowerCase();
         UtilsRetrofit.getInstance(this).getFavoritesByName(new Callback<List<Category>>() {
            @Override
            public void onResponse(@NonNull Call<List<Category>> call, @NonNull Response<List<Category>> response) {
               if (response.isSuccessful()) {
                  List<Category> filteredFavoriteCategories = response.body();
                  for(Category category : filteredFavoriteCategories) {
                     String name = category.getName();
                     int drawableId = getResources().getIdentifier(name.replace(" ", "_").toLowerCase(), "drawable", getPackageName());
                     category.setImageSource(drawableId);
                  }
                  searchBar.setText("");
                  adapter.setCategories(filteredFavoriteCategories);
                  adapter.notifyDataSetChanged();
               } else {
                  Toast.makeText(FavoritesActivity.this, "Filtrarea categoriilor favorite filtrate a esuat!", Toast.LENGTH_SHORT).show();
               }
            }

            @Override
            public void onFailure(@NonNull Call<List<Category>> call, @NonNull Throwable throwable) {
               Toast.makeText(FavoritesActivity.this, "Ceva nu a mers bine! Încearcă din nou!", Toast.LENGTH_SHORT).show();
            }
         }, searchBarInputText);
      });
   }

   private void initializeViews() {
      recyclerView = findViewById(R.id.recycler_view);
      adapter = new CategoryRecyclerViewAdapter(this, "favoriteCategories", this::recreate);
      homePageButton = findViewById(R.id.home_page_button);
      searchBar = findViewById(R.id.search_bar);
      searchIcon = findViewById(R.id.search_icon);
   }

   @Override
   public boolean onOptionsItemSelected(@NonNull MenuItem item) {
      if (item.getItemId() == android.R.id.home) onBackPressed();
      return super.onOptionsItemSelected(item);
   }
}