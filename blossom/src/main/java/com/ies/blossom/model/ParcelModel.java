package com.ies.blossom.model;

import java.util.List;
import java.util.Set;

import com.ies.blossom.entitys.Plant;

public class ParcelModel {
    private Long parcelId;
    private String location;
    private Long owner;
    private Set<Long> phSensors;
    private Set<Long> humSensors;
    
    // quando se pede o form para saber q plantas estao disponíveis
    private List<Plant> plants;
    
    // quando se faz post do form
    private Long plant;


    public ParcelModel() {
    }

    public ParcelModel(String location, Long plant) {
        this.location = location;
        this.plant = plant;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getOwner() {
        return owner;
    }

    public void setOwner(Long owner) {
        this.owner = owner;
    }

    public Set<Long> getPhSensors() {
        return phSensors;
    }

    public void setPhSensors(Set<Long> phSensors) {
        this.phSensors = phSensors;
    }

    public Set<Long> getHumSensors() {
        return humSensors;
    }

    public void setHumSensors(Set<Long> humSensors) {
        this.humSensors = humSensors;
    }

    public Long getPlant() {
        return plant;
    }

    public void setPlant(Long plant) {
        this.plant = plant;
    }

    public Long getParcelId() {
        return parcelId;
    }

    public void setParcelId(Long parcelId) {
        this.parcelId = parcelId;
    }

    public List<Plant> getPlants() {
        return plants;
    }

    public void setPlants(List<Plant> plants) {
        this.plants = plants;
    }
}
