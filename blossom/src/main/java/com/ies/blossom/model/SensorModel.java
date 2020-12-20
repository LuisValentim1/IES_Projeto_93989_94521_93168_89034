package com.ies.blossom.model;


import java.sql.Date;

// ESTA CLASSE NAO ESTA A SER USADA EM LADO NENHUM
// FOI APENAS UMA EXPERIENCIA

public class SensorModel {

    private Long sensorId;
    private Long parcelId;
    private String parcelLocation;
    private Date assocDate;
    private Double lastMeasurement;

    public SensorModel() {}

    public SensorModel(Long sensorId, Long parcelId, String parcelLocation, Date assocDate, Double lastMeasurement) {
        this.sensorId = sensorId;
        this.parcelId = parcelId;
        this.parcelLocation = parcelLocation;
        this.assocDate = assocDate;
        this.lastMeasurement = lastMeasurement;
    }

    public Long getSensorId() {
        return sensorId;
    }

    public void setSensorId(Long sensorId) {
        this.sensorId = sensorId;
    }

    public Long getParcelId() {
        return parcelId;
    }

    public void setParcelId(Long parcelId) {
        this.parcelId = parcelId;
    }

    public String getParcelLocation() {
        return parcelLocation;
    }

    public void setParcelLocation(String parcelLocation) {
        this.parcelLocation = parcelLocation;
    }

    public Date getAssocDate() {
        return assocDate;
    }

    public void setAssocDate(Date assocDate) {
        this.assocDate = assocDate;
    }

    public Double getLastMeasurement() {
        return lastMeasurement;
    }

    public void setLastMeasurement(Double lastMeasurement) {
        this.lastMeasurement = lastMeasurement;
    }
}
