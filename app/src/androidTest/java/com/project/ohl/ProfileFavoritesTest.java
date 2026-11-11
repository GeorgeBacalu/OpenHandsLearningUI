package com.project.ohl;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import com.project.ohl.data.remote.JsonCodec;
import com.project.ohl.data.remote.RemoteLearningRepository;
import com.project.ohl.presentation.UserProfiles;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.Test;
import org.junit.runner.RunWith;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class ProfileFavoritesTest {
    @Test public void profileSwitchesSendTheSelectedUserOnEachFavoriteRequest() throws Exception {
        Context base = InstrumentationRegistry.getInstrumentation().getTargetContext();
        SharedPreferences preferences = base.getSharedPreferences("profile-test-" + UUID.randomUUID(), Context.MODE_PRIVATE);
        Context isolated = new ContextWrapper(base) {
            @Override public SharedPreferences getSharedPreferences(String name, int mode) { return preferences; }
        };
        var requests = new ArrayList<String>();
        var failure = new AtomicReference<Throwable>();
        try (var server = new ServerSocket(0)) {
            server.setSoTimeout(20000);
            Thread responder = new Thread(() -> {
                try {
                    for (int i = 0; i < 2; i++) try (var socket = server.accept()) {
                        var reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                        requests.add(reader.readLine());
                        while (!reader.readLine().isEmpty()) {}
                        socket.getOutputStream().write(("HTTP/1.1 200 OK\r\nContent-Type: application/json\r\nContent-Length: 2\r\nConnection: close\r\n\r\n[]").getBytes(StandardCharsets.UTF_8));
                    }
                } catch (Throwable error) { failure.set(error); }
            });
            responder.setDaemon(true); responder.start();
            var retrofit = new Retrofit.Builder().baseUrl("http://127.0.0.1:" + server.getLocalPort() + "/")
                    .addConverterFactory(GsonConverterFactory.create(JsonCodec.create())).build();
            var repository = new RemoteLearningRepository(retrofit);
            UUID first = UUID.randomUUID(), second = UUID.randomUUID();
            for (UUID profile : new UUID[]{first, second}) {
                UserProfiles.select(isolated, profile);
                assertEquals(profile, UserProfiles.id(isolated));
                var completed = new CountDownLatch(1);
                repository.getFavoriteCategories(UserProfiles.id(isolated)).start(result -> {
                    if (!result.isSuccess()) failure.set(result.error());
                    completed.countDown();
                });
                assertTrue(completed.await(20, TimeUnit.SECONDS));
                assertNull(failure.get());
            }
            responder.join(1000);
            assertEquals(2, requests.size());
            assertTrue(requests.get(0).contains("userId=" + first));
            assertTrue(requests.get(1).contains("userId=" + second));
        } finally { preferences.edit().clear().commit(); }
    }
}
