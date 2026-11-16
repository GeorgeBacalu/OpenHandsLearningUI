package com.project.ohl.presentation.video_section.people;

import com.project.ohl.R;
import com.project.ohl.presentation.video_section.CatalogActivity;

public class PeopleActivity extends CatalogActivity {
    @Override protected int contentLayout() { return R.layout.activity_people; }
    @Override protected int categoryListId() { return R.id.peopleRecyclerView; }
    @Override protected boolean opensChildren() { return true; }
}
