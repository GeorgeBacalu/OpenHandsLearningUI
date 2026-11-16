package com.project.ohl.presentation;

import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/** Keeps category content clear of the toolbar and system navigation. */
public final class CategoryPageLayout {
    private CategoryPageLayout() {}

    public static void configure(AppCompatActivity activity) {
        ViewGroup content = activity.findViewById(android.R.id.content);
        View page = content.getChildAt(0);
        View toolbar = activity.findViewById(androidx.appcompat.R.id.action_bar_container);
        int initialTop = page.getPaddingTop();
        int initialBottom = page.getPaddingBottom();
        int[] systemBars = new int[2];
        int[] pagePosition = new int[2];
        int[] toolbarPosition = new int[2];

        // Opening a page should show its introduction, without focusing search.
        ((ViewGroup) page).setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
        page.setFocusableInTouchMode(true);
        page.requestFocus();

        Runnable updatePadding = () -> {
            page.getLocationInWindow(pagePosition);
            int contentTop = systemBars[0];
            if (toolbar != null && toolbar.isShown()) {
                toolbar.getLocationInWindow(toolbarPosition);
                contentTop = Math.max(contentTop, toolbarPosition[1] + toolbar.getHeight());
            }
            int top = initialTop + Math.max(0, contentTop - pagePosition[1]);
            int bottom = initialBottom + systemBars[1];
            if (page.getPaddingTop() != top || page.getPaddingBottom() != bottom) {
                page.setPadding(page.getPaddingLeft(), top, page.getPaddingRight(), bottom);
            }
        };
        ViewCompat.setOnApplyWindowInsetsListener(page, (view, insets) -> {
            var bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            systemBars[0] = bars.top;
            systemBars[1] = bars.bottom;
            updatePadding.run();
            return insets;
        });
        page.getViewTreeObserver().addOnGlobalLayoutListener(updatePadding::run);
        ViewCompat.requestApplyInsets(page);
    }
}
