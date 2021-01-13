package com.ies.blossom.dto;

import java.sql.Date;

public class SensorDto {

    private Long id;
    private Long parcelId;
    private Long ownerId;

    public SensorDto() {}

    public SensorDto(Long parcelId, Date assoc_date) {
        this.parcelId = parcelId;
    }

    public Long getParcelId() {
        return parcelId;
    }

    public void setParcelId(Long parcelId) {
        this.parcelId = parcelId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

}
