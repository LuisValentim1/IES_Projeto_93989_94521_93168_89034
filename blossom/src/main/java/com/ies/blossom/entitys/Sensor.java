package com.ies.blossom.entitys;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface Sensor {
	
	@JsonIgnore
    public List<Measure> measures();
    @JsonIgnore
    public boolean isEmpty();
    @JsonIgnore
    public Measure getLatest();
    @JsonIgnore
    public Boolean isGood(Plant plant);
}