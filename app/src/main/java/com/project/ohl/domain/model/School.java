package com.project.ohl.domain.model;

import java.util.UUID;
import org.jspecify.annotations.NonNull;

public class School {
    private UUID id;
    private String name;
    private UUID cityId;

    public UUID getCityId() {
        return cityId;
    }

    public School() {}

    public School(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public @NonNull String toString() {
        return "School{id=" + id + ", name='" + name + "'}";
    }
}
