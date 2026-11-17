package com.project.ohl;

import android.content.Context;
import android.content.ContextWrapper;
import android.util.Base64;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import com.project.ohl.data.remote.JwtTokenStore;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.UUID;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class JwtTokenStoreTest {
    private File directory;
    private Context context;

    @Before
    public void isolatedStorage() {
        var target = InstrumentationRegistry.getInstrumentation().getTargetContext();

        directory = new File(target.getCacheDir(), "jwt-test-" + UUID.randomUUID());

        assertTrue(directory.mkdir());

        context =
                new ContextWrapper(target) {
                    @Override
                    public File getNoBackupFilesDir() {
                        return directory;
                    }
                };
    }

    @After
    public void removeIsolatedStorage() {
        for (File file : requireNonNull(directory.listFiles())) assertTrue(file.delete());

        assertTrue(directory.delete());
    }

    @Test
    public void tokensAreEncryptedAndRestoredWithoutPersistingPlaintext() throws Exception {
        String token = token(System.currentTimeMillis() / 1000 + 300);

        var store = new JwtTokenStore(context);

        store.save(token);

        assertEquals(token, store.token());

        String saved = Files.readString(new File(directory, "jwt").toPath(), StandardCharsets.UTF_8);

        assertFalse(saved.contains(token));
        assertEquals(token, new JwtTokenStore(context).token());

        store.clear();

        assertNull(new JwtTokenStore(context).token());
        assertFalse(new File(directory, "jwt").exists());
    }

    @Test
    public void expiredAndMalformedTokensCannotRestoreAnAccount() {
        var store = new JwtTokenStore(context);

        store.save(token(System.currentTimeMillis() / 1000 - 1));

        assertNull(store.token());

        store.save("malformed-token");

        assertNull(store.token());
        assertFalse(new File(directory, "jwt").exists());
    }

    @Test
    public void corruptedStorageAndLegacySessionCookiesAreDiscarded() throws Exception {
        Files.writeString(new File(directory, "session").toPath(), "JSESSIONID=legacy");
        Files.writeString(new File(directory, "jwt").toPath(), "corrupt");

        assertNull(new JwtTokenStore(context).token());
        assertFalse(new File(directory, "session").exists());
        assertFalse(new File(directory, "jwt").exists());
    }

    private String token(long expires) {
        String claims = "{\"exp\":" + expires + "}";

        return "header."
                + Base64.encodeToString(
                        claims.getBytes(StandardCharsets.UTF_8), Base64.URL_SAFE | Base64.NO_WRAP | Base64.NO_PADDING)
                + ".signature";
    }
}
