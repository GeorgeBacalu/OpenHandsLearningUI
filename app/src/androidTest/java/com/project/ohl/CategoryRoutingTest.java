package com.project.ohl;

import android.content.Context;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import com.project.ohl.data.remote.CategoryApi;
import com.project.ohl.data.remote.JsonCodec;
import com.project.ohl.domain.model.Category;
import com.project.ohl.presentation.CategoryNavigation;
import com.project.ohl.presentation.video_section.all_categories.AllCategoriesActivity;
import com.project.ohl.presentation.video_section.alphabet.AlphabetActivity;
import com.project.ohl.presentation.video_section.animals.household.HouseholdAnimalsActivity;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import org.junit.Test;
import org.junit.runner.RunWith;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class CategoryRoutingTest {
    private static final UUID ALPHABET = UUID.fromString("990e2591-117c-3b04-9146-dbdf0ca772b4");
    private static final UUID VIDEO = UUID.fromString("20a6880c-2e37-32e3-8e45-07a17e99a000");

    private Category category(UUID id, String name) {
        var category = new Category(id, name);
        category.setSectionId(VIDEO);
        return category;
    }

    @Test public void renamedParentsKeepTheirDedicatedScreenAndSelectedUuid() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        var parent = category(ALPHABET, "Alfabet");
        var original = CategoryNavigation.intent(context, parent);
        parent.setName("Translated and renamed");
        var renamed = CategoryNavigation.intent(context, parent);
        assertEquals(AlphabetActivity.class.getName(), original.getComponent().getClassName());
        assertEquals(original.getComponent(), renamed.getComponent());
        assertEquals(ALPHABET.toString(), renamed.getStringExtra(CategoryNavigation.PARENT_ID));
        assertEquals(VIDEO.toString(), renamed.getStringExtra(CategoryNavigation.SECTION_ID));
        assertFalse(renamed.hasExtra("parentCode"));
    }

    @Test public void nestedAndNewCategoriesUseTheirOwnIds() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        UUID household = UUID.fromString("07fd780a-80ed-38c1-bdc5-5a66803a20f2");
        var nested = CategoryNavigation.intent(context, category(household, "Renamed animals"));
        assertEquals(HouseholdAnimalsActivity.class.getName(), nested.getComponent().getClassName());
        assertEquals(household.toString(), nested.getStringExtra(CategoryNavigation.PARENT_ID));
        UUID createdId = UUID.randomUUID();
        var created = CategoryNavigation.intent(context, category(createdId, "Alfabet"));
        assertEquals(AllCategoriesActivity.class.getName(), created.getComponent().getClassName());
        assertEquals(createdId.toString(), created.getStringExtra(CategoryNavigation.PARENT_ID));
    }

    @Test public void directChildRequestsUseTheSameUuidAfterRenaming() throws Exception {
        var failure = new AtomicReference<Throwable>();
        var requests = new ArrayList<String>();
        try (var server = new ServerSocket(0)) {
            server.setSoTimeout(10000);
            var responder = new Thread(() -> {
                try {
                    for (int i = 0; i < 2; i++) try (var socket = server.accept()) {
                        socket.setSoTimeout(10000);
                        var reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                        requests.add(reader.readLine());
                        String line;
                        while ((line = reader.readLine()) != null && !line.isEmpty()) {}
                        byte[] body = ("[{\"id\":\"00000000-0000-4000-8000-000000000001\","
                                + "\"name\":\"A\",\"parentId\":\"" + ALPHABET + "\",\"sectionId\":\"" + VIDEO + "\"}]")
                                .getBytes(StandardCharsets.UTF_8);
                        var output = socket.getOutputStream();
                        output.write(("HTTP/1.1 200 OK\r\nContent-Type: application/json\r\nContent-Length: "
                                + body.length + "\r\nConnection: close\r\n\r\n").getBytes(StandardCharsets.UTF_8));
                        output.write(body);
                        output.flush();
                    }
                } catch (Throwable exception) {
                    failure.set(exception);
                }
            });
            responder.setDaemon(true);
            responder.start();
            var api = new Retrofit.Builder().baseUrl("http://127.0.0.1:" + server.getLocalPort() + "/")
                    .addConverterFactory(GsonConverterFactory.create(JsonCodec.create())).build().create(CategoryApi.class);
            var parent = category(ALPHABET, "Alfabet");
            for (String label : new String[]{"Alfabet", "Translated parent"}) {
                parent.setName(label);
                var response = api.getCategoriesByParentIdAndSectionIdAndName(parent.getId(), parent.getSectionId(), "").execute();
                assertTrue(response.isSuccessful());
                assertEquals(ALPHABET, response.body().get(0).getParentId());
            }
            responder.join(10000);
            assertFalse(responder.isAlive());
            assertNull(failure.get());
            assertEquals(2, requests.size());
            assertEquals(requests.get(0), requests.get(1));
            assertTrue(requests.get(0).contains("parentId=" + ALPHABET));
            assertTrue(requests.get(0).contains("sectionId=" + VIDEO));
            assertFalse(requests.get(0).contains("parentCode"));
            assertFalse(requests.get(0).contains("Alfabet"));
        }
    }
}
