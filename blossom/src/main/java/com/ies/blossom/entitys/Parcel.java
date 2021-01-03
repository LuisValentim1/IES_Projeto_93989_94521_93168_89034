package com.ies.blossom.entitys;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ies.blossom.model.GoodPlantMeasureModel;
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
	
	private static double acceptablePercentage = 60.00;

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

    //Constructors
    //
    //
    public Parcel() { super(); }

    
    public Parcel(User owner, String location) {
        this.location = location;
        this.owner = owner;
    }

    //Get and Set
    //
    //
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
    
    //General Object public Methods
    //
    //
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
    
    //Other public methods
    public void addHumSensor(HumSensor sensor) {
    	this.humSensors.add(sensor);
    }
    
    public void addPhSensor(PhSensor sensor) {
    	this.phSensors.add(sensor);
    }
    
    public GoodPlantModel checkPlantConditions() {
    	GoodPlantMeasureModel phStatus = this.checkPlantMeasureConditions(false, this.getPlant(), null);
    	GoodPlantMeasureModel humStatus = this.checkPlantMeasureConditions(true, this.getPlant(), null);
    	return new GoodPlantModel(phStatus, humStatus);
    }
    
    public List<Plant> bestPlants(List<Plant> plants) {
    	return this.bestPlantsFor(plants, true, true, null);
    }
    
    //Public methods which use same private method(PH/HUM)
    //
    //
    public boolean noPhMeasure() {
    	return this.noMeasure(false, null);
    }
    
    public boolean noHumMeasure() {
		return this.noMeasure(true, null);
	}
    
    public Map<Sensor, Measure> getPhSensorTable(){
    	return this.getSensorTable(false, null);
    }
    
    public Map<Sensor, Measure> getHumSensorTable(){
    	return this.getSensorTable(true, null);
    }
    
    //Private Methods
    //
    //
    
    //Consider both variables
    private List<Plant> bestPlantsFor(List<Plant> plants, boolean usesPh, boolean usesHum, Set<Sensor> sensores) {
    	List<Plant> goodPlants = new ArrayList<Plant>();
    	for (Plant plant : plants) {
    		Boolean factor = this.plantIsGoodForParcel(plant, usesPh, usesHum, sensores);
    		if(factor != null && factor) {
    			goodPlants.add(plant);
    		}
		}
    	return goodPlants;
    }
    
    private Boolean plantIsGoodForParcel(Plant plant, boolean usesPh, boolean usesHum, Set<Sensor> sensores) {
    	Boolean goodPh;
    	if(usesPh) {
    		goodPh = this.plantIsGoodForParcelMeasure(false, plant, sensores);
    	} else {
    		goodPh = null;
    	}
    	
    	Boolean goodHum;
    	if(usesHum) {
    		goodHum = this.plantIsGoodForParcelMeasure(true, plant, sensores);
    	} else {
    		goodHum = null;
    	}
    	
    	if (goodHum == null && goodPh == null) {
    		return null;
    	} else if(goodHum == null && goodPh) {
    		return true;
    	} else if(goodHum && goodPh == null) {
    		return true;
    	} else if(goodHum && goodPh) {
    		return true;
    	} else {
    		return false;
    	}
    }
    
    //Private method for HUM/PH references
    //
    //
    
    private GoodPlantMeasureModel checkPlantMeasureConditions(boolean isHumidity, Plant plant, Set<Sensor> sensores) {
    	if(this.noMeasure(isHumidity, null)) {
    		return null;
    	} 
    	Double percentage = this.generalMeasurePercentage(isHumidity, plant, sensores);
    	Boolean goodPh = this.plantIsGoodForParcel(plant, !isHumidity, isHumidity, sensores);
    	return new GoodPlantMeasureModel(percentage, goodPh);
    }
    
    private Boolean plantIsGoodForParcelMeasure(boolean isHumidity, Plant plant, Set<Sensor> sensores) {
    	Double percentage = this.generalMeasurePercentage(isHumidity, plant, sensores);
    	
    	if(percentage == null) {
    		return null;
    	}
    	if(percentage < Parcel.acceptablePercentage) {
    		return false;
    	} else {
    		return true;
    	}
		
	}

	private Double generalMeasurePercentage(boolean isHumidity, Plant plant, Set<Sensor> sensores) {
    	sensores = this.getSensores(isHumidity, sensores);
    	if(this.noMeasure(isHumidity, sensores)){
    		return null;
    	}
    	
    	int count = 0;
    	for (Sensor sensor : sensores) {
    		Boolean isGood = sensor.isGood(plant);
    		if(isGood != null && isGood.booleanValue()) {
    			count++;
    		}
		}
    	DecimalFormat formatter = new DecimalFormat("#0.00");
    	return Double.valueOf(formatter.format(Double.valueOf(100*count/sensores.size()))); 
    }
    
    private Double measure(boolean isHumidity, Set<Sensor> sensores) {
    	
    	sensores = this.getSensores(isHumidity, sensores);
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
    	
    	sensores = this.getSensores(isHumidity, sensores);
    	
    	boolean noMeasure = true;
    	for (Sensor sensor : sensores) {
    		if(!sensor.isEmpty()) {
    			noMeasure = false;
    			break;
    		}
		}
    	return noMeasure;
    }
    
    private Map<Sensor, Measure> getSensorTable(boolean isHumidity, Set<Sensor> sensores){
    	if(this.isEmpty(isHumidity)) {
    		return null;
    	}
    	
    	Map<Sensor, Measure> mapa = new HashMap<Sensor, Measure>();
    	
    	sensores = this.getSensores(isHumidity, sensores);
    	for (Sensor sensor : sensores) {
			if(sensor.isEmpty()) {
				mapa.put(sensor, sensor.getLatest());
			} else {
				mapa.put(sensor, null);
			}
		}
    	return mapa;    	
    }
    
    private boolean isEmpty(boolean isHumidity) {
    	if(isHumidity) {
    		return this.humSensors.isEmpty();
    	} else {
    		return this.phSensors.isEmpty();
    	}
    }
    
    private Set<Sensor> getSensores(boolean isHumidity, Set<Sensor> sensores) {
    	if (sensores != null) {
    		return sensores;
    	}
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
}
