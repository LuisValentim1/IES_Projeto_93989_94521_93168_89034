package com.ies.blossom.model;

public class ChangePlantModel {
    
    private Long parcelId;
    private Long previousPlantId;
    private Long currentPlantId;

    public ChangePlantModel() {}

    public ChangePlantModel(Long parcelId, Long previousPlantId, Long currentPlantId) {
        this.parcelId = parcelId;
        this.previousPlantId = previousPlantId;
        this.currentPlantId = currentPlantId;
    }

    public Long getParcelId() {
        return parcelId;
    }

    public void setParcelId(Long parcelId) {
        this.parcelId = parcelId;
    }

    public Long getPreviousPlantId() {
        return previousPlantId;
    }

    public void setPreviousPlantId(Long previousPlantId) {
        this.previousPlantId = previousPlantId;
    }

    public Long getCurrentPlantId() {
        return currentPlantId;
    }

    public void setCurrentPlantId(Long currentPlantId) {
        this.currentPlantId = currentPlantId;
    }
    
}
