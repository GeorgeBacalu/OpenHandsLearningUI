package com.project.ohl.presentation.video_section.verbs;

import com.project.ohl.R;
import com.project.ohl.presentation.video_section.CatalogActivity;

public class VerbsActivity extends CatalogActivity {
    @Override
    protected int contentLayout() {
        return R.layout.activity_verbs;
    }

    @Override
    protected int categoryListId() {
        return R.id.verbsRecyclerView;
    }
}
