package com.ies.blossom.controllers;

import java.sql.Date;

import com.ies.blossom.entitys.Parcel;
import com.ies.blossom.entitys.PhMeasure;
import com.ies.blossom.entitys.PhSensor;
import com.ies.blossom.entitys.User;
import com.ies.blossom.repositorys.PhMeasureRepository;
import com.ies.blossom.repositorys.PhSensorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
public class PhSensorController {

    @Autowired
    private PhSensorRepository phSensorRepository;

    @Autowired
    private PhMeasureRepository phMeasureRepository;
    /*
    PhSensorRepository phSensorRepository;

    public PhSensorController(PhSensorRepository phSensorRepository) {
        this.phSensorRepository = phSensorRepository;
    }*/

    // TODO Change the URL MAPPING TO parcel NAME DYNAMICALY ex. phsensor.id //

    @GetMapping("/phSensor")
    public String getIssues(Model model) {
        List<PhSensor> sensors = this.phSensorRepository.findAll(); // Normalmente quando se chegasse a esta página já se
                                                            // saberia qual era o id do sensor em causa
                                                            // desta forma vamos buscar o 1o sensor da tabela TODO ir buscar um
                                                            // q faca sentido
        PhSensor sensor = sensors.get(0);
        model.addAttribute("phSensor", sensor);

        model.addAttribute("last_med", sensor.getMeasures().get(0));
        return "phSensor.html";
    }
    // humSensor/measures?sensorId=5
    @GetMapping("/phSensor/measures")
    public String showMeasures(Model model, @RequestParam(name="sensorId", required = true) Long sensorId) {
        List<PhMeasure> measures = this.phMeasureRepository.findBySensorId(sensorId);
        model.addAttribute("measures", measures);
        return "measures.html";
    }
}