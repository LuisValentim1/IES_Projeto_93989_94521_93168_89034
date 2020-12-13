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
    	
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    	
    	User Jaime    = new User();
        Parcel parcel = new Parcel(Jaime, "Aveiro");
        
        HumSensor humsensor = new HumSensor(parcel, new Date((sdf.parse("2019-01-01 00:00:00")).getTime()));
        HumMeasure measure01 = new HumMeasure(humsensor, 
        		new Timestamp((sdf.parse("2020-06-01 00:00:00")).getTime()), 2.1);
        HumMeasure measure02 = new HumMeasure(humsensor, 
				new Timestamp((sdf.parse("2020-06-03 00:00:00")).getTime()), 1.9);
        HumMeasure measure03 = new HumMeasure(humsensor, 
				new Timestamp((sdf.parse("2020-06-05 00:00:00")).getTime()), 1.7);
        
        List<HumMeasure> hummeasures = new ArrayList<HumMeasure>();
        hummeasures.add(measure03);hummeasures.add(measure02);hummeasures.add(measure01);
        
    	Plant plant01 = new Plant("plantatusUno", "planta01", 2.0, 1.0, 1.0, 1.8);
        Plant plant02 = new Plant("planta02", "planta02", 2.0, 1.0, 1.0, 2.5);
        Plant plant03 = new Plant("planta03", "planta03", 2.0, 1.0, 1.0, 2.0);
        Plant plant04 = new Plant("plantatusCuatro", "planta04", 2.0, 1.0, 1.0, 1.5);
        List<Plant> plants = new ArrayList<Plant>();
        plants.add(plant01);plants.add(plant02);plants.add(plant03);plants.add(plant04);
        
        //plants
        //hummeasures
        
        double [] humvalues = new double[hummeasures.size()];
        for (int i = 0; i < hummeasures.size(); i++) {
			humvalues[i] = (hummeasures.get(i)).getValue();
		}
        double humvalue = Arrays.stream(humvalues).average().getAsDouble();
        List<Plant> goodhumplants = new ArrayList<Plant>();
        List<Plant> badhumplants = new ArrayList<Plant>();
        
        for (Plant plant : plants) {
        	boolean inrange = plant.getHumMin() <= humvalue && humvalue <= plant.getHumMax();
        	if (inrange) {
        		goodhumplants.add(plant);
        	} else {
        		badhumplants.add(plant);
        	}
        }
        	
        List<Plant> badhum_dry = new ArrayList<Plant>();
        List<Plant> badhum_wet = new ArrayList<Plant>();
        	
        for (Plant plant : badhumplants) {
        	
        	if (plant.getHumMin() > humvalue) {
        		badhum_dry.add(plant);
        	} else {
        		badhum_wet.add(plant);
        	}
        	
		}
        
        model.addAttribute("parcel", parcel);
        model.addAttribute("goodlants", goodhumplants);
        model.addAttribute("wetplants", badhum_wet);
        model.addAttribute("dryplants", badhum_dry);
        
        return "parcel.html";
    }
    
}