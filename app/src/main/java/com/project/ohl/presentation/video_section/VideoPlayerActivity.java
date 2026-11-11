package com.project.ohl.presentation.video_section;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.project.ohl.domain.model.Category;
import com.project.ohl.R;

import com.project.ohl.presentation.LearningViewModel;
import java.util.Objects;


public class VideoPlayerActivity extends AppCompatActivity {
    public static final String CATEGORY_ID = "categoryId";
    public static final String CATEGORY_NAME = "categoryName";
    public static final String CATEGORY_IMAGE = "categoryImage";

    private TextView categoryName;
    private ImageView categoryImage;
    private Button favoriteButton;
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        initializeViews();

        Intent intent = getIntent();
        if(intent != null) {
            int categoryId = intent.getIntExtra(CATEGORY_ID, -1);
            int imageSubject = intent.getIntExtra(CATEGORY_IMAGE, -1);
            String categoryName = intent.getStringExtra(CATEGORY_NAME);

            if(categoryId != -1) {
                favoriteButton.setEnabled(false);
                LearningViewModel.of(this).<Category>load(this, "category", repository -> repository.getCategoryById(categoryId), result -> {
                    if (result.isSuccess() && result.value() != null) {
                        setData(result.value(), imageSubject);
                        handleFavorite(result.value());
                    } else Toast.makeText(this, R.string.request_failed, Toast.LENGTH_SHORT).show();
                });
            }
        }

        if (intent == null || intent.getStringExtra(CATEGORY_NAME) == null) {
            finish();
            return;
        }
        String videoSubject = intent.getStringExtra(CATEGORY_NAME).toLowerCase(java.util.Locale.ROOT);
        String videoSubjectFormatted = com.project.ohl.presentation.MediaResources.resourceName(videoSubject);

        String uriPath = "android.resource://" + getPackageName() + "/raw/" + videoSubjectFormatted;
        videoView.setVideoPath(uriPath);

        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.start();
    }

    @Override
    protected void onStop() {
        videoView.pause();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        videoView.stopPlayback();
        super.onDestroy();
    }

    private void initializeViews() {
        categoryName = findViewById(R.id.categoryName);
        categoryImage = findViewById(R.id.categoryImage);
        favoriteButton = findViewById(R.id.favoriteButton);
        videoView = findViewById(R.id.videoView);
    }

    private void setData(Category category, int imageSubject) {
        categoryName.setText(category.getName());
        Glide.with(this).asBitmap().load(imageSubject).into(categoryImage);
    }

    private void handleFavorite(Category category) {
        boolean favorite = Boolean.TRUE.equals(category.getFavorite());
        favoriteButton.setEnabled(true);
        favoriteButton.setText(favorite ? R.string.remove_favorite : R.string.add_favorite);
        favoriteButton.setOnClickListener(view -> {
            favoriteButton.setEnabled(false);
            LearningViewModel.of(this).<Category>load(this, "favorite", repository -> favorite
                    ? repository.deleteFavorite(category.getId()) : repository.saveFavorite(category.getId()), result -> {
                favoriteButton.setEnabled(true);
                if (result.isSuccess() && result.value() != null) handleFavorite(result.value());
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