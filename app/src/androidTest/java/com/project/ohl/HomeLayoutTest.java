package com.project.ohl;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.view.SupportMenuInflater;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.ColorUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.project.ohl.presentation.MainRecyclerViewAdapter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class HomeLayoutTest {
    @Test public void homeKeepsTextAndArtworkVisibleInBothThemesAndWithLargeText() {
        render(false, 1f, 360, "day");
        render(true, 1f, 360, "night");
        render(false, 1.6f, 320, "large-text-day");
        render(true, 1.6f, 320, "large-text-night");
    }

    private void render(boolean night, float scale, int widthDp, String suffix) {
        TestMedia.preload();
        var instrumentation = InstrumentationRegistry.getInstrumentation();
        instrumentation.runOnMainSync(() -> {
            var target = instrumentation.getTargetContext();
            var configuration = new Configuration(target.getResources().getConfiguration());
            configuration.uiMode = (configuration.uiMode & ~Configuration.UI_MODE_NIGHT_MASK)
                    | (night ? Configuration.UI_MODE_NIGHT_YES : Configuration.UI_MODE_NIGHT_NO);
            configuration.fontScale = scale;
            var context = new ContextThemeWrapper(target.createConfigurationContext(configuration), R.style.Theme_OpenHandsLearning_Home);
            int surface = context.getColor(R.color.home_surface);
            for (int background : new int[]{surface, context.getColor(R.color.home_background)}) {
                assertTrue("Home text has strong contrast", ColorUtils.calculateContrast(context.getColor(R.color.home_text), background) >= 7);
                assertTrue("Descriptions are readable", ColorUtils.calculateContrast(context.getColor(R.color.home_muted), background) >= 4.5);
            }
            for (int accent : new int[]{R.color.home_video_accent, R.color.home_voice_accent})
                assertTrue("Section actions are readable", ColorUtils.calculateContrast(context.getColor(accent), surface) >= 4.5);
            for (int gradient : new int[]{R.color.brand_gradient_start, R.color.brand_gradient_end})
                assertTrue("Welcome text is readable on the gradient", ColorUtils.calculateContrast(
                        context.getColor(R.color.home_welcome_muted), context.getColor(gradient)) >= 4.5);

            View root = LayoutInflater.from(context).inflate(R.layout.activity_main, null);
            root.setBackgroundColor(context.getColor(R.color.home_background));
            Toolbar toolbar = root.findViewById(R.id.toolbar);
            toolbar.setTitleTextAppearance(context, R.style.HomeToolbarTitle);
            new SupportMenuInflater(context).inflate(R.menu.action_bar_menu, toolbar.getMenu());
            ((TextView) root.findViewById(R.id.welcomeMessage)).setText(context.getString(R.string.welcome_user,
                    scale > 1 ? "Ana Maria Popescu" : "Ana"));
            RecyclerView sections = root.findViewById(R.id.sectionsRecyclerView);
            sections.setLayoutManager(new LinearLayoutManager(context));
            var adapter = new MainRecyclerViewAdapter(context);
            sections.setAdapter(adapter);
            String[] artwork = {TestMedia.url("video_section"), TestMedia.url("voice_to_sign_section")};
            float density = context.getResources().getDisplayMetrics().density;
            int width = Math.round(widthDp * density), height = Math.round(850 * density);
            root.measure(View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY));
            root.layout(0, 0, width, height);
            for (int id : new int[]{R.id.welcomeMessage, R.id.home_headline, R.id.home_explore_heading})
                assertReadable(root.findViewById(id));
            for (int index = 0; index < adapter.getItemCount(); index++) {
                var holder = sections.findViewHolderForAdapterPosition(index);
                assertNotNull("Both home sections are laid out", holder);
                View card = holder.itemView;
                assertTrue("Section cards are easy to tap", card.getHeight() >= 48 * density);
                assertNotNull("Section actions have an accessibility label", card.getContentDescription());
                for (int id : new int[]{R.id.sectionName, R.id.sectionDescription, R.id.sectionAction})
                    assertReadable(card.findViewById(id));
                MaterialCardView imageSurface = card.findViewById(R.id.sectionArtwork);
                assertTrue("Dark illustrations stay on a light canvas in both themes",
                        ColorUtils.calculateLuminance(imageSurface.getCardBackgroundColor().getDefaultColor()) >= 0.8);
                ImageView image = card.findViewById(R.id.sectionImage);
                Glide.with(context).clear(image);
                image.setImageDrawable(TestMedia.drawable(artwork[index]));
                assertEquals(ImageView.ScaleType.FIT_CENTER, image.getScaleType());
            }
            var bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            root.draw(new Canvas(bitmap));
            try (var output = new FileOutputStream(new File(target.getExternalFilesDir(null), "home-" + suffix + ".png"))) {
                assertTrue(bitmap.compress(Bitmap.CompressFormat.PNG, 100, output));
            } catch (IOException error) { throw new AssertionError(error); }
            finally { bitmap.recycle(); }
        });
    }

    private void assertReadable(TextView text) {
        assertTrue("Home labels are not clipped", text.getLayout().getHeight()
                <= text.getHeight() - text.getCompoundPaddingTop() - text.getCompoundPaddingBottom());
        for (int line = 0; line < text.getLineCount(); line++)
            assertEquals("Home labels are not truncated", 0, text.getLayout().getEllipsisCount(line));
    }
}
