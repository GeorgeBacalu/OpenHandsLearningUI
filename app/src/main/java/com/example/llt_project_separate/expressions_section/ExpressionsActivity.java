package com.example.llt_project_separate.expressions_section;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.llt_project_separate.MainActivity;
import com.example.llt_project_separate.R;
import com.example.llt_project_separate.general.standard_classes.Category;
import com.example.llt_project_separate.retrofit.UtilsRetrofit;
import com.example.llt_project_separate.video_section.VideoSectionActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExpressionsActivity extends AppCompatActivity {
   private RecyclerView recyclerView;
   private ExpressionsRecyclerViewAdapter adapter;
   private FloatingActionButton homePageButton;
   private EditText searchBar;
   private ImageView searchIcon;

   @RequiresApi(api = Build.VERSION_CODES.N)
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

      UtilsRetrofit.getInstance(this).getCategoriesByParentIdAndSectionIdAndName(new Callback<List<Category>>() {
         @Override
         public void onResponse(@NonNull Call<List<Category>> call, @NonNull Response<List<Category>> response) {
            if (response.isSuccessful()) {
               List<Category> expressions = response.body();
               for (Category category : expressions) {
                  String name = category.getName();
                  int drawableId = getResources().getIdentifier(name.replace(" ", "_").toLowerCase(), "drawable", getPackageName());
                  category.setImageSource(drawableId);
               }
               adapter.setExpressions(expressions);
            } else {
               Toast.makeText(ExpressionsActivity.this, "Obtinerea subcategoriilor expresii a esuat!", Toast.LENGTH_SHORT).show();
            }
         }

         @Override
         public void onFailure(@NonNull Call<List<Category>>call, @NonNull Throwable throwable) {
            Toast.makeText(ExpressionsActivity.this, "Ceva nu a mers bine! Încearcă din nou!", Toast.LENGTH_SHORT).show();
         }
      }, null, 4, "");

      searchIcon.setOnClickListener(v -> {
         String searchBarText = searchBar.getText().toString().toLowerCase();
         UtilsRetrofit.getInstance(this).getCategoriesByParentIdAndSectionIdAndName(new Callback<List<Category>>() {
            @Override
            public void onResponse(@NonNull Call<List<Category>> call, @NonNull Response<List<Category>> response) {
               if(response.isSuccessful()) {
                  List<Category> filteredExpressions = response.body();
                  for (Category category : filteredExpressions) {
                     String name = category.getName();
                     int drawableId = getResources().getIdentifier(name.replace(" ", "_").toLowerCase(), "drawable", getPackageName());
                     category.setImageSource(drawableId);
                  }
                  searchBar.setText("");
                  adapter.setExpressions(filteredExpressions);
                  adapter.notifyDataSetChanged();
               } else {
                  Toast.makeText(ExpressionsActivity.this, "Obtinerea subcategoriilor expresii filtrate a esuat!", Toast.LENGTH_SHORT).show();
               }
            }

            @Override
            public void onFailure(@NonNull Call<List<Category>> call, @NonNull Throwable throwable) {
               Toast.makeText(ExpressionsActivity.this, "Ceva nu a mers bine! Încearcă din nou!", Toast.LENGTH_SHORT).show();
            }
         }, null, 4, searchBarText);
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
      if (item.getItemId() == android.R.id.home) onBackPressed();
      return super.onOptionsItemSelected(item);
   }
}