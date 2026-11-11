package com.project.ohl.presentation.general.favorites;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.project.ohl.R;
import com.project.ohl.domain.model.Category;
import com.project.ohl.presentation.LearningViewModel;
import com.project.ohl.presentation.MainActivity;
import com.project.ohl.presentation.MediaResources;
import com.project.ohl.presentation.UserProfiles;
import com.project.ohl.presentation.user.UserListActivity;
import com.project.ohl.presentation.video_section.VideoSectionActivity;
import com.project.ohl.presentation.video_section.single_category.CategoryRecyclerViewAdapter;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

import static android.R.id.home;

public class FavoritesActivity extends AppCompatActivity {
   private final ActivityResultLauncher<Intent> profiles = registerForActivityResult(
         new ActivityResultContracts.StartActivityForResult(), result -> {
            if (UserProfiles.id(this) == null) finish(); else recreate();
         });
   private UUID selectedProfile;
   private RecyclerView recyclerView;
   private CategoryRecyclerViewAdapter adapter;
   private FloatingActionButton homePageButton;
   private EditText searchBar;
   private ImageView searchIcon;

   @SuppressLint("NotifyDataSetChanged")

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      selectedProfile = UserProfiles.id(this);
      setContentView(R.layout.activity_favorites);
      Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
      if (UserProfiles.id(this) == null) {
         profiles.launch(new Intent(this, UserListActivity.class));
         return;
      }
      initializeViews();

      homePageButton.setOnClickListener(v -> {
         Intent intent = new Intent(FavoritesActivity.this, MainActivity.class);
         startActivity(intent);
      });

      recyclerView.setAdapter(adapter);
      recyclerView.setLayoutManager(new LinearLayoutManager(FavoritesActivity.this));

      LearningViewModel.of(this).<List<Category>>load(this, "favorites", repository -> repository.getFavoriteCategories(UserProfiles.id(this)), result -> {
            if (result.error() != null) {
            Toast.makeText(FavoritesActivity.this, "Ceva nu a mers bine! Încearcă din nou!", Toast.LENGTH_SHORT).show();

            } else {
               List<Category> favoriteCategories = result.value();
               MediaResources.bindImages(this, favoriteCategories);
               adapter.setCategories(favoriteCategories);
               if (adapter.getItemCount() == 0) {
                  setContentView(R.layout.no_favorites);
                  Button browseCollectionsButton = findViewById(R.id.browseCollectionsButton);
                  browseCollectionsButton.setOnClickListener(v -> startActivity(new Intent(FavoritesActivity.this, VideoSectionActivity.class)));
               }

            }
        });

      searchIcon.setOnClickListener(v -> {
         String searchBarInputText = searchBar.getText().toString().toLowerCase(Locale.ROOT);
         LearningViewModel.of(this).<List<Category>>load(this, "favorites", repository -> repository.getFavoritesByName(UserProfiles.id(this), searchBarInputText), result -> {
            if (result.error() != null) {
               Toast.makeText(FavoritesActivity.this, "Ceva nu a mers bine! Încearcă din nou!", Toast.LENGTH_SHORT).show();

            } else {
                  List<Category> filteredFavoriteCategories = result.value();
                  MediaResources.bindImages(this, filteredFavoriteCategories);
                  searchBar.setText("");
                  adapter.setCategories(filteredFavoriteCategories);
                  adapter.notifyDataSetChanged();

            }
        });
      });
   }

   private void initializeViews() {
      recyclerView = findViewById(R.id.recycler_view);
      adapter = new CategoryRecyclerViewAdapter(this, category ->
            LearningViewModel.of(this).<Category>load(this, "removeFavorite", repository -> repository.deleteFavorite(UserProfiles.id(this), category.getId()), result -> {
               if (result.isSuccess()) recreate();
               else Toast.makeText(this, R.string.request_failed, Toast.LENGTH_SHORT).show();
            }));
      homePageButton = findViewById(R.id.home_page_button);
      searchBar = findViewById(R.id.search_bar);
      searchIcon = findViewById(R.id.search_icon);
   }

   @Override protected void onResume() {
      super.onResume();
      if (selectedProfile != null && !Objects.equals(selectedProfile, UserProfiles.id(this))) recreate();
   }

   @Override
   public boolean onOptionsItemSelected(@NonNull MenuItem item) {
      if (item.getItemId() == home) getOnBackPressedDispatcher().onBackPressed();
      return super.onOptionsItemSelected(item);
   }
}