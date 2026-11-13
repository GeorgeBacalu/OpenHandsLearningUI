package com.project.ohl.presentation.video_section.single_category;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.project.ohl.R;
import com.project.ohl.domain.model.Category;
import com.project.ohl.presentation.LearningViewModel;
import com.project.ohl.presentation.UserSession;
import com.project.ohl.presentation.user.LoginActivity;
import java.util.Objects;
import java.util.UUID;

import static android.R.id.home;

public class CategoryActivity extends AppCompatActivity {
    public static final String CATEGORY_ID = "categoryId";
    public static final String CATEGORY_NAME = "categoryName";
    public static final String CATEGORY_IMAGE = "categoryImage";

    private TextView categoryName;
    private ImageView categoryImage;
    private Button favoriteButton;
    private UUID authenticatedUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authenticatedUserId = UserSession.id();
        setContentView(R.layout.activity_category);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initializeViews();

        Intent intent = getIntent();
        if(intent != null) {
            String categoryIdText = intent.getStringExtra(CATEGORY_ID);
            UUID categoryId = categoryIdText == null ? null : UUID.fromString(categoryIdText);
            int imageSubject = intent.getIntExtra(CATEGORY_IMAGE, -1);
            if(categoryId != null) {
                favoriteButton.setEnabled(false);
                LearningViewModel.of(this).<Category>load(this, "category", repository -> repository.getCategoryById(categoryId, authenticatedUserId), result -> {
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
        if (authenticatedUserId == null) {
            favoriteButton.setEnabled(true);
            favoriteButton.setText(R.string.login_to_favorite);
            favoriteButton.setOnClickListener(view -> startActivity(new Intent(this, LoginActivity.class)));
            return;
        }
        boolean favorite = Boolean.TRUE.equals(category.getFavorite());
        favoriteButton.setEnabled(true);
        favoriteButton.setText(favorite ? R.string.remove_favorite : R.string.add_favorite);
        favoriteButton.setOnClickListener(view -> {
            favoriteButton.setEnabled(false);
            LearningViewModel.of(this).<Category>load(this, "favorite", repository -> favorite
                    ? repository.deleteFavorite(UserSession.id(), category.getId()) : repository.saveFavorite(UserSession.id(), category.getId()), result -> {
                favoriteButton.setEnabled(true);
                if (result.isSuccess() && result.value() != null) handleAddToFavorite(result.value());
                else Toast.makeText(this, R.string.request_failed, Toast.LENGTH_SHORT).show();
            });
        });
    }

    @Override protected void onResume() {
        super.onResume();
        if (!Objects.equals(authenticatedUserId, UserSession.id())) recreate();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == home) getOnBackPressedDispatcher().onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}