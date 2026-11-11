package com.project.ohl;

import android.content.Context;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import com.project.ohl.data.remote.JsonCodec;
import com.project.ohl.domain.model.SignText;
import com.project.ohl.domain.model.TextSignPair;
import com.project.ohl.presentation.MediaResources;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class SignMediaTest {
    @Test public void resolvesTheCompleteIllustrationSetAndRetainedAVideo() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        for (char letter = 'a'; letter <= 'z'; letter++) {
            assertTrue(MediaResources.drawable(context, "dactilema_" + letter) != 0);
        }
        assertEquals(0, context.getResources().getIdentifier("a", "drawable", context.getPackageName()));
        assertTrue(context.getResources().getIdentifier("dactilema_a", "raw", context.getPackageName()) != 0);
        var gson = JsonCodec.create();
        var a = gson.fromJson("{\"letter\":\"Ă\",\"mediaKey\":\"dactilema_a\"}", TextSignPair.class);
        var space = gson.fromJson("{\"letter\":\" \" ,\"mediaKey\":\"space\"}", TextSignPair.class);
        MediaResources.bindSigns(context, List.of(a, space));
        var output = SignText.translate("ă Ă", List.of(a, space));
        assertEquals(3, output.size());
        assertTrue(output.stream().allMatch(sign -> sign.getImageSource() != 0));
        assertEquals(a.getImageSource(), output.get(0).getImageSource());
        assertEquals(a.getImageSource(), output.get(2).getImageSource());
    }
}
