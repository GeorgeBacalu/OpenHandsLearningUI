package com.project.ohl;

import android.content.Context;
import android.content.Intent;
import android.view.ContextThemeWrapper;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import com.project.ohl.presentation.HomeFeature;
import com.project.ohl.presentation.MainRecyclerViewAdapter;
import org.junit.Test;
import org.junit.runner.RunWith;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class HomeRoutingTest {
    private static final class TestNavigationContext extends ContextThemeWrapper {
        private Intent started;

        TestNavigationContext(Context context) {
            super(context, R.style.Theme_OpenHandsLearning_Home);
        }

        @Override
        public void startActivity(Intent intent) {
            started = intent;
        }
    }

    @Test
    public void fixedHomeCardsOpenTheirDedicatedScreensWithoutApiMetadata() {
        InstrumentationRegistry.getInstrumentation()
                .runOnMainSync(() -> {
                    var context =
                            new TestNavigationContext(InstrumentationRegistry.getInstrumentation().getTargetContext());
                    var list = new RecyclerView(context);

                    list.setLayoutManager(new LinearLayoutManager(context));

                    var adapter = new MainRecyclerViewAdapter(context);

                    list.setAdapter(adapter);
                    list.measure(
                            View.MeasureSpec.makeMeasureSpec(1080, View.MeasureSpec.EXACTLY),
                            View.MeasureSpec.makeMeasureSpec(2200, View.MeasureSpec.EXACTLY));
                    list.layout(0, 0, 1080, 2200);

                    assertEquals(2, adapter.getItemCount());

                    for (int index = 0; index < adapter.getItemCount(); index++) {
                        var holder = list.findViewHolderForAdapterPosition(index);

                        assertNotNull(holder);

                        holder.itemView.findViewById(R.id.sectionCard).performClick();

                        assertNotNull(context.started);
                        assertEquals(
                                HomeFeature.values()[index].screen().getName(),
                                requireNonNull(context.started.getComponent()).getClassName());
                    }

                    list.setAdapter(null);
                });
    }
}
