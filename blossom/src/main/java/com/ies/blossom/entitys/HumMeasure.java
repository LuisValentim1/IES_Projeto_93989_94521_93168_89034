package com.ies.blossom.entitys;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "hum_measures")
public class HumMeasure {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long measureId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "sensor_id")
    @JsonBackReference
    private HumSensor sensor;

    @Column(name = "timestamp")
    private Timestamp timestamp;

    @Column(name = "value")
    private Double value;

    public HumMeasure() { super(); }

    public HumMeasure(HumSensor sensor, Timestamp timestamp, Double value) {
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

    public HumSensor getSensor() {
        return sensor;
    }

    public void setSensor(HumSensor sensor) {
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
