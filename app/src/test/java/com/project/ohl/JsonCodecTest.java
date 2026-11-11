package com.project.ohl;

import com.google.gson.JsonParseException;
import com.project.ohl.data.remote.JsonCodec;
import com.project.ohl.domain.model.Category;
import com.project.ohl.domain.model.Section;
import com.project.ohl.domain.model.School;
import com.project.ohl.domain.model.City;
import java.util.UUID;
import com.project.ohl.domain.model.User;
import java.time.LocalDate;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

public class JsonCodecTest {
    @Test public void exchangesStableMediaKeysWithoutDeviceResourceIds() {
        var gson = JsonCodec.create();
        var category = gson.fromJson("{\"mediaKey\":\"dactilema_a\"}", Category.class);
        category.setImageSource(123);
        assertEquals("dactilema_a", category.getMediaKey());
        assertFalse(gson.toJson(category).contains("imageSource"));
        assertFalse(gson.toJson(category).contains("imageId"));
        var section = gson.fromJson("{\"mediaKey\":\"video_section\",\"iconKey\":\"ic_video_section\"}", Section.class);
        section.setIcon(456);
        assertFalse(gson.toJson(section).contains("\"icon\":"));
    }

    @Test public void keepsCatalogIdentityAndMediaIndependentOfDisplayNames() {
        var category = JsonCodec.create().fromJson("{\"mediaKey\":\"alfabet\",\"name\":\"Alphabet\"}", Category.class);
        category.setName("Translated alphabet");
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

    @Test public void exchangesSchoolAndLocationUuids() {
        var gson = JsonCodec.create();
        String id = "00000000-0000-4000-8000-000000000104";
        var school = gson.fromJson("{\"name\":\"School A\",\"cityId\":\"" + id + "\"}", School.class);
        assertEquals("School A", school.getName());
        assertEquals(UUID.fromString(id), school.getCityId());
        var city = gson.fromJson("{\"name\":\"Cluj\",\"countyId\":\"" + id + "\"}", City.class);
        assertEquals(UUID.fromString(id), city.getCountyId());
        var user = gson.fromJson("{\"schoolId\":\"" + id + "\"}", User.class);
        assertEquals(UUID.fromString(id), user.getSchoolId());
        assertFalse(gson.toJson(user).contains("institutionId"));
        assertFalse(gson.toJson(user).contains("classroomId"));
    }

    @Test public void userDebugOutputDoesNotExposePasswords() {
        var user = JsonCodec.create().fromJson("{\"password\":\"private-value\"}", User.class);
        assertFalse(user.toString().contains("private-value"));
    }
}
