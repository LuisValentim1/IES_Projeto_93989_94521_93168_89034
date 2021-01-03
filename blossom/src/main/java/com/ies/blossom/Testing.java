package com.ies.blossom;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ies.blossom.entitys.HumMeasure;
import com.ies.blossom.entitys.HumSensor;
import com.ies.blossom.entitys.Parcel;
import com.ies.blossom.entitys.PhMeasure;
import com.ies.blossom.entitys.PhSensor;
import com.ies.blossom.entitys.Plant;

public class Testing {
	
	
	 public static void makeData(Parcel parcel, List<Plant> plants) {
		 plants = new ArrayList<Plant>();
		 Plant plant = new Plant("Daisy", "Daisius", 2.0, 1.0, 1.0, 2.0);
         parcel.setPlant(plant);
         plants.add(plant);
         plants.add(new Plant("Rosa", "Rosita", 1.5, 1.0, 1.0, 2.5));
         plants.add(new Plant("Margarida", "Margaridus", 0.5, 0.3, 1.0, 2.5));
         
         Double[] lista = new Double[]{1.0, 1.6, 1.4};
         Double[] lista2 = new Double[]{4.0, 1.6, 1.4};
         try {
        	 parcel.setPhSensors(makePhSensors(parcel, lista2));
			parcel.setHumSensors(makeHumSensors(parcel, lista));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
                  	
     }
     
     private static Set<HumSensor> makeHumSensors(Parcel parcel, Double[] lista) throws ParseException{
    	 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    	 
    	 Set<HumSensor> humsensores = new HashSet<HumSensor>();
    	 HumSensor humsensor;
    	 List<HumMeasure> humeasures;
    	 for (Double double1 : lista) {
    		 humsensor = new HumSensor(parcel, new Date((sdf.parse("2019-01-01 00:00:00")).getTime()));
             humeasures = new ArrayList<HumMeasure>();
             humeasures.add(new HumMeasure(humsensor, new Timestamp((sdf.parse("2019-01-03 00:00:00")).getTime()) , double1));
             humsensor.setMeasures(humeasures);
             humsensores.add(humsensor);
		}
    	return humsensores;
     }
     
     private static Set<PhSensor> makePhSensors(Parcel parcel, Double[] lista) throws ParseException{
    	 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    	 
    	 Set<PhSensor> phsensores = new HashSet<PhSensor>();
    	 PhSensor phsensor;
    	 List<PhMeasure> phmeasures;
    	 for (Double double1 : lista) {
    		 phsensor = new PhSensor(parcel, new Date((sdf.parse("2019-01-01 00:00:00")).getTime()));
             phmeasures = new ArrayList<PhMeasure>();
             phmeasures.add(new PhMeasure(phsensor, new Timestamp((sdf.parse("2019-01-03 00:00:00")).getTime()), double1));
             phsensor.setMeasures(phmeasures);
             phsensores.add(phsensor);
		}
    	return phsensores;
     }
	
	

}
