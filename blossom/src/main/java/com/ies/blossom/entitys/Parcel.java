package com.ies.blossom.entitys;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;

import java.text.DecimalFormat;
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
    @JsonBackReference
    private User owner;

    @OneToMany(mappedBy = "parcel")
    @JsonManagedReference
    private Set<PhSensor> phSensors = new HashSet<>();

    @OneToMany(mappedBy = "parcel")
    @JsonManagedReference
    private Set<HumSensor> humSensors = new HashSet<>();

    @ManyToOne()
    @JoinColumn(name = "plant_id")
    @JsonBackReference
    private Plant plant;

    public Parcel() { super(); }

    public Parcel(User owner, String location) {
        this.location = location;
        this.owner = owner;
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
    
    public void addHumSensor(HumSensor sensor) {
    	this.humSensors.add(sensor);
    }
    
    public void addPhSensor(PhSensor sensor) {
    	this.phSensors.add(sensor);
    }
    
    public Double PhMeasure() {
    	if(this.phSensors.isEmpty()) {
    		return null;
    	}
    	
    	DecimalFormat formatter = new DecimalFormat("#0.00");
    	double sum = 0;
    	for (PhSensor sensor : phSensors) {
    		sum+=sensor.getMeasures().get(0).getValue();
		}
    	double value = Double.valueOf(sum/this.phSensors.size());
    	String tmp = formatter.format(value);
        
    	return Double.valueOf(tmp);
    }
    
    public Double HumMeasure() {
    	if(this.humSensors.isEmpty()) {
    		return null;
    	}
    	
    	DecimalFormat formatter = new DecimalFormat("#0.00");
    	double sum = 0;
    	for (HumSensor sensor : humSensors) {
    		sum+=sensor.getMeasures().get(0).getValue();
		}
    	double value = Double.valueOf(sum/this.humSensors.size());
    	String tmp = formatter.format(value);
    	
    	return Double.valueOf(tmp);
    }
    
    @Override
    public boolean equals(Object obj) {
    	if (this == obj) {
    		return true;
    	}
    	if (! (obj instanceof Parcel)) {
    		return false;
    	}
    	
    	Parcel other = (Parcel) obj;
    	if (this.getParcelId() == other.getParcelId()) {
    		return true;
    	}
    	return false;
    }
    
    
}
