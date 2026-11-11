package com.project.ohl.domain.model;

import java.util.UUID;

public class School {
    private UUID id;
    private String name;
    private UUID cityId;
    public UUID getCityId() { return cityId; }
    public void setCityId(UUID value) { cityId = value; }
    public School() {}
    public School(UUID id, String name) { this.id = id; this.name = name; }
    public UUID getId() { return id; }
    public void setId(UUID value) { id = value; }
    public String getName() { return name; }
    public void setName(String value) { name = value; }
    @Override public String toString() { return "School{id=" + id + ", name='" + name + "'}"; }
}
