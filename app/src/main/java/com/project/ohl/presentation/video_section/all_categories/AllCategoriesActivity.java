package com.project.ohl.presentation.video_section.all_categories;

import android.content.Intent;
import com.project.ohl.R;
import com.project.ohl.presentation.video_section.CatalogActivity;
import com.project.ohl.presentation.video_section.VideoPlayerActivity;
import java.util.UUID;

public class AllCategoriesActivity extends CatalogActivity {
    @Override
    protected int contentLayout() {
        return R.layout.activity_all_categories;
    }

    @Override
    protected int categoryListId() {
        return R.id.categoriesRecyclerView;
    }

    @Override
    protected boolean opensChildren() {
        return true;
    }

    @Override
    protected boolean useCategoryTitle() {
        return true;
    }

    @Override
    protected void onEmptyCatalog(UUID parentId) {
        startActivity(
                new Intent(this, VideoPlayerActivity.class)
                        .putExtra(VideoPlayerActivity.CATEGORY_ID, parentId.toString())
                        .putExtra(
                                VideoPlayerActivity.CATEGORY_NAME,
                                getIntent().getStringExtra(VideoPlayerActivity.CATEGORY_NAME))
                        .putExtra(
                                VideoPlayerActivity.CATEGORY_IMAGE,
                                getIntent().getStringExtra(VideoPlayerActivity.CATEGORY_IMAGE)));
        finish();
    }
}
