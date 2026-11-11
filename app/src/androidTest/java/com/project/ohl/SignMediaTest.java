package com.project.ohl;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import com.project.ohl.domain.model.SignText;
import com.project.ohl.presentation.MediaResources;
import com.project.ohl.presentation.text_to_sign_section.TextToSignSectionActivity;
import com.project.ohl.presentation.voice_to_sign_section.VoiceToSignSectionActivity;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class SignMediaTest {
    @Test public void resolvesEveryDictionaryEntryFromBundledResources() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        for (String key : SignText.dictionary().values()) assertTrue(MediaResources.drawable(context, key) != 0);
        assertEquals(0, context.getResources().getIdentifier("a", "drawable", context.getPackageName()));
        assertTrue(context.getResources().getIdentifier("dactilema_a", "raw", context.getPackageName()) != 0);
        var signs = SignText.translate("aăâbcd efg h iîjklmnopqrsşștţțuvwxyz AĂÂÎȘȚ");
        MediaResources.bindSigns(context, signs);
        assertTrue(signs.stream().allMatch(sign -> sign.getImageSource() != 0));
    }

    @Test public void textScreenConvertsImmediatelyWithoutLoadingAnApiCatalog() {
        checkScreen(TextToSignSectionActivity.class, R.id.textToSignRecyclerView);
    }

    @Test public void voiceScreenUsesTheSameLocalConversion() {
        var instrumentation = InstrumentationRegistry.getInstrumentation();
        instrumentation.getUiAutomation().grantRuntimePermission(
                instrumentation.getTargetContext().getPackageName(), Manifest.permission.RECORD_AUDIO);
        checkScreen(VoiceToSignSectionActivity.class, R.id.voiceToSignRecyclerView);
    }

    private void checkScreen(Class<? extends Activity> type, int recyclerId) {
        var instrumentation = InstrumentationRegistry.getInstrumentation();
        var intent = new Intent(instrumentation.getTargetContext(), type).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Activity activity = instrumentation.startActivitySync(intent);
        try {
            instrumentation.runOnMainSync(() -> {
                ((EditText) activity.findViewById(R.id.textInput)).setText("aĂ ș!");
                activity.findViewById(R.id.confirmButton).performClick();
                RecyclerView list = activity.findViewById(recyclerId);
                assertEquals(4, list.getAdapter().getItemCount());
                assertEquals(View.VISIBLE, activity.findViewById(R.id.outputContent).getVisibility());
                activity.findViewById(R.id.confirmButton).performClick();
                assertEquals(0, list.getAdapter().getItemCount());
                assertEquals(View.GONE, activity.findViewById(R.id.outputContent).getVisibility());
            });
        } finally {
            instrumentation.runOnMainSync(activity::finish);
        }
    }
}
