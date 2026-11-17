package com.project.ohl.domain.model;

import java.util.UUID;
import org.jspecify.annotations.NonNull;

public class User {

    private UUID id;

    private String name;

    private String email;

    private String phone;

    private UUID schoolId;

    private Role role;
    private String schoolName;
    private String cityName;
    private String countyName;

    public String getSchoolName() {
        return schoolName;
    }

    public String getCityName() {
        return cityName;
    }

    public String getCountyName() {
        return countyName;
    }

    public User() {}

    public User(UUID id, String name, String email, String phone, UUID schoolId, Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.schoolId = schoolId;
        this.role = role;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public UUID getSchoolId() {
        return schoolId;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public @NonNull String toString() {
        return "User{"
                + "id="
                + id
                + ", name='"
                + name
                + '\''
                + ", email='"
                + email
                + '\''
                + ", phone='"
                + phone
                + '\''
                + ", schoolId="
                + schoolId
                + ", role="
                + role
                + '}';
    }
}
