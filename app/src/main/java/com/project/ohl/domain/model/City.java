package com.project.ohl.domain.model;

import java.util.UUID;

public class City {
    private UUID id;
    private String name;
    private UUID countyId;

    public UUID getCountyId() {
        return countyId;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
