package com.project.ohl;

import android.util.Base64;
import com.project.ohl.app.ApplicationGraph;
import com.project.ohl.data.remote.RetrofitService;
import com.project.ohl.domain.model.User;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

final class MediaTestAccount implements AutoCloseable {
    private final Object repository = ApplicationGraph.authentication();
    private final Field account;
    private final Object previousAccount;
    private final String previousToken = RetrofitService.tokens().token();

    MediaTestAccount() throws ReflectiveOperationException {
        account = repository.getClass().getDeclaredField("account");
        account.setAccessible(true);
        previousAccount = account.get(repository);
        var user = new User();
        user.setId(UUID.randomUUID());
        user.setName("Media test");
        String claims = "{\"exp\":" + (System.currentTimeMillis() / 1000 + 3600) + "}";
        RetrofitService.tokens().save("header." + Base64.encodeToString(claims.getBytes(StandardCharsets.UTF_8),
                Base64.URL_SAFE | Base64.NO_WRAP | Base64.NO_PADDING) + ".signature");
        account.set(repository, user);
    }

    @Override public void close() throws IllegalAccessException {
        if (previousToken == null) RetrofitService.tokens().clear();
        else RetrofitService.tokens().save(previousToken);
        account.set(repository, previousAccount);
    }
}
