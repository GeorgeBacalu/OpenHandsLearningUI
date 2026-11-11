package com.project.ohl.video_section.all_categories;

import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.project.ohl.R;

import com.project.ohl.general.standard_classes.Category;
import com.project.ohl.retrofit.UtilsRetrofit;
import java.util.List;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
      UtilsRetrofit.getInstance(this).getAllCategories(new Callback<List<Category>>() {
         @Override
         public void onResponse(@NotNull Call<List<Category>> call, @NotNull Response<List<Category>> response) {
            if (response.isSuccessful()) {
               categoriesAdapter.setCategories(response.body());
            } else {
               Toast.makeText(AllCategoriesActivity.this, "Obtinerea categoriilor a esuat!", Toast.LENGTH_SHORT).show();
            }
         }

         @Override
         public void onFailure(@NotNull Call<List<Category>> call, @NotNull Throwable throwable) {
            Toast.makeText(AllCategoriesActivity.this, "Ceva nu a mers bine! Încearcă din nou!", Toast.LENGTH_SHORT).show();
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