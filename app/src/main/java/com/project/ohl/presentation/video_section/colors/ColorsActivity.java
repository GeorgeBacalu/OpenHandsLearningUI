package com.project.ohl.presentation.video_section.colors;

import com.project.ohl.R;
import com.project.ohl.presentation.video_section.CatalogActivity;

public class ColorsActivity extends CatalogActivity {
    @Override
    protected int contentLayout() {
        return R.layout.activity_colors;
    }

    @Override
    protected int categoryListId() {
        return R.id.colorsRecyclerView;
    }
}
