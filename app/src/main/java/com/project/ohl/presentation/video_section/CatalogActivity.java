package com.project.ohl.presentation.video_section;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.project.ohl.R;
import com.project.ohl.app.AppViewModels;
import com.project.ohl.presentation.AuthenticatedActivity;
import com.project.ohl.presentation.CatalogViewModel;
import com.project.ohl.presentation.CategoryNavigation;
import com.project.ohl.presentation.CategoryPageLayout;
import com.project.ohl.presentation.CategorySearch;
import java.util.Objects;
import java.util.UUID;

public abstract class CatalogActivity extends AuthenticatedActivity {
    protected abstract int contentLayout();
    protected abstract int categoryListId();
    protected int searchInputId() { return R.id.searchBarInput; }
    protected int searchIconId() { return R.id.searchBarIcon; }
    protected boolean opensChildren() { return false; }
    protected boolean configureInsets() { return true; }
    protected boolean useCategoryTitle() { return false; }
    protected UUID parentId() { return CategoryNavigation.parentId(this); }
    protected void onEmptyCatalog(UUID parentId) {}

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(contentLayout());
        if (configureInsets()) CategoryPageLayout.configure(this);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        if (useCategoryTitle()) {
            String title = getIntent().getStringExtra(VideoPlayerActivity.CATEGORY_NAME);
            if (title != null && !title.isBlank()) setTitle(title);
        }
        UUID selectedParent = parentId();
        var model = AppViewModels.get(this, CatalogViewModel.class);
        var adapter = opensChildren() ? CatalogRecyclerViewAdapter.forCategories(this) : CatalogRecyclerViewAdapter.forVideos(this);
        RecyclerView list = findViewById(categoryListId());
        list.setAdapter(adapter);
        list.setLayoutManager(new GridLayoutManager(this, getResources().getInteger(R.integer.video_category_columns)));
        EditText search = findViewById(searchInputId());
        if (search != null) {
            search.setText(model.query());
            CategorySearch.bind(search, findViewById(searchIconId()), () -> model.search(search.getText().toString()));
        }
        model.state().observe(this, state -> {
            if (state.value() != null) adapter.setCategories(state.value());
            if (state.isSuccess() && state.value() != null && state.value().isEmpty()) onEmptyCatalog(selectedParent);
            if (state.error() != null) Toast.makeText(this, R.string.request_failed, Toast.LENGTH_SHORT).show();
        });
        model.initialize(selectedParent);
    }

    @Override public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getOnBackPressedDispatcher().onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
