package com.ies.blossom.entitys;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ph_sensors")
public class PhSensor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long sensorId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "parcel_id")
    private Parcel parcel;

    @Column(name = "assoc_date")
    private Date assocDate;

    @OneToMany(mappedBy = "sensor")
    private Set<PhMeasure> measures = new HashSet<>();

    public PhSensor() { super(); }

    public PhSensor(Long sensorId, Parcel parcel, Date assocDate, Set<PhMeasure> measures) {
        this.sensorId = sensorId;
        this.parcel = parcel;
        this.assocDate = assocDate;
        this.measures = measures;
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

    public Set<PhMeasure> getMeasures() {
        return measures;
    }

    public void setMeasures(Set<PhMeasure> measures) {
        this.measures = measures;
    }
}
