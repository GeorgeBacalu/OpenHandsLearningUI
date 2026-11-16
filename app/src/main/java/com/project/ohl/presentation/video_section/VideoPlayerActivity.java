package com.project.ohl.presentation.video_section;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import com.google.android.material.button.MaterialButton;
import com.project.ohl.presentation.AuthenticatedActivity;
import com.project.ohl.R;
import com.project.ohl.domain.model.Category;
import com.project.ohl.app.AppViewModels;
import com.project.ohl.presentation.CategoryViewModel;
import com.project.ohl.presentation.MediaResources;
import com.project.ohl.presentation.UserSession;
import com.project.ohl.presentation.user.LoginActivity;
import java.util.Objects;
import java.util.UUID;

public class VideoPlayerActivity extends AuthenticatedActivity {
    public static final String CATEGORY_ID = "categoryId";
    public static final String CATEGORY_NAME = "categoryName";
    public static final String CATEGORY_IMAGE = "categoryImage";

    private TextView categoryName;
    private ImageView categoryImage;
    private MaterialButton favoriteButton;
    private UUID authenticatedUserId;
    private CategoryViewModel model;
    private VideoPlaybackControls playback;
    private UUID categoryId;
    private String imageSubject;
    private String boundVideoUrl;
    private boolean videoBound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authenticatedUserId = UserSession.id();
        model = AppViewModels.get(this, CategoryViewModel.class);
        setContentView(R.layout.activity_video_player);
        initializeViews();
        findViewById(R.id.video_player_card).setClipToOutline(true);
        playback = new VideoPlaybackControls(findViewById(android.R.id.content), savedInstanceState);
        Toolbar toolbar = findViewById(R.id.video_toolbar);
        toolbar.setNavigationOnClickListener(view -> getOnBackPressedDispatcher().onBackPressed());
        ViewCompat.setAccessibilityHeading(categoryName, true);

        Intent intent = getIntent();
        if (intent == null || intent.getStringExtra(CATEGORY_NAME) == null || intent.getStringExtra(CATEGORY_ID) == null) {
            finish();
            return;
        }
        try { categoryId = UUID.fromString(intent.getStringExtra(CATEGORY_ID)); }
        catch (IllegalArgumentException invalidId) { finish(); return; }
        categoryName.setText(intent.getStringExtra(CATEGORY_NAME));
        imageSubject = intent.getStringExtra(CATEGORY_IMAGE);
        bindImage(imageSubject);
        findViewById(R.id.video_retry).setOnClickListener(view -> loadCategory());
        model.state().observe(this, state -> {
            if (state.isLoading()) { playback.showLoading(); favoriteButton.setEnabled(false); }
            else if (state.isSuccess() && state.value() != null) {
                setData(state.value(), imageSubject);
                handleFavorite(state.value());
            } else if (state.error() != null) playback.showError();
        });
        observeFavorite();
        model.initialize(categoryId, authenticatedUserId);
    }

    private void loadCategory() { videoBound = false; model.reload(); }

    @Override protected void onStart() {
        super.onStart();
        playback.startTracking();
    }

    @Override
    protected void onStop() {
        playback.pause();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        playback.release();
        super.onDestroy();
    }

    @Override protected void onSaveInstanceState(@NonNull Bundle state) {
        playback.saveState(state);
        super.onSaveInstanceState(state);
    }

    private void initializeViews() {
        categoryName = findViewById(R.id.categoryName);
        categoryImage = findViewById(R.id.categoryImage);
        favoriteButton = findViewById(R.id.favoriteButton);
    }

    private void setData(Category category, String initialImageUrl) {
        categoryName.setText(category.getName());
        if (!videoBound || !Objects.equals(boundVideoUrl, category.getVideoUrl())) {
            videoBound = true;
            boundVideoUrl = category.getVideoUrl();
            playback.load(boundVideoUrl);
        }
        bindImage(category.getImageUrl() == null ? initialImageUrl : category.getImageUrl());
    }

    private void bindImage(String url) {
        ((View) categoryImage.getParent()).setVisibility(url != null ? View.VISIBLE : View.GONE);
        MediaResources.loadImage(this, url, categoryImage);
    }

    private void handleFavorite(Category category) {
        if (authenticatedUserId == null) {
            favoriteButton.setEnabled(true);
            favoriteButton.setText(R.string.login_to_favorite);
            favoriteButton.setOnClickListener(view -> startActivity(new Intent(this, LoginActivity.class)));
            return;
        }
        boolean favorite = Boolean.TRUE.equals(category.getFavorite());
        favoriteButton.setEnabled(!model.changingFavorite());
        favoriteButton.setText(favorite ? R.string.remove_favorite : R.string.add_favorite);
        favoriteButton.setIconResource(favorite ? R.drawable.ic_favorite_filled : R.drawable.ic_favorites);
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
}
