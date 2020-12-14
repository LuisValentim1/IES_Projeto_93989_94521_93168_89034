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

    public ParcelController(ParcelRepository parcelRepository) {
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
}