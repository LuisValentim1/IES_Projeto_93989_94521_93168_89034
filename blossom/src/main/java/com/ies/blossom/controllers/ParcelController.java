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
    	
    	// basic data generation. change it for db access
        User Jaime    = new User();
        Parcel parcel = new Parcel(Jaime, "Aveiro");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = new Date((sdf.parse("2019-01-01 00:00:00")).getTime());
        
        HumSensor humsensor = new HumSensor(parcel, date);
        
        HumMeasure measure01 = new HumMeasure(humsensor, 
        		new Timestamp((sdf.parse("2020-06-01 00:00:00")).getTime()), 2.1);
        HumMeasure measure02 = new HumMeasure(humsensor, 
				new Timestamp((sdf.parse("2020-06-03 00:00:00")).getTime()), 1.9);
        HumMeasure measure03 = new HumMeasure(humsensor, 
				new Timestamp((sdf.parse("2020-06-05 00:00:00")).getTime()), 1.7);
        
        PhSensor phsensor = new PhSensor(parcel, date);
        
        PhMeasure phmeasure01 = new PhMeasure(phsensor, 
        		new Timestamp((sdf.parse("2020-06-01 00:00:00")).getTime()), 0.9);
        PhMeasure phmeasure02 = new PhMeasure(phsensor, 
				new Timestamp((sdf.parse("2020-06-03 00:00:00")).getTime()), 1.5);
        PhMeasure phmeasure03 = new PhMeasure(phsensor, 
				new Timestamp((sdf.parse("2020-06-05 00:00:00")).getTime()), 2.1);
        
        
        
        Plant plant01 = new Plant("planta01", "planta01", 2.0, 1.0, 1.0, 1.8);//on parcel
        Plant plant02 = new Plant("planta02", "planta02", 2.0, 1.0, 1.0, 2.5);//on parcel
        Plant plant03 = new Plant("planta03", "planta03", 2.0, 1.0, 1.0, 2.0);//not in
        Plant plant04 = new Plant("planta04", "planta04", 2.0, 1.0, 1.0, 1.5);//not in
        
        List<Plant> in = new ArrayList<Plant>();
        in.add(plant01); in.add(plant02);
        List<Plant> out = new ArrayList<Plant>();
        out.add(plant03);out.add(plant04);       
        
        List<HumMeasure> hummeasures = new ArrayList<HumMeasure>();
        hummeasures.add(measure03);hummeasures.add(measure02);hummeasures.add(measure01);
        List<PhMeasure> phmeasures = new ArrayList<PhMeasure>();
        phmeasures.add(phmeasure01);phmeasures.add(phmeasure02);phmeasures.add(phmeasure02);
        // end of basic data generation
        
        double [] humvalues = new double[hummeasures.size()];
        for (int i = 0; i < hummeasures.size(); i++) {
			humvalues[i] = (hummeasures.get(i)).getValue();
		}
        double humvalue = Arrays.stream(humvalues).average().getAsDouble();
        
        double [] phvalues = new double[phmeasures.size()];
        for (int i = 0; i < phmeasures.size(); i++) {
        	phvalues[i] = (phmeasures.get(i)).getValue();
		}
        double phvalue = Arrays.stream(humvalues).average().getAsDouble();
        
        List<Plant> inParcelNotGood = new ArrayList<Plant>();
        List<Plant> inParcelGood = new ArrayList<Plant>();
        List<Plant> notInGood = new ArrayList<Plant>();
        
        boolean humgood, phgood;
        
        for (Plant plant : in) {
        	humgood = plant.getHumMin() <= humvalue && humvalue <= plant.getHumMax();
        	phgood = plant.getPhMin() <= phvalue && phvalue <= plant.getPhMax();
        	
			if(humgood && phgood) {
				inParcelGood.add(plant);
			} else {
				inParcelNotGood.add(plant);
			}
		}
        
        for (Plant plant : out) {
        	humgood = plant.getHumMin() <= humvalue && humvalue <= plant.getHumMax();
        	phgood = plant.getPhMin() <= phvalue && phvalue <= plant.getPhMax();
        	if(humgood && phgood) {
				notInGood.add(plant);
			}		
		}
        
        model.addAttribute("parcel", parcel);
        model.addAttribute("inParcelNotGood", inParcelNotGood);
        model.addAttribute("inParcelGood", inParcelGood);
        model.addAttribute("notInGood", notInGood);
        
        return "parcel.html";
    }
    
}