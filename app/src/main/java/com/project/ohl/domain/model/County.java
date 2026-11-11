package com.project.ohl.domain.model;

import java.util.UUID;

public class County {
    private UUID id;
    private String name;
    public UUID getId() { return id; }
    public void setId(UUID value) { id = value; }
    public String getName() { return name; }
    public void setName(String value) { name = value; }
}
