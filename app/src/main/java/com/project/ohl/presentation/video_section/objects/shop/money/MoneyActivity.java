package com.project.ohl.presentation.video_section.objects.shop.money;

import com.project.ohl.R;
import com.project.ohl.presentation.video_section.CatalogActivity;

public class MoneyActivity extends CatalogActivity {
    @Override
    protected int contentLayout() {
        return R.layout.activity_money;
    }

    @Override
    protected int categoryListId() {
        return R.id.moneyRecyclerView;
    }
}
