package com.project.ohl.presentation.video_section.expressions;

import com.project.ohl.R;
import com.project.ohl.presentation.video_section.CatalogActivity;

public class ExpressionsActivity extends CatalogActivity {
    @Override
    protected int contentLayout() {
        return R.layout.activity_expressions;
    }

    @Override
    protected int categoryListId() {
        return R.id.recycler_view;
    }

    @Override
    protected int searchInputId() {
        return R.id.search_bar;
    }

    @Override
    protected int searchIconId() {
        return R.id.search_icon;
    }
}
