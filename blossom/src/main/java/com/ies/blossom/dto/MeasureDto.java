package com.ies.blossom.dto;

public class MeasureDto {

    private Long id;
    private Long sensorId;
    private Double value;

    public MeasureDto() {}

    public MeasureDto(Long sensorId, Double value) {
        this.sensorId = sensorId;
        this.value = value;
    }

    public Long getSensorId() {
        return sensorId;
    }

    public void setSensorId(Long sensorId) {
        this.sensorId = sensorId;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
