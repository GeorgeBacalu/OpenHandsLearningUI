package com.project.ohl.presentation.general.favorites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.project.ohl.R;
import com.project.ohl.domain.model.Category;
import com.project.ohl.presentation.AuthenticatedActivity;
import com.project.ohl.presentation.CategorySearch;
import com.project.ohl.presentation.LearningViewModel;
import com.project.ohl.presentation.MediaResources;
import com.project.ohl.presentation.UserSession;
import com.project.ohl.presentation.video_section.VideoSectionActivity;
import com.project.ohl.presentation.video_section.single_category.CategoryRecyclerViewAdapter;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class FavoritesActivity extends AuthenticatedActivity {
    private UUID authenticatedUserId;
    private CategoryRecyclerViewAdapter adapter;
    private EditText searchBar;
    private AlertDialog removeDialog;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authenticatedUserId = UserSession.id();
        setContentView(R.layout.activity_favorites);
        Toolbar toolbar = findViewById(R.id.favorites_toolbar);
        toolbar.setNavigationOnClickListener(view -> getOnBackPressedDispatcher().onBackPressed());
        ViewCompat.setAccessibilityHeading(findViewById(R.id.favorite_header), true);
        ViewCompat.setAccessibilityHeading(findViewById(R.id.firstMessage), true);
        searchBar = findViewById(R.id.search_bar);
        if (savedInstanceState != null) searchBar.setText(savedInstanceState.getString("favorites.query", ""));
        RecyclerView list = findViewById(R.id.recycler_view);
        adapter = new CategoryRecyclerViewAdapter(this, this::confirmRemoval);
        list.setAdapter(adapter);
        list.setLayoutManager(new GridLayoutManager(this, getResources().getInteger(R.integer.video_category_columns)));
        CategorySearch.bind(searchBar, findViewById(R.id.search_icon), this::loadFavorites);
        findViewById(R.id.favorites_retry).setOnClickListener(view -> loadFavorites());
    }

    @Override protected void onResume() {
        super.onResume();
        if (isFinishing() || authenticatedUserId == null) return;
        if (!Objects.equals(authenticatedUserId, UserSession.id())) { recreate(); return; }
        loadFavorites();
    }

    private void loadFavorites() {
        String query = searchBar.getText().toString().strip();
        findViewById(R.id.favorites_progress).setVisibility(View.VISIBLE);
        findViewById(R.id.favorites_error).setVisibility(View.GONE);
        findViewById(R.id.favorites_empty).setVisibility(View.GONE);
        findViewById(R.id.recycler_view).setVisibility(View.GONE);
        ((TextView) findViewById(R.id.favorites_count)).setText(R.string.favorites_loading);
        LearningViewModel.of(this).<List<Category>>load(this, "favorites",
                repository -> repository.getFavoritesByName(authenticatedUserId, query), result -> {
            findViewById(R.id.favorites_progress).setVisibility(View.GONE);
            if (!result.isSuccess() || result.value() == null) {
                ((TextView) findViewById(R.id.favorites_count)).setText(null);
                findViewById(R.id.favorites_error).setVisibility(View.VISIBLE);
                return;
            }
            showFavorites(result.value(), query);
        });
    }

    private void showFavorites(List<Category> categories, String query) {
        MediaResources.bindImages(this, categories);
        adapter.setCategories(categories);
        int count = categories.size();
        ((TextView) findViewById(R.id.favorites_count)).setText(getResources().getQuantityString(
                query.isEmpty() ? R.plurals.favorites_saved_count : R.plurals.favorites_results_count, count, count));
        findViewById(R.id.recycler_view).setVisibility(count == 0 ? View.GONE : View.VISIBLE);
        findViewById(R.id.favorites_empty).setVisibility(count == 0 ? View.VISIBLE : View.GONE);
        if (count != 0) return;
        boolean searching = !query.isEmpty();
        ((TextView) findViewById(R.id.firstMessage)).setText(searching ? R.string.favorites_no_results : R.string.first_message);
        ((TextView) findViewById(R.id.secondMessage)).setText(searching
                ? getString(R.string.favorites_no_results_caption, query) : getString(R.string.second_message));
        ((ImageView) findViewById(R.id.noFavoritesImage)).setImageResource(searching ? R.drawable.ic_search : R.drawable.ic_favorite_filled);
        MaterialButton action = findViewById(R.id.browseCollectionsButton);
        action.setText(searching ? R.string.favorites_clear_search : R.string.browse_collections);
        action.setOnClickListener(view -> {
            if (searching) {
                searchBar.setText("");
                loadFavorites();
            } else startActivity(new Intent(this, VideoSectionActivity.class));
        });
    }

    private void confirmRemoval(Category category) {
        removeDialog = new MaterialAlertDialogBuilder(this)
                .setMessage(getString(R.string.remove_favorite_confirmation, category.getName()))
                .setNegativeButton(R.string.profile_cancel, null)
                .setPositiveButton(R.string.favorites_remove_action, (dialog, which) -> removeFavorite(category))
                .show();
    }

    private void removeFavorite(Category category) {
        adapter.setRemovalPending(true);
        findViewById(R.id.favorites_progress).setVisibility(View.VISIBLE);
        LearningViewModel.of(this).<Category>load(this, "removeFavorite",
                repository -> repository.deleteFavorite(authenticatedUserId, category.getId()), result -> {
            adapter.setRemovalPending(false);
            findViewById(R.id.favorites_progress).setVisibility(View.GONE);
            if (result.isSuccess()) loadFavorites();
            else Toast.makeText(this, R.string.request_failed, Toast.LENGTH_LONG).show();
        });
    }

    @Override protected void onSaveInstanceState(@NonNull Bundle state) {
        state.putString("favorites.query", searchBar.getText().toString());
        super.onSaveInstanceState(state);
    }

    @Override protected void onDestroy() {
        if (removeDialog != null) removeDialog.dismiss();
        super.onDestroy();
    }
}
