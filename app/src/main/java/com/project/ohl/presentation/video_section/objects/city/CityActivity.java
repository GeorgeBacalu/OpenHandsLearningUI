package com.project.ohl.presentation.video_section.objects.city;

import com.project.ohl.R;
import com.project.ohl.presentation.video_section.CatalogActivity;

public class CityActivity extends CatalogActivity {
    @Override
    protected int contentLayout() {
        return R.layout.activity_city;
    }

    @Override
    protected int categoryListId() {
        return R.id.cityRecyclerView;
    }
}
