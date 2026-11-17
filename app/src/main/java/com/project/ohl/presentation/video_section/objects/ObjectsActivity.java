package com.project.ohl.presentation.video_section.objects;

import com.project.ohl.R;
import com.project.ohl.presentation.video_section.CatalogActivity;

public class ObjectsActivity extends CatalogActivity {
    @Override
    protected int contentLayout() {
        return R.layout.activity_objects;
    }

    @Override
    protected int categoryListId() {
        return R.id.objectsRecyclerView;
    }

    @Override
    protected boolean opensChildren() {
        return true;
    }
}
