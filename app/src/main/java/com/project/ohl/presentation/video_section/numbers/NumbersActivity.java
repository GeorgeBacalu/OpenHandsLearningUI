package com.project.ohl.presentation.video_section.numbers;

import com.project.ohl.R;
import com.project.ohl.presentation.video_section.CatalogActivity;

public class NumbersActivity extends CatalogActivity {
    @Override
    protected int contentLayout() {
        return R.layout.activity_numbers;
    }

    @Override
    protected int categoryListId() {
        return R.id.numbersRecyclerView;
    }
}
