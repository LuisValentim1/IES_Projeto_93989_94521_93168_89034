package com.ies.blossom.dto;

public class PlantDto {
    
    private String cientificName;
    private String englishName;
    private Double phMax;
    private Double phMin;
    private Double humMin;
    private Double humMax;

    public PlantDto() {}

    public PlantDto(String cientificName, String englishName, Double phMax, Double phMin, Double humMin,
            Double humMax) {
        this.cientificName = cientificName;
        this.englishName = englishName;
        this.phMax = phMax;
        this.phMin = phMin;
        this.humMin = humMin;
        this.humMax = humMax;
    }

    public String getCientificName() {
        return cientificName;
    }

    public void setCientificName(String cientificName) {
        this.cientificName = cientificName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public Double getPhMax() {
        return phMax;
    }

    public void setPhMax(Double phMax) {
        this.phMax = phMax;
    }

    public Double getPhMin() {
        return phMin;
    }

    public void setPhMin(Double phMin) {
        this.phMin = phMin;
    }

    public Double getHumMin() {
        return humMin;
    }

    public void setHumMin(Double humMin) {
        this.humMin = humMin;
    }

    public Double getHumMax() {
        return humMax;
    }

    public void setHumMax(Double humMax) {
        this.humMax = humMax;
    }

    
}
