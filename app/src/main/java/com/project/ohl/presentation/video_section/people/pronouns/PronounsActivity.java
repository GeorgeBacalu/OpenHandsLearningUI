package com.project.ohl.presentation.video_section.people.pronouns;

import com.project.ohl.R;
import com.project.ohl.presentation.video_section.CatalogActivity;

public class PronounsActivity extends CatalogActivity {
    @Override
    protected int contentLayout() {
        return R.layout.activity_pronouns;
    }

    @Override
    protected int categoryListId() {
        return R.id.pronounsRecyclerView;
    }
}
