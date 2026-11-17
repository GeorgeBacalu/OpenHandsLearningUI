package com.project.ohl;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class VideoPlayerLayoutTest {
    @Test
    public void playerFitsSmallScreensBothThemesAndLargeText() {
        render(false, 1f, 360, 880, "day");
        render(true, 1f, 360, 880, "night");
        render(false, 1.6f, 320, 800, "large-text");
        render(true, 1f, 640, 360, "landscape");
    }

    private void render(boolean night, float fontScale, int widthDp, int heightDp, String suffix) {
        TestMedia.preload();

        var instrumentation = InstrumentationRegistry.getInstrumentation();

        instrumentation.runOnMainSync(() -> {
            var target = instrumentation.getTargetContext();
            var configuration = new Configuration(target.getResources().getConfiguration());

            configuration.uiMode =
                    (configuration.uiMode & ~Configuration.UI_MODE_NIGHT_MASK)
                            | (night ? Configuration.UI_MODE_NIGHT_YES : Configuration.UI_MODE_NIGHT_NO);

            configuration.fontScale = fontScale;

            var context =
                    new ContextThemeWrapper(
                            target.createConfigurationContext(configuration),
                            R.style.Theme_OpenHandsLearning_VideoPlayer);

            View root = LayoutInflater.from(context).inflate(R.layout.activity_video_player, null);

            root.setBackgroundColor(context.getColor(R.color.auth_background));

            ((TextView) root.findViewById(R.id.categoryName)).setText("Acesta este colegul meu");

            ((ImageView) root.findViewById(R.id.categoryImage))
                    .setImageDrawable(TestMedia.drawable(TestMedia.url("formule_de_adresare")));

            root.findViewById(R.id.video_loading).setVisibility(View.GONE);

            for (int id :
                    new int[] {R.id.video_play_pause, R.id.video_replay, R.id.video_progress, R.id.favoriteButton})
                root.findViewById(id).setEnabled(true);

            Bitmap frame;

            frame = TestMedia.videoFrame();

            assertNotNull("An S3 video frame is available", frame);

            var preview = new ImageView(context);

            preview.setScaleType(ImageView.ScaleType.FIT_CENTER);
            preview.setImageBitmap(frame);

            ((FrameLayout) root.findViewById(R.id.videoViewContainer))
                    .addView(preview, 0, new FrameLayout.LayoutParams(-1, -1));

            float density = context.getResources().getDisplayMetrics().density;

            int width = Math.round(widthDp * density), height = Math.round(heightDp * density);

            root.measure(
                    View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY));
            root.layout(0, 0, width, height);

            View stage = root.findViewById(R.id.videoViewContainer);

            assertTrue("Player fits the screen width", stage.getWidth() <= width - 40 * density);
            assertEquals("Player preserves a 4:3 viewport", stage.getWidth() * 3f / 4f, stage.getHeight(), 2f);

            for (int id :
                    new int[] {R.id.video_play_pause, R.id.video_replay, R.id.video_progress, R.id.favoriteButton}) {
                View action = root.findViewById(id);

                assertTrue("Player controls have accessible touch targets", action.getHeight() >= 48 * density);
            }

            for (int id :
                    new int[] {R.id.categoryName, R.id.video_play_pause, R.id.video_replay, R.id.favoriteButton}) {
                TextView text = root.findViewById(id);

                assertTrue(
                        "Player text is not clipped",
                        text.getLayout().getHeight()
                                <= text.getHeight() - text.getCompoundPaddingTop() - text.getCompoundPaddingBottom());

                for (int line = 0; line < text.getLineCount(); line++)
                    assertEquals("Player labels are not truncated", 0, text.getLayout().getEllipsisCount(line));
            }

            var bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

            root.draw(new Canvas(bitmap));

            try (var output =
                    new FileOutputStream(
                            new File(target.getExternalFilesDir(null), "video-player-" + suffix + ".png"))) {
                assertTrue(bitmap.compress(Bitmap.CompressFormat.PNG, 100, output));
            } catch (IOException error) {
                throw new AssertionError(error);
            } finally {
                bitmap.recycle();

                frame.recycle();
            }
        });
    }
}
