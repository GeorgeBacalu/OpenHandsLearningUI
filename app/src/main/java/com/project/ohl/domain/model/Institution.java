package com.project.ohl.domain.model;

import java.util.UUID;

public class Institution {
    private UUID id;
    private String school;
    public Institution() {}
    public Institution(UUID id, String school) { this.id = id; this.school = school; }
    public UUID getId() { return id; }
    public void setId(UUID value) { id = value; }
    public String getSchool() { return school; }
    public void setSchool(String value) { school = value; }
    @Override public String toString() { return "Institution{id=" + id + ", school='" + school + "'}"; }
}
