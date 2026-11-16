package com.project.ohl;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.graphics.ColorUtils;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import com.project.ohl.domain.model.Category;
import com.project.ohl.presentation.video_section.VideoPlayerActivity;
import com.project.ohl.presentation.video_section.single_category.CategoryRecyclerViewAdapter;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/** Checks favorites without reading or changing the signed-in user's saved collection. */
@RunWith(AndroidJUnit4.class)
public class FavoritesLayoutTest {
    private static class RecordingContext extends ContextThemeWrapper {
        Intent opened;
        RecordingContext(Context base) { super(base, R.style.Theme_OpenHandsLearning_Home); }
        @Override public void startActivity(Intent intent) { opened = intent; }
    }

    @Test public void cardsStayReadableAndActionsTargetTheChosenCategory() {
        render(false, 1f, "saved", "day");
        render(true, 1f, "saved", "night");
        render(false, 1.6f, "saved", "large-text-day");
        render(true, 1.6f, "saved", "large-text-night");
    }

    @Test public void emptyCollectionAndEmptySearchHaveDistinctReadableStates() {
        for (boolean night : new boolean[]{false, true}) {
            render(night, 1f, "empty", "empty-" + (night ? "night" : "day"));
            render(night, 1f, "search", "search-" + (night ? "night" : "day"));
        }
    }

    private void render(boolean night, float scale, String state, String suffix) {
        var instrumentation = InstrumentationRegistry.getInstrumentation();
        instrumentation.runOnMainSync(() -> {
            var target = instrumentation.getTargetContext();
            var config = new Configuration(target.getResources().getConfiguration());
            config.uiMode = (config.uiMode & ~Configuration.UI_MODE_NIGHT_MASK)
                    | (night ? Configuration.UI_MODE_NIGHT_YES : Configuration.UI_MODE_NIGHT_NO);
            config.fontScale = scale;
            config.setLocale(Locale.forLanguageTag("ro"));
            var context = new RecordingContext(target.createConfigurationContext(config));
            assertTrue("Header title has strong contrast", ColorUtils.calculateContrast(context.getColor(R.color.home_text),
                    context.getColor(R.color.favorites_header_surface)) >= 7);
            assertTrue("Header caption remains readable", ColorUtils.calculateContrast(context.getColor(R.color.home_muted),
                    context.getColor(R.color.favorites_header_surface)) >= 4.5);
            assertTrue("Favorite heart stays visible", ColorUtils.calculateContrast(context.getColor(R.color.favorites_accent),
                    context.getColor(R.color.catalog_card_surface)) >= 4.5);
            View root = LayoutInflater.from(context).inflate(R.layout.activity_favorites, null);
            root.setBackgroundColor(context.getColor(R.color.home_background));
            RecyclerView list = root.findViewById(R.id.recycler_view);
            var grid = new GridLayoutManager(context, context.getResources().getInteger(R.integer.video_category_columns));
            list.setLayoutManager(grid);
            assertEquals("Favorites use the same three-column grid as video categories", 3, grid.getSpanCount());
            list.setItemAnimator(null);
            var removed = new AtomicReference<Category>();
            var adapter = new CategoryRecyclerViewAdapter(context, removed::set);
            list.setAdapter(adapter);
            var categories = List.of(
                    new Category(UUID.randomUUID(), "Acesta este colegul meu", R.drawable.acesta_este_colegul_meu),
                    new Category(UUID.randomUUID(), "Bunic / Bunică", R.drawable.bunic_bunica),
                    new Category(UUID.randomUUID(), "Formule de adresare", R.drawable.formule_de_adresare),
                    new Category(UUID.randomUUID(), "Alfabet", R.drawable.alfabet),
                    new Category(UUID.randomUUID(), "Emoții", R.drawable.emotii),
                    new Category(UUID.randomUUID(), "Expresii", R.drawable.expressions_section));
            boolean saved = state.equals("saved"), searching = state.equals("search");
            adapter.setCategories(saved ? categories : List.of());
            ((TextView) root.findViewById(R.id.favorites_count)).setText(context.getResources().getQuantityString(
                    searching ? R.plurals.favorites_results_count : R.plurals.favorites_saved_count,
                    saved ? categories.size() : 0, saved ? categories.size() : 0));
            list.setVisibility(saved ? View.VISIBLE : View.GONE);
            root.findViewById(R.id.favorites_empty).setVisibility(saved ? View.GONE : View.VISIBLE);
            if (searching) {
                ((EditText) root.findViewById(R.id.search_bar)).setText("zz");
                ((TextView) root.findViewById(R.id.firstMessage)).setText(R.string.favorites_no_results);
                ((TextView) root.findViewById(R.id.secondMessage)).setText(context.getString(R.string.favorites_no_results_caption, "zz"));
                ((TextView) root.findViewById(R.id.browseCollectionsButton)).setText(R.string.favorites_clear_search);
                ((ImageView) root.findViewById(R.id.noFavoritesImage)).setImageResource(R.drawable.ic_search);
            }
            float density = context.getResources().getDisplayMetrics().density;
            int width = Math.round((scale > 1 ? 320 : 360) * density), height = Math.round(900 * density);
            measure(root, width, height);
            readable(root.findViewById(R.id.favorite_header));
            assertTrue(root.findViewById(R.id.search_icon).getHeight() >= 48 * density);
            if (saved) {
                for (int index = 0; index < categories.size(); index++) {
                    var holder = list.findViewHolderForAdapterPosition(index);
                    assertNotNull(holder);
                    View card = holder.itemView;
                    readable(card.findViewById(R.id.categoryName));
                    View remove = card.findViewById(R.id.removeFromFavoriteButton);
                    assertTrue("Heart has an accessible touch target", remove.getWidth() >= 48 * density && remove.getHeight() >= 48 * density);
                    assertTrue(remove.getContentDescription().toString().contains(categories.get(index).getName()));
                    ImageView image = card.findViewById(R.id.categoryImage);
                    assertEquals("Heart overlays the top of the image", image.getTop(), remove.getTop());
                    assertEquals("Heart overlays the right edge of the image", image.getRight(), remove.getRight());
                    com.bumptech.glide.Glide.with(context).clear(image);
                    image.setImageResource(categories.get(index).getImageSource());
                    assertEquals(image.getWidth(), image.getHeight());
                    assertEquals(ImageView.ScaleType.FIT_XY, image.getScaleType());
                    assertEquals(Color.WHITE, ((ColorDrawable) image.getBackground()).getColor());
                }
                View firstCard = list.findViewHolderForAdapterPosition(0).itemView;
                for (int index = 1; index < 3; index++) {
                    View card = list.findViewHolderForAdapterPosition(index).itemView;
                    assertEquals("Three favorite cards fit on the same row", firstCard.getTop(), card.getTop());
                    assertTrue(card.getLeft() > firstCard.getLeft());
                }
                assertTrue("The fourth favorite starts the next row",
                        list.findViewHolderForAdapterPosition(3).itemView.getTop() > firstCard.getTop());
                View first = list.findViewHolderForAdapterPosition(0).itemView;
                first.performClick();
                assertEquals(VideoPlayerActivity.class.getName(), context.opened.getComponent().getClassName());
                assertEquals(categories.get(0).getId().toString(), context.opened.getStringExtra(VideoPlayerActivity.CATEGORY_ID));
                assertNull("Opening a favorite does not remove it", removed.get());
                first.findViewById(R.id.removeFromFavoriteButton).performClick();
                assertEquals(categories.get(0).getId(), removed.get().getId());
                adapter.setRemovalPending(true);
                removed.set(null);
                measure(root, width, height);
                list.findViewHolderForAdapterPosition(0).itemView.findViewById(R.id.removeFromFavoriteButton).performClick();
                assertNull("A pending removal cannot be submitted twice", removed.get());
                adapter.setRemovalPending(false);
                measure(root, width, height);
                for (int index = 0; index < categories.size(); index++) {
                    ImageView image = list.findViewHolderForAdapterPosition(index).itemView.findViewById(R.id.categoryImage);
                    com.bumptech.glide.Glide.with(context).clear(image);
                    image.setImageResource(categories.get(index).getImageSource());
                }
                measure(root, width, height);
            } else {
                for (int id : new int[]{R.id.firstMessage, R.id.secondMessage, R.id.browseCollectionsButton}) readable(root.findViewById(id));
                assertTrue(root.findViewById(R.id.browseCollectionsButton).getHeight() >= 48 * density);
            }
            var bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            root.draw(new Canvas(bitmap));
            try (var output = new FileOutputStream(new File(target.getExternalFilesDir(null), "favorites-" + suffix + ".png"))) {
                assertTrue(bitmap.compress(Bitmap.CompressFormat.PNG, 100, output));
            } catch (java.io.IOException error) { throw new AssertionError(error); }
            finally { bitmap.recycle(); }
        });
    }

    private void measure(View root, int width, int height) {
        root.measure(View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY));
        root.layout(0, 0, width, height);
    }

    private void readable(TextView text) {
        assertNotNull(text.getLayout());
        assertTrue("Favorite labels are not clipped", text.getLayout().getHeight() <= text.getHeight()
                - text.getCompoundPaddingTop() - text.getCompoundPaddingBottom());
        for (int line = 0; line < text.getLineCount(); line++) assertEquals(0, text.getLayout().getEllipsisCount(line));
    }
}
