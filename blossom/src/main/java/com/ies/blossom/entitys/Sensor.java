package com.ies.blossom.entitys;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public interface Sensor {
	
	
    public List<Measure> measures();
    public boolean isEmpty();
    public Measure getLatest();
    public Boolean isGood(Plant plant);
}