package com.project.ohl.presentation.video_section.single_category;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.project.ohl.domain.model.Category;
import com.project.ohl.R;
import com.project.ohl.presentation.LearningViewModel;

import java.util.Objects;

public class CategoryActivity extends AppCompatActivity {
    public static final String CATEGORY_ID = "categoryId";
    public static final String CATEGORY_NAME = "categoryName";
    public static final String CATEGORY_IMAGE = "categoryImage";

    private TextView categoryName;
    private ImageView categoryImage;
    private Button favoriteButton;

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
                favoriteButton.setEnabled(false);
                LearningViewModel.of(this).<Category>load(this, "category", repository -> repository.getCategoryById(categoryId), result -> {
                    if (result.isSuccess() && result.value() != null) {
                        setData(result.value(), imageSubject);
                        handleAddToFavorite(result.value());
                    } else Toast.makeText(this, R.string.request_failed, Toast.LENGTH_SHORT).show();
                });
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


    private void handleAddToFavorite(Category category) {
        boolean favorite = Boolean.TRUE.equals(category.getFavorite());
        favoriteButton.setEnabled(true);
        favoriteButton.setText(favorite ? R.string.remove_favorite : R.string.add_favorite);
        favoriteButton.setOnClickListener(view -> {
            favoriteButton.setEnabled(false);
            LearningViewModel.of(this).<Category>load(this, "favorite", repository -> favorite
                    ? repository.deleteFavorite(category.getId()) : repository.saveFavorite(category.getId()), result -> {
                favoriteButton.setEnabled(true);
                if (result.isSuccess() && result.value() != null) handleAddToFavorite(result.value());
                else Toast.makeText(this, R.string.request_failed, Toast.LENGTH_SHORT).show();
            });
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) getOnBackPressedDispatcher().onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}