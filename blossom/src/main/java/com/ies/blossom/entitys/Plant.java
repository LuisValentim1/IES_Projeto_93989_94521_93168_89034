package com.ies.blossom.entitys;

import javax.persistence.*;

@Entity
@Table(name = "plants")
public class Plant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long plantId;

    @Column(name = "cientific_name")
    private String cientificName;

    @Column(name = "english_name")
    private String englishName;

    @Column(name = "ph_max")
    private Double phMax;

    @Column(name = "ph_min")
    private Double phMin;

    @Column(name = "hum_min")
    private Double humMin;

    @Column(name = "hum_max")
    private Double humMax;

    public Plant() {}

    public Plant(Long plantId, String cientificName, String englishName, Double phMax, Double phMin, Double humMin, Double humMax) {
        this.plantId = plantId;
        this.cientificName = cientificName;
        this.englishName = englishName;
        this.phMax = phMax;
        this.phMin = phMin;
        this.humMin = humMin;
        this.humMax = humMax;
    }

    public Long getPlantId() {
        return plantId;
    }

    public void setPlantId(Long plantId) {
        this.plantId = plantId;
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
