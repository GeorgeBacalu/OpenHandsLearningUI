package com.example.llt_project_separate.video_section.single_category;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.llt_project_separate.general.standard_classes.Category;
import com.example.llt_project_separate.R;
import com.example.llt_project_separate.general.shared_preferences.Utils;

import java.util.List;
import java.util.Objects;

public class CategoryActivity extends AppCompatActivity {
    public static final String CATEGORY_ID = "categoryId";
    public static final String CATEGORY_NAME = "categoryName";
    public static final String CATEGORY_IMAGE = "categoryImage";

    private TextView categoryName;
    private ImageView categoryImage;
    private Button favoriteButton;
    // private CardView categoryCard;
    // private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initializeViews();

        Intent intent = getIntent();
        if(intent != null) {
            int categoryId = intent.getIntExtra(CATEGORY_ID, -1);
            int imageSubject = intent.getIntExtra(CATEGORY_IMAGE, -1);
            if(categoryId != -1) {
                Category incomingCategory = Utils.getInstance(this).getCategoryById(categoryId);
                if(incomingCategory != null) {
                    setData(incomingCategory, imageSubject);
                    handleAddToFavorite(incomingCategory);
                }
            }
        }
    }

    private void initializeViews() {
        categoryName = findViewById(R.id.categoryName);
        categoryImage = findViewById(R.id.categoryImage);
        favoriteButton = findViewById(R.id.favoriteButton);
    }

    private void setData(Category category, int imageSubject) {
        categoryName.setText(category.getName());
        Glide.with(this).asBitmap().load(imageSubject).into(categoryImage);
    }

    /**
     * Add the category to favorite categories ArrayList
     */

    @SuppressLint("SetTextI18n")
    private void handleAddToFavorite(final Category incomingCategory) {
        List<Category> favoriteCategories = Utils.getInstance(this).getFavoriteCategories();
        boolean existInFavoriteCategories = false;
        for(Category category : favoriteCategories) {
            if (category.getId() == incomingCategory.getId()) {
                existInFavoriteCategories = true;
                break;
            }
        }
        if(existInFavoriteCategories) favoriteButton.setText("Elimina favorit");
        else {
            favoriteButton.setOnClickListener(v -> {
                if(Utils.getInstance(CategoryActivity.this).addedToFavorite(incomingCategory)) {
                    Toast.makeText(CategoryActivity.this, incomingCategory.getName() + " adăugat la favorite", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CategoryActivity.this, "Ceva nu a mers bine! Încearcă din nou!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}