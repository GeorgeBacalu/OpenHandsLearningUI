package com.project.ohl;

import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import com.project.ohl.presentation.CategorySearch;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class CategorySearchTest {
    @Test public void iconAndKeyboardSubmitTheVisibleQueryAndAllowClearingIt() {
        var instrumentation = InstrumentationRegistry.getInstrumentation();
        instrumentation.runOnMainSync(() -> {
            var context = new ContextThemeWrapper(instrumentation.getTargetContext(),
                    R.style.Theme_OpenHandsLearning_Authentication);
            var input = new EditText(context);
            var icon = new View(context);
            var queries = new ArrayList<String>();
            CategorySearch.bind(input, icon, () -> queries.add(input.getText().toString()));
            input.setText("Ar");
            icon.performClick();
            assertEquals("The submitted query stays visible", "Ar", input.getText().toString());
            assertEquals(EditorInfo.IME_ACTION_SEARCH, input.getImeOptions());
            input.onEditorAction(EditorInfo.IME_ACTION_SEARCH);
            input.setText("");
            input.onEditorAction(EditorInfo.IME_ACTION_SEARCH);
            assertEquals(List.of("Ar", "Ar", ""), queries);
        });
    }
}
