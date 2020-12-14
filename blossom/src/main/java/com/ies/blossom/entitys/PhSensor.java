package com.ies.blossom.entitys;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ph_sensors")
public class PhSensor {

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
    private List<PhMeasure> measures = new ArrayList<PhMeasure>();

    @Transient
	  private PhMeasure latest;

    public PhSensor() { super(); }

    public PhSensor(Parcel parcel, Date assocDate) {
        this.parcel = parcel;
        this.assocDate = assocDate;
    }

    public Long getSensorId() {
        return sensorId;
    }

    public void setSensorId(Long sensorId) {
        this.sensorId = sensorId;
    }

    public Parcel getParcel() {
        return parcel;
    }

    public void setParcel(Parcel parcel) {
        this.parcel = parcel;
    }

    public Date getAssocDate() {
        return assocDate;
    }

    public void setAssocDate(Date assocDate) {
        this.assocDate = assocDate;
    }

    public List<PhMeasure> getMeasures() {
        return measures;
    }

    public void setMeasures(List<PhMeasure> measures) {
        this.measures = measures;
        this.updateLatestMeasure();
    }
    
    public void addPhMeasure(PhMeasure measure) {
    	this.measures.add(measure);
    	this.updateLatestMeasure();
    }
    
    private void updateLatestMeasure() {
    	PhMeasure latest = null;
    	for (PhMeasure humMeasure : this.getMeasures()) {
    		if (latest == null || latest.getTimestamp().after(humMeasure.getTimestamp())) {
    			latest = humMeasure;
    		}
		}
    	this.latest = latest;
    	
    }
    
    public PhMeasure getLatest() {
    	return this.latest;
    }
}
