package com.project.ohl.presentation.video_section.objects.outside;

import com.project.ohl.R;
import com.project.ohl.presentation.video_section.CatalogActivity;

public class OutsideActivity extends CatalogActivity {
    @Override protected int contentLayout() { return R.layout.activity_outside; }
    @Override protected int categoryListId() { return R.id.outsideRecyclerView; }
}
