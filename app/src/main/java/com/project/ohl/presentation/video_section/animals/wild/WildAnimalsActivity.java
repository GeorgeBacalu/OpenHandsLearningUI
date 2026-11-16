package com.project.ohl.presentation.video_section.animals.wild;

import com.project.ohl.R;
import com.project.ohl.presentation.video_section.CatalogActivity;

public class WildAnimalsActivity extends CatalogActivity {
    @Override protected int contentLayout() { return R.layout.activity_wild_animals; }
    @Override protected int categoryListId() { return R.id.wildAnimalsRecyclerView; }
}
