package com.project.ohl;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import com.google.gson.Gson;
import com.project.ohl.data.remote.RetrofitService;
import com.project.ohl.domain.model.Category;
import com.project.ohl.presentation.CategoryNavigation;
import com.project.ohl.presentation.MainActivity;
import com.project.ohl.presentation.MediaResources;
import com.project.ohl.presentation.UserSession;
import com.project.ohl.presentation.video_section.VideoSectionActivity;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BooleanSupplier;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeTrue;

@RunWith(AndroidJUnit4.class)
public class CategoryImageDisplayTest {
    private final Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
    private final StringBuilder report = new StringBuilder();
    private int checkedImages;
    private int textOnlyCards;
    private int checkedCards;
    private int missingImages;

    @Test public void everyCatalogPageDisplaysCompleteImagesInWhiteSquares() throws Exception {
        assumeTrue("true".equals(InstrumentationRegistry.getArguments().getString("auditCategoryImages")));
        var context = instrumentation.getTargetContext();
        instrumentation.startActivitySync(new Intent(context, MainActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        instrumentation.waitForIdleSync();
        SystemClock.sleep(1000);
        assumeTrue("Sign in before auditing protected catalog pages", UserSession.id() != null);
        var connection = (HttpURLConnection) URI.create(BuildConfig.API_BASE_URL + "api/categories").toURL().openConnection();
        connection.setRequestProperty("Authorization", "Bearer " + RetrofitService.tokens().token());
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(10000);
        List<Category> catalog;
        try (var reader = new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8)) {
            catalog = Arrays.asList(new Gson().fromJson(reader, Category[].class));
        } finally { connection.disconnect(); }
        var missing = new HashSet<String>();
        for (var category : catalog) if (category.getImageUrl() == null) missing.add(category.getName());
        var parents = new HashSet<>();
        for (var category : catalog) if (category.getParentId() != null) parents.add(category.getParentId());
        var output = new File(context.getExternalFilesDir(null), "category-image-audit");
        assertTrue(output.isDirectory() || output.mkdirs());
        var pageNames = new ArrayList<String>();
        var intents = new ArrayList<Intent>();
        var counts = new ArrayList<Integer>();
        pageNames.add("Video");
        intents.add(new Intent(context, VideoSectionActivity.class));
        counts.add((int) catalog.stream().filter(c -> c.getParentId() == null).count());
        for (var category : catalog) if (parents.contains(category.getId())) {
            pageNames.add(category.getName());
            intents.add(CategoryNavigation.intent(context, category));
            counts.add((int) catalog.stream().filter(c -> Objects.equals(c.getParentId(), category.getId())).count());
        }
        for (int page = 0; page < intents.size(); page++) {
            String name = pageNames.get(page);
            int expected = counts.get(page);
            Activity activity = instrumentation.startActivitySync(intents.get(page).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            try {
                instrumentation.runOnMainSync(() -> activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON));
                await(() -> {
                    var list = findList(activity.getWindow().getDecorView());
                    return list != null && list.getAdapter() != null && list.getAdapter().getItemCount() == expected
                            && list.getChildCount() > 0;
                }, name + ": catalog did not load; activity=" + activity.getClass().getSimpleName());
                for (int position = 0; position < expected; position++) {
                    int index = position;
                    instrumentation.runOnMainSync(() -> findList(activity.getWindow().getDecorView()).scrollToPosition(index));
                    await(() -> {
                        var holder = findList(activity.getWindow().getDecorView()).findViewHolderForAdapterPosition(index);
                        if (holder == null) return false;
                        ImageView image = holder.itemView.findViewById(R.id.categoryImage);
                        String label = ((TextView) holder.itemView.findViewById(R.id.categoryName)).getText().toString();
                        return image == null || missing.contains(label) || (image.getWidth() > 0 && image.getDrawable() instanceof BitmapDrawable);
                    }, name + ": image " + position + " did not load");
                    instrumentation.runOnMainSync(() -> {
                        var holder = findList(activity.getWindow().getDecorView()).findViewHolderForAdapterPosition(index);
                        assertNotNull(holder);
                        ImageView image = holder.itemView.findViewById(R.id.categoryImage);
                        String label = ((TextView) holder.itemView.findViewById(R.id.categoryName)).getText().toString();
                        checkedCards++;
                        if (image == null) { textOnlyCards++; report.append(name).append(" / ").append(label).append(": text only\n"); return; }
                        if (missing.contains(label)) { missingImages++; checkFrame(image, label); report.append(name).append(" / ").append(label).append(": MISSING SOURCE IMAGE\n"); }
                        else checkImage(image, name + " / " + label);
                    });
                }
                instrumentation.runOnMainSync(() -> {
                    var decor = activity.getWindow().getDecorView();
                    View intro = activity.findViewById(R.id.categoryIntroCard);
                    if (intro != null) checkIntro(intro, name);
                    Bitmap bitmap = Bitmap.createBitmap(decor.getWidth(), decor.getHeight(), Bitmap.Config.ARGB_8888);
                    decor.draw(new Canvas(bitmap));
                    try (var stream = new FileOutputStream(new File(output, MediaResources.resourceName(name) + ".png"))) {
                        assertTrue(bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream));
                    } catch (IOException error) { throw new AssertionError(error); }
                    finally { bitmap.recycle(); }
                });
                report.append("PAGE OK: ").append(name).append(" (").append(expected).append(" cards)\n");
                Files.writeString(new File(output, "report.txt").toPath(), report.toString());
            } finally { instrumentation.runOnMainSync(activity::finish); }
        }
        report.append("TOTAL: ").append(intents.size()).append(" pages, ").append(checkedImages)
                .append(" images, ").append(missingImages).append(" missing source images, ").append(textOnlyCards).append(" text-only cards\n");
        Files.writeString(new File(output, "report.txt").toPath(), report.toString());
        assertEquals(catalog.size(), checkedCards);
        assertEquals("Every category must have an image", 0, missingImages);
        assertEquals("Every card must display its image", 0, textOnlyCards);
    }

    private void checkFrame(ImageView image, String label) {
        assertEquals(label + ": square", image.getWidth(), image.getHeight());
        assertEquals(label + ": fill both axes", ImageView.ScaleType.FIT_XY, image.getScaleType());
        assertTrue(label + ": white surface", image.getBackground() instanceof ColorDrawable);
        assertEquals(label + ": white surface", Color.WHITE, ((ColorDrawable) image.getBackground()).getColor());
    }
    private void checkImage(ImageView image, String label) {
        checkFrame(image, label);
        var drawable = image.getDrawable();
        var bounds = new RectF(drawable.getBounds());
        image.getImageMatrix().mapRect(bounds);
        assertEquals(label + ": left edge", 0f, bounds.left, 1f);
        assertEquals(label + ": top edge", 0f, bounds.top, 1f);
        assertEquals(label + ": right edge", (float) image.getWidth(), bounds.right, 1f);
        assertEquals(label + ": bottom edge", (float) image.getHeight(), bounds.bottom, 1f);
        checkedImages++;
        report.append(label).append(": ").append(image.getWidth()).append("px square, full drawable, white surface\n");
    }
    private void checkIntro(View view, String label) {
        if (view instanceof ImageView image) checkImage(image, label + " intro");
        if (view instanceof ViewGroup group) for (int i = 0; i < group.getChildCount(); i++) checkIntro(group.getChildAt(i), label);
    }
    private RecyclerView findList(View view) {
        if (view instanceof RecyclerView list) return list;
        if (view instanceof ViewGroup group) for (int i = 0; i < group.getChildCount(); i++) {
            var found = findList(group.getChildAt(i));
            if (found != null) return found;
        }
        return null;
    }
    private void await(BooleanSupplier condition, String failure) {
        long deadline = SystemClock.uptimeMillis() + 15000;
        while (SystemClock.uptimeMillis() < deadline) {
            var ready = new AtomicBoolean();
            instrumentation.runOnMainSync(() -> ready.set(condition.getAsBoolean()));
            if (ready.get()) { instrumentation.waitForIdleSync(); return; }
            SystemClock.sleep(100);
        }
        fail(failure);
    }
}
