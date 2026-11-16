package com.project.ohl.presentation.video_section.objects.classroom;

import com.project.ohl.R;
import com.project.ohl.presentation.video_section.CatalogActivity;

public class ClassActivity extends CatalogActivity {
    @Override protected int contentLayout() { return R.layout.activity_class; }
    @Override protected int categoryListId() { return R.id.classRecyclerView; }
}
