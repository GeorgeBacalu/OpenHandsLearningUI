package com.project.ohl.presentation.video_section.forms_of_address;

import com.project.ohl.R;
import com.project.ohl.presentation.video_section.CatalogActivity;

public class FormsOfAddressActivity extends CatalogActivity {
    @Override protected int contentLayout() { return R.layout.activity_forms_of_address; }
    @Override protected int categoryListId() { return R.id.formsOfAddressRecyclerView; }
}
