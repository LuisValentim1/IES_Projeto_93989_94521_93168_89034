package com.ies.blossom.entitys;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "ph_measures")
public class PhMeasure {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long measureId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "sensor_id")
    @JsonBackReference
    private PhSensor sensor;

    @Column(name = "timestamp")
    private Timestamp timestamp;

    @Column(name = "value")
    private Double value;

    public PhMeasure() { super(); }

    public PhMeasure(PhSensor sensor, Timestamp timestamp, Double value) {
        this.sensor = sensor;
        this.timestamp = timestamp;
        this.value = value;
    }

    public Long getMeasureId() {
        return measureId;
    }

    public void setMeasureId(Long measureId) {
        this.measureId = measureId;
    }

    public PhSensor getSensor() {
        return sensor;
    }

    public void setSensor(PhSensor sensor) {
        this.sensor = sensor;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
