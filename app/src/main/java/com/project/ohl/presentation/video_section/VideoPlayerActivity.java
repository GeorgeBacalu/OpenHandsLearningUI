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
import com.project.ohl.presentation.LearningViewModel;
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
    private VideoPlaybackControls playback;
    private UUID categoryId;
    private int imageSubject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authenticatedUserId = UserSession.id();
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
        imageSubject = intent.getIntExtra(CATEGORY_IMAGE, 0);
        bindImage(imageSubject);
        findViewById(R.id.video_retry).setOnClickListener(view -> loadCategory());
        loadCategory();
    }

    private void loadCategory() {
        playback.showLoading();
        favoriteButton.setEnabled(false);
        LearningViewModel.of(this).<Category>load(this, "category",
                repository -> repository.getCategoryById(categoryId, authenticatedUserId), result -> {
            if (result.isSuccess() && result.value() != null) {
                setData(result.value(), imageSubject);
                handleFavorite(result.value());
            } else playback.showError();
        });
    }

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

    private void setData(Category category, int imageSubject) {
        categoryName.setText(category.getName());
        String key = category.getMediaKey() == null ? MediaResources.resourceName(category.getName()) : category.getMediaKey();
        playback.load(MediaResources.raw(this, key));
        bindImage(imageSubject > 0 ? imageSubject : MediaResources.drawable(this, key));
    }

    private void bindImage(int resourceId) {
        ((View) categoryImage.getParent()).setVisibility(resourceId > 0 ? View.VISIBLE : View.GONE);
        if (resourceId > 0) MediaResources.loadImage(this, resourceId, categoryImage);
    }

    private void handleFavorite(Category category) {
        if (authenticatedUserId == null) {
            favoriteButton.setEnabled(true);
            favoriteButton.setText(R.string.login_to_favorite);
            favoriteButton.setOnClickListener(view -> startActivity(new Intent(this, LoginActivity.class)));
            return;
        }
        boolean favorite = Boolean.TRUE.equals(category.getFavorite());
        favoriteButton.setEnabled(true);
        favoriteButton.setText(favorite ? R.string.remove_favorite : R.string.add_favorite);
        favoriteButton.setIconResource(favorite ? R.drawable.ic_favorite_filled : R.drawable.ic_favorites);
        favoriteButton.setOnClickListener(view -> {
            favoriteButton.setEnabled(false);
            LearningViewModel.of(this).<Category>load(this, "favorite", repository -> favorite
                    ? repository.deleteFavorite(UserSession.id(), category.getId()) : repository.saveFavorite(UserSession.id(), category.getId()), result -> {
                favoriteButton.setEnabled(true);
                if (result.isSuccess() && result.value() != null) handleFavorite(result.value());
                else Toast.makeText(this, R.string.request_failed, Toast.LENGTH_SHORT).show();
            });
        });
    }


    @Override protected void onResume() {
        super.onResume();
        if (!Objects.equals(authenticatedUserId, UserSession.id())) recreate();
    }
}
