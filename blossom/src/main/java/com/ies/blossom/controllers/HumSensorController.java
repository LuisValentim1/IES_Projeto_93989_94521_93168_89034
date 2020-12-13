package com.ies.blossom.controllers;

import java.sql.Date;
import java.util.List;

import com.ies.blossom.entitys.Parcel;
import com.ies.blossom.entitys.HumMeasure;
import com.ies.blossom.entitys.HumSensor;
import com.ies.blossom.entitys.User;
import com.ies.blossom.model.SensorModel;
import com.ies.blossom.repositorys.HumMeasureRepository;
import com.ies.blossom.repositorys.HumSensorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HumSensorController {

    @Autowired
    private HumSensorRepository humSensorRepository;

    @Autowired
    private HumMeasureRepository humMeasureRepository;
    /* comento pq n sei qual era a intencao ao colocar o repo no construtor
    HumSensorRepository humSensorRepository;

    public HumSensorController(HumSensorRepository humSensorRepository) {
        this.humSensorRepository = humSensorRepository;
    }*/

    // TODO Change the URL MAPPING TO parcel NAME DYNAMICALY ex. phsensor.id //

    @GetMapping("/humSensor")
    public String getIssues(Model model) {
        List<HumSensor> sensors = this.humSensorRepository.findAll(); // Normalmente quando se chegasse a esta página já se
                                                            // saberia qual era o id do sensor em causa
                                                            // desta forma vamos buscar o 1o sensor da tabela TODO ir buscar 1
                                                            // q faca sentido
        HumSensor sensor = sensors.get(0);
        model.addAttribute("humSensor", sensor);

        model.addAttribute("last_med", sensor.getMeasures().get(0));
        return "humSensor.html";
    }
    // humSensor/measures?sensorId=5
    @GetMapping("/humSensor/measures")
    public String showMeasures(Model model, @RequestParam(name="sensorId", required = true) Long sensorId) {
        List<HumMeasure> measures = this.humMeasureRepository.findBySensorId(sensorId);
        model.addAttribute("measures", measures);
        return "measures.html";
    }
}