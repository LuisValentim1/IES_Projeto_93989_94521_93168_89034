package com.ies.blossom.dto;

public class ParcelDto {

    private Long owner;
    private String location;

    public ParcelDto() {}

    public ParcelDto(Long owner, String location) {
        this.owner = owner;
        this.location = location;
    }

    public Long getOwner() {
        return owner;
    }

    public void setOwner(Long owner) {
        this.owner = owner;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
