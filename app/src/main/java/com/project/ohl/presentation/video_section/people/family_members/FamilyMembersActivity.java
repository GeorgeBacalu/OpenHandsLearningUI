package com.project.ohl.presentation.video_section.people.family_members;

import com.project.ohl.R;
import com.project.ohl.presentation.video_section.CatalogActivity;

public class FamilyMembersActivity extends CatalogActivity {
    @Override
    protected int contentLayout() {
        return R.layout.activity_family_members;
    }

    @Override
    protected int categoryListId() {
        return R.id.familyMembersRecyclerView;
    }
}
