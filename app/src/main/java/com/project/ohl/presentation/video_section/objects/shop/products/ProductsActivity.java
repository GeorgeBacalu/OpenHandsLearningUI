package com.project.ohl.presentation.video_section.objects.shop.products;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.project.ohl.R;
import com.project.ohl.presentation.CategoryPageLayout;
import com.project.ohl.domain.model.Category;
import com.project.ohl.presentation.CategoryNavigation;
import com.project.ohl.presentation.LearningViewModel;
import com.project.ohl.presentation.MediaResources;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;

import static android.R.id.home;

public class ProductsActivity extends AppCompatActivity {
   private RecyclerView productsRecyclerView;
   private ProductsRecyclerViewAdapter productsAdapter;
   private EditText searchBarInput;
   private ImageView searchBarIcon;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      UUID parentId = CategoryNavigation.parentId(this);
      setContentView(R.layout.activity_products);
      CategoryPageLayout.configure(this);
      Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

      initializeViews();


      productsRecyclerView.setAdapter(productsAdapter);
      productsRecyclerView.setLayoutManager(new GridLayoutManager(this, getResources().getInteger(R.integer.video_category_columns)));

      LearningViewModel.of(this).<List<Category>>load(this, "categories", repository -> repository.getCategoriesByParentIdAndName(parentId, ""), result -> {
            if (result.error() != null) {
            Toast.makeText(ProductsActivity.this, "Ceva nu a mers bine! Încearcă din nou!", Toast.LENGTH_SHORT).show();

            } else {
               List<Category> products = result.value();
               MediaResources.bindImages(this, products);
               productsAdapter.setProducts(products);

            }
        });

      searchBarIcon.setOnClickListener(v -> {
         String searchBarInputText = searchBarInput.getText().toString().toLowerCase(Locale.ROOT);
         LearningViewModel.of(this).<List<Category>>load(this, "categories", repository -> repository.getCategoriesByParentIdAndName(parentId, searchBarInputText), result -> {
            if (result.error() != null) {
               Toast.makeText(ProductsActivity.this, "Ceva nu a mers bine! Încearcă din nou!", Toast.LENGTH_SHORT).show();

            } else {
                  List<Category> filteredProducts = result.value();
                  MediaResources.bindImages(this, filteredProducts);
                  searchBarInput.setText("");
                  productsAdapter.setProducts(filteredProducts);

            }
        });
      });
   }

   private void initializeViews() {
      productsRecyclerView = findViewById(R.id.productsRecyclerView);
      productsAdapter = new ProductsRecyclerViewAdapter(this);
      searchBarInput = findViewById(R.id.searchBarInput);
      searchBarIcon = findViewById(R.id.searchBarIcon);
   }

   @Override
   public boolean onOptionsItemSelected(@NonNull MenuItem item) {
      if (item.getItemId() == home) getOnBackPressedDispatcher().onBackPressed();
      return super.onOptionsItemSelected(item);
   }
}
