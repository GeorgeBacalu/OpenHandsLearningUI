package com.project.ohl.data.remote;

import android.content.Context;
import android.util.Base64;
import com.google.gson.JsonParser;
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
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import okhttp3.HttpUrl;

public final class JwtTokenStore {
    private static final String KEY_ALIAS = "ohl.jwt.token";
    private final AtomicFile file;
    private final HttpUrl origin = HttpUrl.get(BuildConfig.API_BASE_URL);
    private String token;

    public JwtTokenStore(Context context) {
        new AtomicFile(new File(context.getNoBackupFilesDir(), "session")).delete();
        file = new AtomicFile(new File(context.getNoBackupFilesDir(), "jwt"));
        if (file.getBaseFile().exists()) {
            try {
                byte[] bytes = file.readFully();
                if (bytes.length < 29) throw new IOException("Invalid session storage");
                var cipher = Cipher.getInstance("AES/GCM/NoPadding");
                cipher.init(Cipher.DECRYPT_MODE, key(), new GCMParameterSpec(128, Arrays.copyOf(bytes, 12)));
                cipher.updateAAD(origin.toString().getBytes(StandardCharsets.UTF_8));
                token = new String(cipher.doFinal(bytes, 12, bytes.length - 12), StandardCharsets.UTF_8);
            } catch (GeneralSecurityException | IOException exception) { clear(); }
        }
    }

    public synchronized String token() {
        if (token == null) return null;
        try {
            String[] parts = token.split("\\.");
            if (parts.length != 3) { clear(); return null; }
            String payload = new String(Base64.decode(parts[1], Base64.URL_SAFE | Base64.NO_WRAP), StandardCharsets.UTF_8);
            long expires = JsonParser.parseString(payload).getAsJsonObject().get("exp").getAsLong();
            if (expires <= System.currentTimeMillis() / 1000) clear();
        } catch (RuntimeException exception) { clear(); }
        return token;
    }
    public synchronized void save(String value) { token = value; persist(); }
    public synchronized void clear() { token = null; file.delete(); }

    public boolean sameOrigin(HttpUrl url) {
        return origin.host().equals(url.host()) && origin.port() == url.port() && origin.scheme().equals(url.scheme());
    }

    private void persist() {
        if (token == null) { file.delete(); return; }
        FileOutputStream stream = null;
        try {
            var cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, key());
            cipher.updateAAD(origin.toString().getBytes(StandardCharsets.UTF_8));
            byte[] encrypted = cipher.doFinal(token.getBytes(StandardCharsets.UTF_8));
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
