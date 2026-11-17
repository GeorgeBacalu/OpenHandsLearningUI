package com.project.ohl.presentation.video_section.alphabet;

import com.project.ohl.R;
import com.project.ohl.presentation.video_section.CatalogActivity;

public class AlphabetActivity extends CatalogActivity {
    @Override
    protected int contentLayout() {
        return R.layout.activity_alphabet;
    }

    @Override
    protected int categoryListId() {
        return R.id.alphabetRecyclerView;
    }
}
