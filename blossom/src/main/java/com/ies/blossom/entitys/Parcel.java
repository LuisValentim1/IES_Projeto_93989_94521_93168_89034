package com.ies.blossom.entitys;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "parcels")
public class Parcel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long parcelId;

    @Column(name = "location")
    private String location;

    @ManyToOne(optional = false)
    @JoinColumn(name = "owner_id")
    private User owner;

    @OneToMany(mappedBy = "parcel")
    private Set<PhSensor> phSensors = new HashSet<>();

    @OneToMany(mappedBy = "parcel")
    private Set<HumSensor> humSensors = new HashSet<>();

    @ManyToOne()
    @JoinColumn(name = "plant_id")
    private Plant plant;

    public Parcel() { super(); }

    public Parcel(Long parcelId, String location, User owner, Set<PhSensor> phSensors, Set<HumSensor> humSensors, Plant plant) {
        this.parcelId = parcelId;
        this.location = location;
        this.owner = owner;
        this.phSensors = phSensors;
        this.humSensors = humSensors;
        this.plant = plant;
    }

    public Parcel(String location, User owner, Set<PhSensor> phSensors, Set<HumSensor> humSensors) {
        this.location = location;
        this.owner = owner;
        this.phSensors = phSensors;
        this.humSensors = humSensors;
    }

    public Plant getPlant() {
        return plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public Long getParcelId() {
        return parcelId;
    }

    public void setParcelId(Long parcelId) {
        this.parcelId = parcelId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Set<PhSensor> getPhSensors() {
        return phSensors;
    }

    public void setPhSensors(Set<PhSensor> phSensors) {
        this.phSensors = phSensors;
    }

    public Set<HumSensor> getHumSensors() {
        return humSensors;
    }

    public void setHumSensors(Set<HumSensor> humSensors) {
        this.humSensors = humSensors;
    }
}
