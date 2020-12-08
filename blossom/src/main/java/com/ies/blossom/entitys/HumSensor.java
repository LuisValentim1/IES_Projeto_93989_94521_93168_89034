package com.ies.blossom.entitys;

import javax.persistence.*;
import java.sql.Date;
import java.util.Set;

@Entity
@Table(name = "hum_sensors")
public class HumSensor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long sensorId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "parcel_id")
    private Parcel parcel;

    @Column(name = "assoc_date")
    private Date assocDate;

    @OneToMany(mappedBy = "sensor")
    private Set<HumMeasure> measures;

    public HumSensor() { super(); }

    public HumSensor(Long sensorId, Parcel parcel, Date assocDate, Set<HumMeasure> measures) {
        this.sensorId = sensorId;
        this.parcel = parcel;
        this.assocDate = assocDate;
        this.measures = measures;
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

    public Set<HumMeasure> getMeasures() {
        return measures;
    }

    public void setMeasures(Set<HumMeasure> measures) {
        this.measures = measures;
    }
}
