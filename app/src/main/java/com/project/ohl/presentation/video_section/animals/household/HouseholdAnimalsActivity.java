package com.project.ohl.presentation.video_section.animals.household;

import com.project.ohl.R;
import com.project.ohl.presentation.video_section.CatalogActivity;

public class HouseholdAnimalsActivity extends CatalogActivity {
    @Override
    protected int contentLayout() {
        return R.layout.activity_household_animals;
    }

    @Override
    protected int categoryListId() {
        return R.id.householdAnimalsRecyclerView;
    }
}
