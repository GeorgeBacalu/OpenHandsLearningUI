package com.project.ohl;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/** Verifies the shared card layouts without loading or changing the signed-in account. */
@RunWith(AndroidJUnit4.class)
public class CatalogCardLayoutTest {
    @Test public void cardsKeepArtworkSquareAndLongLabelsReadableAcrossThemes() {
        render(false, 1f, "day");
        render(true, 1f, "night");
        render(false, 1.6f, "large-text");
    }

    private void render(boolean night, float fontScale, String suffix) {
        var instrumentation = InstrumentationRegistry.getInstrumentation();
        instrumentation.runOnMainSync(() -> {
            var target = instrumentation.getTargetContext();
            var configuration = new Configuration(target.getResources().getConfiguration());
            configuration.uiMode = (configuration.uiMode & ~Configuration.UI_MODE_NIGHT_MASK)
                    | (night ? Configuration.UI_MODE_NIGHT_YES : Configuration.UI_MODE_NIGHT_NO);
            configuration.fontScale = fontScale;
            var context = new ContextThemeWrapper(target.createConfigurationContext(configuration), R.style.Theme_OpenHandsLearning);
            float density = context.getResources().getDisplayMetrics().density;
            var root = new LinearLayout(context);
            root.setOrientation(LinearLayout.VERTICAL);
            root.setBackgroundColor(context.getColor(R.color.auth_background));
            int padding = Math.round(12 * density);
            root.setPadding(padding, padding, padding, padding);
            var inflater = LayoutInflater.from(context);
            var labels = new ArrayList<TextView>();
            var images = new ArrayList<ImageView>();
            var sections = new LinearLayout(context);
            sections.setBackgroundColor(context.getColor(R.color.main_blue));
            root.addView(sections);
            String[] sectionNames = {"Video", "Voce în semne"};
            int[] artwork = {R.drawable.video_section, R.drawable.voice_to_sign_section};
            for (int index = 0; index < sectionNames.length; index++) {
                View card = inflater.inflate(R.layout.section_item, sections, false);
                TextView label = card.findViewById(R.id.sectionName);
                label.setText(sectionNames[index]); labels.add(label);
                ((ImageView) card.findViewById(R.id.sectionImage)).setImageResource(artwork[index]);
                var params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                params.setMargins(padding / 2, padding, padding / 2, padding);
                sections.addView(card, params);
            }
            String[] names = {"Alfabet", "Animale", "Formule de adresare", "Membrii familiei", "Emoții", "Expresii"};
            int[] photos = {R.drawable.alfabet, R.drawable.animale, R.drawable.formule_de_adresare,
                    R.drawable.membrii_familiei, R.drawable.emotii, R.drawable.expressions_section};
            for (int rowIndex = 0; rowIndex < 2; rowIndex++) {
                var row = new LinearLayout(context);
                root.addView(row);
                for (int column = 0; column < 3; column++) {
                    int index = rowIndex * 3 + column;
                    View card = inflater.inflate(R.layout.category_item, row, false);
                    TextView label = card.findViewById(R.id.categoryName);
                    label.setText(names[index]); labels.add(label);
                    ImageView image = card.findViewById(R.id.categoryImage);
                    image.setImageResource(photos[index]); images.add(image);
                    var params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                    params.setMargins(padding / 3, padding, padding / 3, padding);
                    row.addView(card, params);
                }
            }
            View favorite = inflater.inflate(R.layout.category_item_expandable, root, false);
            ((TextView) favorite.findViewById(R.id.categoryName)).setText("Expresii");
            ((ImageView) favorite.findViewById(R.id.categoryImage)).setImageResource(R.drawable.expressions_section);
            favorite.findViewById(R.id.expendedRelativeLayout).setVisibility(View.VISIBLE);
            root.addView(favorite);
            int width = Math.round(360 * density);
            root.measure(View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            root.layout(0, 0, width, root.getMeasuredHeight());
            for (TextView label : labels) {
                assertTrue(label.getLayout().getHeight() <= label.getHeight() - label.getCompoundPaddingTop() - label.getCompoundPaddingBottom());
                for (int line = 0; line < label.getLineCount(); line++) assertEquals(0, label.getLayout().getEllipsisCount(line));
            }
            for (ImageView image : images) {
                assertEquals(image.getWidth(), image.getHeight());
                assertEquals(ImageView.ScaleType.FIT_XY, image.getScaleType());
                assertEquals(Color.WHITE, ((ColorDrawable) image.getBackground()).getColor());
            }
            var bitmap = Bitmap.createBitmap(width, root.getHeight(), Bitmap.Config.ARGB_8888);
            root.draw(new Canvas(bitmap));
            try (var stream = new FileOutputStream(new File(target.getExternalFilesDir(null), "catalog-cards-" + suffix + ".png"))) {
                assertTrue(bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream));
            } catch (java.io.IOException error) { throw new AssertionError(error); }
            finally { bitmap.recycle(); }
        });
    }
}
