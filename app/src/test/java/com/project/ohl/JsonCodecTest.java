package com.project.ohl;

import com.project.ohl.data.remote.JsonCodec;
import com.project.ohl.domain.model.Category;
import com.project.ohl.domain.model.Section;
import com.project.ohl.domain.model.User;
import com.google.gson.JsonParseException;
import java.time.LocalDate;
import org.junit.Test;
import static org.junit.Assert.*;

public class JsonCodecTest {
    @Test public void usesTheApiImageAndIconFields() {
        var gson = JsonCodec.create();
        var category = gson.fromJson("{\"id\":42,\"name\":\"A\",\"imageId\":7,\"parentId\":2,\"sectionId\":1}", Category.class);
        assertEquals(Integer.valueOf(7), category.getImageSource());
        assertEquals(Long.valueOf(2), category.getParentId());
        assertTrue(gson.toJson(category).contains("\"imageId\":7"));
        assertFalse(gson.toJson(category).contains("imageSource"));
        var section = gson.fromJson("{\"id\":1,\"name\":\"Video\",\"iconId\":3,\"imageId\":4}", Section.class);
        assertEquals(3, section.getIcon());
        assertEquals(4, section.getImageSource());
    }

    @Test public void roundTripsIsoDatesWithoutReflectingIntoJavaTime() {
        var gson = JsonCodec.create();
        var user = gson.fromJson("{\"id\":1,\"birthday\":\"2000-02-29\"}", User.class);
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
