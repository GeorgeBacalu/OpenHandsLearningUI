package com.project.ohl.presentation.video_section.objects.shop.products;

import com.project.ohl.R;
import com.project.ohl.presentation.video_section.CatalogActivity;

public class ProductsActivity extends CatalogActivity {
    @Override protected int contentLayout() { return R.layout.activity_products; }
    @Override protected int categoryListId() { return R.id.productsRecyclerView; }
}
