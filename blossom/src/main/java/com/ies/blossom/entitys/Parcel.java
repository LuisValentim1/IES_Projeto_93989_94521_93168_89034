package com.ies.blossom.entitys;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import com.ies.blossom.model.GoodPlantModel;

import javax.persistence.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
    	return this.measure(false);
    }
    
    public Double HumMeasure() {
    	return this.measure(true);
    }
    
    public boolean noPhMeasure() {
    	return this.noMeasure(false, null);
    }
    
    public boolean noHumMeasure() {
		return this.noMeasure(true, null);
	}
    
    public Map<Sensor, Measure> getPhSensorTable(){
    	return this.getSensorTable(false);
    }
    
    public Map<Sensor, Measure> getHumSensorTable(){
    	return this.getSensorTable(true);
    }
    
    public Double generalHumMeasurePercentage() {
    	return this.generalMeasurePercentage(true);
    }
    
    public Double generalPhMeasurePercentage() {
    	return this.generalMeasurePercentage(false);
    }
    
    public GoodPlantModel checkPlantConditions() {
    	return new GoodPlantModel(this, 60.00);
    }
    
    private Double generalMeasurePercentage(boolean isHumidity) {
    	Set<Sensor> sensores = this.getSensores(isHumidity);
    	if(this.noMeasure(isHumidity, sensores)){
    		return null;
    	}
    	
    	int count = 0;
    	for (Sensor sensor : sensores) {
    		Boolean isGood = sensor.isGood(this.getPlant());
    		if(isGood != null && isGood.booleanValue()) {
    			count++;
    		}
		}
    	DecimalFormat formatter = new DecimalFormat("#0.00");
    	return Double.valueOf(formatter.format(Double.valueOf(100*count/sensores.size()))); 
    }
    
    private Double measure(boolean isHumidity) {
    	
    	Set<Sensor> sensores = this.getSensores(isHumidity);
    	if (this.noMeasure(isHumidity, sensores)) {
    		return null;
    	}
    	
    	DecimalFormat formatter = new DecimalFormat("#0.00");
    	
    	double sum = 0;
    	for (Sensor sensor :  sensores) {
    		if(!sensor.isEmpty()) {
    			sum+=sensor.getLatest().getValue();
    		}
		}
    	return Double.valueOf(formatter.format(Double.valueOf(sum/sensores.size())));
    	
    }
    
    private boolean noMeasure(boolean isHumidity, Set<Sensor> sensores) {
    	
    	if(this.isEmpty(isHumidity)) {
    		return true;
    	}
    	
    	if(sensores == null) {
    		sensores = this.getSensores(isHumidity);
    	}
    	
    	boolean noMeasure = true;
    	for (Sensor sensor : sensores) {
    		if(!sensor.isEmpty()) {
    			noMeasure = false;
    			break;
    		}
		}
    	return noMeasure;
    }
    
    private boolean isEmpty(boolean isHumidity) {
    	if(isHumidity) {
    		return this.humSensors.isEmpty();
    	} else {
    		return this.phSensors.isEmpty();
    	}
    }
    
    private Set<Sensor> getSensores(boolean isHumidity) {
    	Set<Sensor> set = new HashSet<Sensor>();
    	if(isHumidity) {
    		for (HumSensor sensor : this.humSensors) {
    			set.add((Sensor) sensor);
			}
    	} else {
    		for (PhSensor sensor : this.phSensors) {
    			set.add((Sensor) sensor);
			}
    	}
    	return set;    	
    }
    
    private Map<Sensor, Measure> getSensorTable(boolean isHumidity){
    	if(this.isEmpty(isHumidity)) {
    		return null;
    	}
    	
    	Map<Sensor, Measure> mapa = new HashMap<Sensor, Measure>();
    	Set<Sensor> set = this.getSensores(isHumidity);
    	for (Sensor sensor : set) {
			if(sensor.isEmpty()) {
				mapa.put(sensor, sensor.getLatest());
			} else {
				mapa.put(sensor, null);
			}
		}
    	return mapa;    	
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
