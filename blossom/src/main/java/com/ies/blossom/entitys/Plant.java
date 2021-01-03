package com.ies.blossom.entitys;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "plants")
public class Plant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long plantId;

    @Column(name = "cientific_name")
    private String cientificName;

    @Column(name = "english_name")
    private String englishName;

    @Column(name = "ph_max")
    private Double phMax;

    @Column(name = "ph_min")
    private Double phMin;

    @Column(name = "hum_min")
    private Double humMin;

    @Column(name = "hum_max")
    private Double humMax;

    @OneToMany(mappedBy = "plant")
    @JsonManagedReference
    private Set<Parcel> parcels = new HashSet<>();

    public Plant() { super(); }

    public Plant(String cientificName, String englishName, Double phMax, Double phMin, Double humMin, Double humMax) {
        this.cientificName = cientificName;
        this.englishName = englishName;
        this.phMax = phMax;
        this.phMin = phMin;
        this.humMin = humMin;
        this.humMax = humMax;
    }

    public Set<Parcel> getParcels() {
        return parcels;
    }

    public void setParcels(Set<Parcel> parcels) {
        this.parcels = parcels;
    }

    public Long getPlantId() {
        return plantId;
    }

    public void setPlantId(Long plantId) {
        this.plantId = plantId;
    }

    public String getCientificName() {
        return cientificName;
    }

    public void setCientificName(String cientificName) {
        this.cientificName = cientificName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public Double getPhMax() {
        return phMax;
    }

    public void setPhMax(Double phMax) {
        this.phMax = phMax;
    }

    public Double getPhMin() {
        return phMin;
    }

    public void setPhMin(Double phMin) {
        this.phMin = phMin;
    }

    public Double getHumMin() {
        return humMin;
    }

    public void setHumMin(Double humMin) {
        this.humMin = humMin;
    }

    public Double getHumMax() {
        return humMax;
    }

    public void setHumMax(Double humMax) {
        this.humMax = humMax;
    }
    
    public Boolean isGoodPh(Double phMeasure) {
    	if (phMeasure == null) {
    		return null;
    	}
        return this.getPhMin() <= phMeasure && phMeasure <= this.getPhMax();
    }
    
    public Boolean isGoodHum(Double humMeasure) {
    	if (humMeasure == null) {
    		return null;
    	}
        return this.getPhMin() <= humMeasure && humMeasure <= this.getPhMax();
    }
    
    @Override
    public boolean equals(Object obj) {
    	if (this == obj) {
    		return true;
    	}
    	if (! (obj instanceof Plant)) {
    		return false;
    	}
    	Plant other = (Plant) obj;
    	if (this.getPlantId() == other.getPlantId()) {
    		return true;
    	}
    	return false;
    	
    }
    
    
}
