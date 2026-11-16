package com.project.ohl.presentation.video_section.animals;

import com.project.ohl.R;
import com.project.ohl.presentation.video_section.CatalogActivity;

public class AnimalsActivity extends CatalogActivity {
    @Override protected int contentLayout() { return R.layout.activity_animals; }
    @Override protected int categoryListId() { return R.id.animalsRecyclerView; }
    @Override protected boolean opensChildren() { return true; }
}
