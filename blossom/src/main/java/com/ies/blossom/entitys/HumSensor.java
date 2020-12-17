package com.ies.blossom.entitys;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "hum_sensors")
public class HumSensor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long sensorId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "parcel_id")
    @JsonBackReference
    private Parcel parcel;

    @Column(name = "assoc_date")
    private Date assocDate;

    @OneToMany(mappedBy = "sensor")
    @JsonManagedReference
    private List<HumMeasure> measures = new ArrayList<HumMeasure>();
    
    @Transient
	  private HumMeasure latest;

    public HumSensor() { super(); }

    public HumSensor(Parcel parcel, Date assocDate) {
        this.parcel = parcel;
        this.assocDate = assocDate;
    }

    public Parcel getParcel() {
        return parcel;
    }

    public void setParcel(Parcel parcel) {
        this.parcel = parcel;
    }

    public Long getSensorId() {
        return sensorId;
    }

    public void setSensorId(Long sensorId) {
        this.sensorId = sensorId;
    }

    public Date getAssocDate() {
        return assocDate;
    }

    public void setAssocDate(Date assocDate) {
        this.assocDate = assocDate;
    }

    public List<HumMeasure> getMeasures() {
        return measures;
    }

    public void setMeasures(List<HumMeasure> measures) {
        this.measures = measures;
        HumMeasure latest = null;
    	for (HumMeasure humMeasure : this.getMeasures()) {
    		if (latest == null || latest.getTimestamp().after(humMeasure.getTimestamp())) {
    			latest = humMeasure;
    		}
		}
    	this.latest = latest;
    }
    
    public void addHumMeasure(HumMeasure measure) {
    	this.measures.add(measure);
    	this.latest = measure;
    }
    
    public HumMeasure getLatest() {
    	return this.latest;
    }
    
    @Override
    public boolean equals(Object obj) {
    	if (this == obj) {
    		return true;
    	}
    	if (! (obj instanceof Plant)) {
    		return false;
    	}
    	HumSensor other = (HumSensor) obj;
    	if (this.getSensorId() == other.getSensorId()) {
    		return true;
    	}
    	return false;
    	
    }
}