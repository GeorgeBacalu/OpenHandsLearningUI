package com.project.ohl;

import com.project.ohl.data.remote.JsonCodec;
import com.project.ohl.domain.model.Category;
import com.project.ohl.domain.model.City;
import com.project.ohl.domain.model.Role;
import com.project.ohl.domain.model.School;
import com.project.ohl.domain.model.User;
import java.util.Set;
import java.util.UUID;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class JsonCodecTest {
    @Test
    public void readsCategoryIdsAndMediaUrlsWithoutObsoleteCardState() {
        var gson = JsonCodec.create();

        String id = "00000000-0000-4000-8000-000000000001";
        String base = "https://open-hands-learning.s3.us-east-1.amazonaws.com/categories/alfabet/a_breve/";

        var category =
                gson.fromJson(
                        "{\"id\":\""
                                + id
                                + "\",\"imageUrl\":\""
                                + base
                                + "main.webp\","
                                + "\"videoUrl\":\""
                                + base
                                + "main.mp4\"}",
                        Category.class);

        category.setName("A different translated label");

        assertEquals(UUID.fromString(id), category.getId());
        assertEquals(base + "main.webp", category.getImageUrl());
        assertEquals(base + "main.mp4", category.getVideoUrl());
        assertFalse(gson.toJson(category).contains("isExpanded"));
        assertFalse(gson.toJson(category).contains("mediaKey"));
    }

    @Test
    public void exchangesOnlySupportedProfileFields() {
        var gson = JsonCodec.create();
        var id = UUID.fromString("00000000-0000-4000-8000-000000000001");
        var user = new User(id, "Profile", "profile@example.test", "0712345678", id, Role.USER);
        var value = gson.toJsonTree(user).getAsJsonObject();

        assertEquals(Set.of("id", "name", "email", "phone", "role", "schoolId"), value.keySet());

        var restored = gson.fromJson(value, User.class);

        assertEquals("0712345678", restored.getPhone());
        assertEquals("USER", value.get("role").getAsString());
        assertEquals(Role.USER, restored.getRole());
        assertEquals(id, restored.getId());
        assertFalse(restored.toString().contains("test-password"));
    }

    @Test
    public void exchangesSchoolAndLocationUuids() {
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

    @Test
    public void accountJsonDoesNotRetainPasswords() {
        var user = JsonCodec.create().fromJson("{\"password\":\"private-value\"}", User.class);

        assertFalse(JsonCodec.create().toJson(user).contains("password"));
        assertFalse(user.toString().contains("private-value"));
    }
}
