package com.ies.blossom.entitys;

import javax.persistence.*;
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
    @Column(name = "owner")
    private User owner;

    @OneToMany(mappedBy = "parcel")
    @Column(name = "ph_sensors")
    private Set<PhSensor> phSensors;

    @OneToMany(mappedBy = "parcel")
    @Column(name = "hum_sensors")
    private Set<HumSensor> humSensors;

    @ManyToOne()
    @Column(name = "plant")
    private Plant plant;

    public Parcel() {}

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
