package com.project.ohl.presentation.video_section.objects.home;

import com.project.ohl.R;
import com.project.ohl.presentation.video_section.CatalogActivity;

public class HomeActivity extends CatalogActivity {
    @Override protected int contentLayout() { return R.layout.activity_home; }
    @Override protected int categoryListId() { return R.id.homeRecyclerView; }
}
