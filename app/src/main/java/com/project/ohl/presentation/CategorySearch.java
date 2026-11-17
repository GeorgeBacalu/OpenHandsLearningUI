package com.project.ohl.presentation;

import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

public final class CategorySearch {
    private CategorySearch() {}

    public static void bind(EditText input, View icon, Runnable search) {
        icon.setOnClickListener(view -> search.run());

        input.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        input.setOnEditorActionListener((view, action, event) -> {
            boolean enter = event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER;

            if (action != EditorInfo.IME_ACTION_SEARCH && !enter) return false;

            if (!enter || event.getAction() == KeyEvent.ACTION_UP) icon.performClick();

            return true;
        });
    }
}
