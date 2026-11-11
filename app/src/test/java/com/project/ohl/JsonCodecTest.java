package com.project.ohl;

import com.google.gson.JsonParseException;
import com.project.ohl.data.remote.JsonCodec;
import com.project.ohl.domain.model.Category;
import com.project.ohl.domain.model.Section;
import com.project.ohl.domain.model.User;
import java.time.LocalDate;
import java.util.UUID;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public class JsonCodecTest {
    @Test public void usesTheApiImageAndIconFields() {
        var gson = JsonCodec.create();
        var category = gson.fromJson("{\"id\":\"00000000-0000-4000-8000-00000000002a\",\"name\":\"A\",\"imageId\":7,\"parentId\":\"00000000-0000-4000-8000-000000000002\",\"sectionId\":\"00000000-0000-4000-8000-000000000001\"}", Category.class);
        assertEquals(UUID.fromString("00000000-0000-4000-8000-00000000002a"), category.getId());
        assertEquals(Integer.valueOf(7), category.getImageSource());
        assertEquals(UUID.fromString("00000000-0000-4000-8000-000000000002"), category.getParentId());
        assertTrue(gson.toJson(category).contains("\"id\":\"00000000-0000-4000-8000-00000000002a\""));
        assertTrue(gson.toJson(category).contains("\"imageId\":7"));
        assertFalse(gson.toJson(category).contains("imageSource"));
        var section = gson.fromJson("{\"id\":\"00000000-0000-4000-8000-000000000001\",\"name\":\"Video\",\"iconId\":3,\"imageId\":4}", Section.class);
        assertEquals(3, section.getIcon());
        assertEquals(4, section.getImageSource());
    }

    @Test public void keepsCatalogIdentityAndMediaIndependentOfDisplayNames() {
        var category = JsonCodec.create().fromJson("{\"code\":\"ALPHABET\",\"mediaKey\":\"alfabet\",\"name\":\"Alphabet\"}", Category.class);
        category.setName("Translated alphabet");
        assertEquals("ALPHABET", category.getCode());
        assertEquals("alfabet", category.getMediaKey());
        var section = JsonCodec.create().fromJson("{\"code\":\"VIDEO\",\"mediaKey\":\"video_section\",\"iconKey\":\"ic_video_section\"}", Section.class);
        assertEquals("VIDEO", section.getCode());
        assertEquals("ic_video_section", section.getIconKey());
    }

    @Test public void roundTripsIsoDatesWithoutReflectingIntoJavaTime() {
        var gson = JsonCodec.create();
        var user = gson.fromJson("{\"id\":\"00000000-0000-4000-8000-000000000001\",\"birthday\":\"2000-02-29\"}", User.class);
        assertEquals(LocalDate.of(2000, 2, 29), user.getBirthday());
        assertTrue(gson.toJson(user).contains("\"birthday\":\"2000-02-29\""));
        assertNull(gson.fromJson("{\"birthday\":null}", User.class).getBirthday());
        assertThrows(JsonParseException.class, () -> gson.fromJson("{\"birthday\":\"2001-02-29\"}", User.class));
    }

    @Test public void userDebugOutputDoesNotExposePasswords() {
        var user = JsonCodec.create().fromJson("{\"password\":\"private-value\"}", User.class);
        assertFalse(user.toString().contains("private-value"));
    }
}
