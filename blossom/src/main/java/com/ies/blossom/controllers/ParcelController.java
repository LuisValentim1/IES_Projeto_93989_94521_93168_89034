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
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Controller
public class ParcelController {

    @Autowired
    private ParcelRepository parcelRepository;
    @Autowired
    private PlantRepository plantRepository;
    /*
    ParcelRepository parcelRepository;
    PlantRepository plantRepository;

    public ParcelController(ParcelRepository parcelRepository, PlantRepository plantRepository) {
        this.parcelRepository = parcelRepository;

    }*/

    // TODO Change the URL MAPPING TO parcel NAME DYNAMICALY //

    @GetMapping("/parcel/{id}")
    public String getIssues(Model model, @PathVariable(value="id") Long parcelId) {
        Parcel parcel = this.parcelRepository.getOne(parcelId);

        // ir buscar todos as ultimas medidas relativas aos sensores de ph
        Map<PhSensor, PhMeasure> retPh = new HashMap<PhSensor, PhMeasure>();
        for (PhSensor sensor : parcel.getPhSensors()) {
            retPh.put(sensor, sensor.getMeasures().get(0)); // em vez de ir buscar o primeiro pode ir buscar o ultimo
        }

        // ir buscar todos as ultimas medidas relativas aos sensores de hum
        Map<HumSensor, HumMeasure> retHum = new HashMap<HumSensor, HumMeasure>();
        for (HumSensor sensor : parcel.getHumSensors()) {
            retHum.put(sensor, sensor.getMeasures().get(0)); // em vez de ir buscar o primeiro pode ir buscar o ultimo
        }

        model.addAttribute("parcel", parcel);
        model.addAttribute("phSensorsLastMeasures", retPh);
        model.addAttribute("humSensorsLastMeasures", retHum);
        return "parcel.html";
    }
    
    
    private static void makeData(User user, Parcel parcel, Collection<Plant> plants) throws ParseException {
    	
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