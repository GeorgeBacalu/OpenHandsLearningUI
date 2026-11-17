package com.project.ohl.presentation.video_section.emotions;

import com.project.ohl.R;
import com.project.ohl.presentation.video_section.CatalogActivity;

public class EmotionsActivity extends CatalogActivity {
    @Override
    protected int contentLayout() {
        return R.layout.activity_emotions;
    }

    @Override
    protected int categoryListId() {
        return R.id.emotionsRecyclerView;
    }
}
