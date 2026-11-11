package com.project.ohl;

import com.project.ohl.data.remote.JsonCodec;
import com.project.ohl.domain.model.Category;
import com.project.ohl.domain.model.Section;
import com.project.ohl.domain.model.School;
import com.project.ohl.domain.model.City;
import java.util.UUID;
import java.util.Set;
import com.project.ohl.domain.model.User;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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


    @Test public void exchangesOnlySupportedProfileFields() {
        var gson = JsonCodec.create();
        var id = UUID.fromString("00000000-0000-4000-8000-000000000001");
        var user = new User(id, "Profile", "profile@example.test", "test-password", "0712345678", id, id);
        var value = gson.toJsonTree(user).getAsJsonObject();
        assertEquals(Set.of("id", "name", "email", "phone", "password", "roleId", "schoolId"), value.keySet());
        var restored = gson.fromJson(value, User.class);
        assertEquals("0712345678", restored.getPhone());
        assertEquals(id, restored.getId());
        assertFalse(restored.toString().contains("test-password"));
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
