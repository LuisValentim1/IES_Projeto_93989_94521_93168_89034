package com.ies.blossom.dto;

public class ParcelDto {

    private Long id;
    private Long owner;
    private String location;
    private Long plantId;

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

    public Long getPlantId() {
        return plantId;
    }

    public void setPlantId(Long plantId) {
        this.plantId = plantId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
