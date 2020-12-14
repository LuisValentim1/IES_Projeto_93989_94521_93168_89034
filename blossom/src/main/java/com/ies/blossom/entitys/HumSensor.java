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
        this.updateLatestMeasure();
    }
    
    public void addHumMeasure(HumMeasure measure) {
    	this.measures.add(measure);
    	this.updateLatestMeasure();
    }
    
    private void updateLatestMeasure() {
    	HumMeasure latest = null;
    	for (HumMeasure humMeasure : this.getMeasures()) {
    		if (latest == null || latest.getTimestamp().after(humMeasure.getTimestamp())) {
    			latest = humMeasure;
    		}
		}
    	this.latest = latest;
    	
    }
    
    public HumMeasure getLatest() {
    	return this.latest;
    }
}