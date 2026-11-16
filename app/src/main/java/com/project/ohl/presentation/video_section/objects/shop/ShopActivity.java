package com.project.ohl.presentation.video_section.objects.shop;

import com.project.ohl.R;
import com.project.ohl.presentation.video_section.CatalogActivity;

public class ShopActivity extends CatalogActivity {
    @Override protected int contentLayout() { return R.layout.activity_shop; }
    @Override protected int categoryListId() { return R.id.shopRecyclerView; }
    @Override protected boolean opensChildren() { return true; }
}
