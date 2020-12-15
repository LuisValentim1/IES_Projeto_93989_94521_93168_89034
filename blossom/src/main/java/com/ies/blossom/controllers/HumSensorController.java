package com.ies.blossom.controllers;

import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ies.blossom.entitys.Parcel;
import com.ies.blossom.entitys.HumMeasure;
import com.ies.blossom.entitys.HumSensor;
import com.ies.blossom.entitys.User;
import com.ies.blossom.model.SensorModel;
import com.ies.blossom.repositorys.HumMeasureRepository;
import com.ies.blossom.repositorys.HumSensorRepository;
import com.ies.blossom.repositorys.ParcelRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HumSensorController {

    @Autowired
    private HumSensorRepository humSensorRepository;

    @Autowired
    private HumMeasureRepository humMeasureRepository;

    @Autowired
    private ParcelRepository parcelRepository;
    /* comento pq n sei qual era a intencao ao colocar o repo no construtor
    HumSensorRepository humSensorRepository;

    public HumSensorController(HumSensorRepository humSensorRepository) {
        this.humSensorRepository = humSensorRepository;
    }*/

    // TODO Change the URL MAPPING TO parcel NAME DYNAMICALY ex. phsensor.id //

    @GetMapping("/humSensor/{id}")
    public String getIssues(Model model, @PathVariable(value = "id") Long sensorId) {
        // TODO colocar excecoes
        HumSensor sensor = this.humSensorRepository.getOne(sensorId);
        model.addAttribute("humSensor", sensor);

        model.addAttribute("last_med", sensor.getMeasures().get(0)); // vai buscar o mais antigo TODO
        return "humSensor.html";
    }
    // humSensor/measures?sensorId=5
    @GetMapping("/humSensor/measures")
    public String showMeasures(Model model, @RequestParam(name="sensorId", required = true) Long sensorId) {
        List<HumMeasure> measures = this.humMeasureRepository.findBySensorId(sensorId);
        model.addAttribute("measures", measures);
        return "measures.html";
    }

    // humsensor/new?parcelId=9
    @GetMapping("/humsensor/new")
    public String createSensor(@RequestParam(name = "parcelId", required = true) Long parcelId,
                                HttpServletRequest request) {
        // como para já n há info associada aos sensores
        // podemos fazer logo a criacao do objeto na base de dados
        // pq o utilizador nao teria que preencher nenhum campo de formulário
        HumSensor sensor2save = new HumSensor();
        
        Parcel parcel = this.parcelRepository.getOne(parcelId);
        parcel.getHumSensors().add(sensor2save);
        this.parcelRepository.save(parcel);

        sensor2save.setParcel(parcel);
        sensor2save.setAssocDate(new Date(System.currentTimeMillis()));
        this.humSensorRepository.save(sensor2save);
        return "redirect:" + request.getHeader("Referer");
    }
}