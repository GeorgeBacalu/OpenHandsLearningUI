package com.project.ohl.presentation.video_section.single_category;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.project.ohl.presentation.AuthenticatedActivity;
import com.project.ohl.R;
import com.project.ohl.presentation.MediaResources;
import com.project.ohl.domain.model.Category;
import com.project.ohl.app.AppViewModels;
import com.project.ohl.presentation.CategoryViewModel;
import com.project.ohl.presentation.UserSession;
import com.project.ohl.presentation.user.LoginActivity;
import java.util.Objects;
import java.util.UUID;

import static android.R.id.home;

public class CategoryActivity extends AuthenticatedActivity {
    public static final String CATEGORY_ID = "categoryId";
    public static final String CATEGORY_NAME = "categoryName";
    public static final String CATEGORY_IMAGE = "categoryImage";

    private TextView categoryName;
    private ImageView categoryImage;
    private Button favoriteButton;
    private UUID authenticatedUserId;
    private CategoryViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authenticatedUserId = UserSession.id();
        model = AppViewModels.get(this, CategoryViewModel.class);
        setContentView(R.layout.activity_category);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        initializeViews();

        Intent intent = getIntent();
        if(intent != null) {
            String categoryIdText = intent.getStringExtra(CATEGORY_ID);
            UUID categoryId = categoryIdText == null ? null : UUID.fromString(categoryIdText);
            String imageSubject = intent.getStringExtra(CATEGORY_IMAGE);
            if(categoryId != null) {
                model.state().observe(this, state -> {
                    if (state.isLoading()) favoriteButton.setEnabled(false);
                    else if (state.isSuccess() && state.value() != null) {
                        setData(state.value(), imageSubject);
                        handleAddToFavorite(state.value());
                    } else if (state.error() != null) Toast.makeText(this, R.string.request_failed, Toast.LENGTH_SHORT).show();
                });
                observeFavorite();
                model.initialize(categoryId, authenticatedUserId);
            }
        }
    }

    private void initializeViews() {
        categoryName = findViewById(R.id.categoryName);
        categoryImage = findViewById(R.id.categoryImage);
        favoriteButton = findViewById(R.id.favoriteButton);
    }

    private void setData(Category category, String imageSubject) {
        categoryName.setText(category.getName());
        MediaResources.loadImage(this, category.getImageUrl() == null ? imageSubject : category.getImageUrl(), categoryImage);
    }


    private void handleAddToFavorite(Category category) {
        if (authenticatedUserId == null) {
            favoriteButton.setEnabled(true);
            favoriteButton.setText(R.string.login_to_favorite);
            favoriteButton.setOnClickListener(view -> startActivity(new Intent(this, LoginActivity.class)));
            return;
        }
        boolean favorite = Boolean.TRUE.equals(category.getFavorite());
        favoriteButton.setEnabled(!model.changingFavorite());
        favoriteButton.setText(favorite ? R.string.remove_favorite : R.string.add_favorite);
        favoriteButton.setOnClickListener(view -> model.toggleFavorite());
    }

    private void observeFavorite() {
        model.favoriteState().observe(this, state -> {
            favoriteButton.setEnabled(!state.isLoading() && model.state().getValue().value() != null);
            if (state.error() != null) {
                model.acknowledgeFavoriteError();
                Toast.makeText(this, R.string.request_failed, Toast.LENGTH_SHORT).show();
            }
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
