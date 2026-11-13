package com.project.ohl.domain.model;

import java.util.UUID;

public class City {
    private UUID id;
    private String name;
    private UUID countyId;
    public UUID getCountyId() { return countyId; }
    public void setCountyId(UUID value) { countyId = value; }
    public UUID getId() { return id; }
    public void setId(UUID value) { id = value; }
    public String getName() { return name; }
    public void setName(String value) { name = value; }
}
