package com.project.ohl.presentation.video_section;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import com.project.ohl.R;
import com.project.ohl.presentation.general.favorites.FavoritesActivity;
import java.util.UUID;

public class VideoSectionActivity extends CatalogActivity {
    @Override protected int contentLayout() { return R.layout.activity_video_section; }
    @Override protected int categoryListId() { return R.id.mainCategoriesRecyclerView; }
    @Override protected boolean opensChildren() { return true; }
    @Override protected boolean configureInsets() { return false; }
    @Override protected UUID parentId() { return null; }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.video_section_menu, menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.openFavorites) {
            startActivity(new Intent(this, FavoritesActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
