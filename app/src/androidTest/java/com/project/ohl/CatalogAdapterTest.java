package com.project.ohl;

import android.content.Intent;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import com.project.ohl.domain.model.Category;
import com.project.ohl.presentation.CategoryNavigation;
import com.project.ohl.presentation.video_section.CatalogRecyclerViewAdapter;
import com.project.ohl.presentation.video_section.VideoPlayerActivity;
import com.project.ohl.presentation.video_section.alphabet.AlphabetActivity;
import java.util.List;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class CatalogAdapterTest {
    @Test public void parentCardsBrowseChildrenAndVideoCardsOpenPlayback() {
        InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> {
            var context = new TestNavigationContext();
            var category = new Category(UUID.fromString("990e2591-117c-3b04-9146-dbdf0ca772b4"), "Alfabet");
            var parents = CatalogRecyclerViewAdapter.forCategories(context);
            parents.setCategories(List.of(category));
            var parentList = recycler(context, parents);
            clickFirst(parentList);
            assertEquals(AlphabetActivity.class.getName(), requireNonNull(context.started.getComponent()).getClassName());
            assertEquals(category.getId().toString(), context.started.getStringExtra(CategoryNavigation.PARENT_ID));

            var videos = CatalogRecyclerViewAdapter.forVideos(context);
            videos.setCategories(List.of(category));
            var videoList = recycler(context, videos);
            clickFirst(videoList);
            assertEquals(VideoPlayerActivity.class.getName(), requireNonNull(context.started.getComponent()).getClassName());
            assertEquals(category.getId().toString(), context.started.getStringExtra(VideoPlayerActivity.CATEGORY_ID));
            assertEquals(category.getName(), context.started.getStringExtra(VideoPlayerActivity.CATEGORY_NAME));
            assertFalse(context.started.hasExtra(CategoryNavigation.PARENT_ID));
            parentList.setAdapter(null);
            videoList.setAdapter(null);
        });
    }

    @Test public void filteredCardsDisplayAndOpenTheCurrentResults() {
        InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> {
            var context = new TestNavigationContext();
            var adapter = CatalogRecyclerViewAdapter.forVideos(context);
            adapter.setCategories(List.of(new Category(UUID.randomUUID(), "Previous result")));
            var recycler = recycler(context, adapter);
            var current = new Category(UUID.randomUUID(), "Filtered result");
            adapter.setCategories(List.of(current));
            layout(recycler);
            TextView label = requireNonNull(recycler.findViewHolderForAdapterPosition(0)).itemView.findViewById(R.id.categoryName);
            assertEquals(current.getName(), label.getText().toString());
            clickFirst(recycler);
            assertEquals(current.getId().toString(), context.started.getStringExtra(VideoPlayerActivity.CATEGORY_ID));
            adapter.setCategories(List.of());
            layout(recycler);
            assertEquals(0, recycler.getChildCount());
            recycler.setAdapter(null);
        });
    }

    private static RecyclerView recycler(TestNavigationContext context, CatalogRecyclerViewAdapter adapter) {
        var recycler = new RecyclerView(context);
        recycler.setLayoutManager(new LinearLayoutManager(context));
        recycler.setItemAnimator(null);
        recycler.setAdapter(adapter);
        layout(recycler);
        return recycler;
    }

    private static void layout(RecyclerView recycler) {
        recycler.measure(View.MeasureSpec.makeMeasureSpec(1080, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(1920, View.MeasureSpec.EXACTLY));
        recycler.layout(0, 0, 1080, 1920);
    }

    private static void clickFirst(RecyclerView recycler) {
        var holder = recycler.findViewHolderForAdapterPosition(0);
        assertNotNull(holder);
        assertTrue(holder.itemView.performClick());
    }

    private static final class TestNavigationContext extends ContextThemeWrapper {
        private Intent started;

        private TestNavigationContext() {
            super(InstrumentationRegistry.getInstrumentation().getTargetContext(), R.style.Theme_OpenHandsLearning);
        }

        @Override public void startActivity(Intent intent) {
            started = intent;
        }
    }
}
