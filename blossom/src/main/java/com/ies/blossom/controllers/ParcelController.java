package com.ies.blossom.controllers;

import com.ies.blossom.entitys.HumMeasure;
import com.ies.blossom.entitys.HumSensor;
import com.ies.blossom.entitys.Parcel;
import com.ies.blossom.entitys.PhMeasure;
import com.ies.blossom.entitys.PhSensor;
import com.ies.blossom.entitys.Plant;
import com.ies.blossom.entitys.User;
import com.ies.blossom.repositorys.ParcelRepository;
import com.ies.blossom.repositorys.PlantRepository;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ParcelController {

    ParcelRepository parcelRepository;
    PlantRepository plantRepository;

    public ParcelController(ParcelRepository parcelRepository, PlantRepository plantRepository) {
        this.parcelRepository = parcelRepository;
        this.plantRepository = plantRepository;
    }

    // TODO Change the URL MAPPING TO parcel NAME DYNAMICALY //

    @GetMapping("/parcel")
    public String getIssues(Model model) throws ParseException {
    	
    	User user         = new User();
        Parcel parcel     = new Parcel();
        Set<Plant> plants = new HashSet<Plant>();
        makeData(user, parcel, plants);
        
        int count = 0;
        int sum = 0;
        for (HumSensor sensor : parcel.getHumSensors()) {
        	for (HumMeasure measure : sensor.getMeasures()) {
        		sum += measure.getValue();
			}
        	count += sensor.getMeasures().size();
		}
        double humidity;
        if (count == 0) {
        	humidity = 0;
        } else {
        	humidity = sum/count;
        }
        
        count = 0;
        sum = 0;
        for (PhSensor sensor : parcel.getPhSensors()) {
        	for (PhMeasure measure : sensor.getMeasures()) {
        		sum += measure.getValue();
			}
        	count += sensor.getMeasures().size();
		}
        double ph;
        if (count == 0) {
        	ph = 0;
        } else {
        	ph = sum/count;
        }
        
        boolean goodConditions = true;        
        Plant cropPlant = parcel.getPlant();
        boolean goodHum = cropPlant.getHumMin() <= humidity && humidity <= cropPlant.getHumMax();
        boolean goodPh = cropPlant.getPhMin() <= ph && ph <= cropPlant.getPhMax();
        if((!goodHum) || (!goodPh)) {
        	goodConditions = false;        	
        }
        
        List<Plant> listplants = new ArrayList<Plant>();
        
        for (Plant plant : plants) {
        	goodHum = cropPlant.getHumMin() <= humidity && humidity <= cropPlant.getHumMax();
        	goodPh = cropPlant.getPhMin() <= ph && ph <= cropPlant.getPhMax();
        	if(goodHum && goodPh) {
        		listplants.add(plant);
        	}
		}
        
        model.addAttribute("parcel", parcel);
        model.addAttribute("goodConditions", goodConditions);
        model.addAttribute("humidity", humidity);
        model.addAttribute("ph", ph);
        model.addAttribute("plants", listplants);
        
        
        
        return "parcel.html";
    }
    
    
    private static void makeData(User user, Parcel parcel, Set<Plant> plants) throws ParseException {
    	
    	user.setName("Jaime");
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    	
    	parcel.setLocation("Aveiro");
        parcel.setOwner(user);
        
        Plant plant01 = new Plant("planta in parcel", "parcel planta", 2.0, 1.0, 1.0, 1.8);
        parcel.setPlant(plant01);
        
        HumSensor humsensor = new HumSensor(parcel, new Date((sdf.parse("2019-01-01 00:00:00")).getTime()));
        PhSensor phsensor = new PhSensor(parcel, new Date((sdf.parse("2019-01-01 00:00:00")).getTime()));
        parcel.addHumSensor(humsensor);
        parcel.addPhSensor(phsensor);
        humsensor.addHumMeasure(new HumMeasure(humsensor, new Timestamp((sdf.parse("2020-06-01 00:00:00")).getTime()), 2.1));
        humsensor.addHumMeasure(new HumMeasure(humsensor, new Timestamp((sdf.parse("2020-06-03 00:00:00")).getTime()), 1.9));
        humsensor.addHumMeasure(new HumMeasure(humsensor, new Timestamp((sdf.parse("2020-06-05 00:00:00")).getTime()), 1.7));
        phsensor.addPhMeasure(new PhMeasure(phsensor, new Timestamp((sdf.parse("2020-06-01 00:00:00")).getTime()), 2.1));
        phsensor.addPhMeasure(new PhMeasure(phsensor, new Timestamp((sdf.parse("2020-06-03 00:00:00")).getTime()), 1.9));
        phsensor.addPhMeasure(new PhMeasure(phsensor, new Timestamp((sdf.parse("2020-06-05 00:00:00")).getTime()), 1.7));
        
        humsensor = new HumSensor(parcel, new Date((sdf.parse("2019-01-01 00:00:00")).getTime()));
        phsensor = new PhSensor(parcel, new Date((sdf.parse("2019-01-01 00:00:00")).getTime()));
        parcel.addHumSensor(humsensor);
        parcel.addPhSensor(phsensor);
        humsensor.addHumMeasure(new HumMeasure(humsensor, new Timestamp((sdf.parse("2020-06-01 00:00:00")).getTime()), 2.1));
        humsensor.addHumMeasure(new HumMeasure(humsensor, new Timestamp((sdf.parse("2020-06-03 00:00:00")).getTime()), 1.9));
        humsensor.addHumMeasure(new HumMeasure(humsensor, new Timestamp((sdf.parse("2020-06-05 00:00:00")).getTime()), 1.7));
        phsensor.addPhMeasure(new PhMeasure(phsensor, new Timestamp((sdf.parse("2020-06-01 00:00:00")).getTime()), 2.1));
        phsensor.addPhMeasure(new PhMeasure(phsensor, new Timestamp((sdf.parse("2020-06-03 00:00:00")).getTime()), 1.9));
        phsensor.addPhMeasure(new PhMeasure(phsensor, new Timestamp((sdf.parse("2020-06-05 00:00:00")).getTime()), 1.7));
        

        plants.add(plant01);
        plants.add(new Plant("planta02", "planta02", 2.0, 1.0, 1.0, 2.5));
        plants.add(new Plant("planta03", "planta03", 2.0, 1.0, 1.0, 2.0));
        plants.add(new Plant("plantatusCuatro", "planta04", 2.0, 1.0, 1.0, 1.5));
    	
    }
    
}