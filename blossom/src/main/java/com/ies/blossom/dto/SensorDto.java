package com.ies.blossom.dto;

import java.sql.Date;

public class SensorDto {

    private Long parcelId;

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

}
