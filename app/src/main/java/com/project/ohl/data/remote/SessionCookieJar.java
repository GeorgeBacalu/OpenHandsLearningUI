package com.project.ohl.data.remote;

import android.content.Context;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.AtomicFile;
import com.project.ohl.BuildConfig;
import java.io.IOException;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.util.Arrays;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

public final class SessionCookieJar implements CookieJar {
    private static final String KEY_ALIAS = "ohl.session.cookie";
    private final AtomicFile file;
    private final HttpUrl origin = HttpUrl.get(BuildConfig.API_BASE_URL);
    private Cookie session;

    public SessionCookieJar(Context context) {
        file = new AtomicFile(new File(context.getNoBackupFilesDir(), "session"));
        if (file.getBaseFile().exists()) {
            try {
                byte[] bytes = file.readFully();
                if (bytes.length < 29) throw new IOException("Invalid session storage");
                var cipher = Cipher.getInstance("AES/GCM/NoPadding");
                cipher.init(Cipher.DECRYPT_MODE, key(), new GCMParameterSpec(128, Arrays.copyOf(bytes, 12)));
                cipher.updateAAD(origin.toString().getBytes(StandardCharsets.UTF_8));
                session = Cookie.parse(origin, new String(cipher.doFinal(bytes, 12, bytes.length - 12), StandardCharsets.UTF_8));
            } catch (GeneralSecurityException | IOException exception) { clear(); }
        }
    }

    @Override
    public synchronized void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (!sameOrigin(url)) return;
        for (Cookie cookie : cookies) {
            if (!cookie.name().equals("JSESSIONID")) continue;
            session = cookie.expiresAt() > System.currentTimeMillis() ? cookie : null;
            persist();
        }
    }

    @Override
    public synchronized List<Cookie> loadForRequest(HttpUrl url) {
        return sameOrigin(url) && session != null && session.matches(url)
                && session.expiresAt() > System.currentTimeMillis() ? List.of(session) : List.of();
    }

    public synchronized boolean hasSession() { return session != null && session.expiresAt() > System.currentTimeMillis(); }
    public synchronized void clear() { session = null; file.delete(); }

    private boolean sameOrigin(HttpUrl url) {
        return origin.host().equals(url.host()) && origin.port() == url.port() && origin.scheme().equals(url.scheme());
    }

    private void persist() {
        if (session == null) { file.delete(); return; }
        FileOutputStream stream = null;
        try {
            var cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, key());
            cipher.updateAAD(origin.toString().getBytes(StandardCharsets.UTF_8));
            byte[] encrypted = cipher.doFinal(session.toString().getBytes(StandardCharsets.UTF_8));
            stream = file.startWrite();
            stream.write(cipher.getIV());
            stream.write(encrypted);
            file.finishWrite(stream);
        } catch (GeneralSecurityException | IOException exception) {
            if (stream != null) file.failWrite(stream);
            file.delete();
        }
    }

    private SecretKey key() throws GeneralSecurityException, IOException {
        var store = KeyStore.getInstance("AndroidKeyStore");
        store.load(null);
        if (store.containsAlias(KEY_ALIAS)) return (SecretKey) store.getKey(KEY_ALIAS, null);
        var generator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
        generator.init(new KeyGenParameterSpec.Builder(KEY_ALIAS,
                KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE).build());
        return generator.generateKey();
    }
}
