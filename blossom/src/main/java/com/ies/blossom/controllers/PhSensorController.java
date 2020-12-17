package com.ies.blossom.controllers;

import java.sql.Date;

import com.ies.blossom.entitys.Parcel;
import com.ies.blossom.entitys.PhMeasure;
import com.ies.blossom.entitys.PhSensor;
import com.ies.blossom.repositorys.ParcelRepository;
import com.ies.blossom.repositorys.PhMeasureRepository;
import com.ies.blossom.repositorys.PhSensorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

@Controller
public class PhSensorController {

    @Autowired
    private PhSensorRepository phSensorRepository;

    @Autowired
    private PhMeasureRepository phMeasureRepository;

    @Autowired
    private ParcelRepository parcelRepository;
    /*
    PhSensorRepository phSensorRepository;

    public PhSensorController(PhSensorRepository phSensorRepository) {
        this.phSensorRepository = phSensorRepository;
    }*/

    // TODO Change the URL MAPPING TO parcel NAME DYNAMICALY ex. phsensor.id //

    @GetMapping("/phSensor/{id}")
    public String getIssues(Model model, @PathVariable(value = "id") Long sensorId) {
        // TODO colocar excecoes
        PhSensor sensor = this.phSensorRepository.getOne(sensorId);
        model.addAttribute("phSensor", sensor);

        if (!sensor.getMeasures().isEmpty())
            model.addAttribute("last_med", sensor.getMeasures().get(0)); // vai buscar o mais antigo TODO
        return "phSensor.html";
    }

    @GetMapping("/phSensor/measures")
    public String showMeasures(Model model, @RequestParam(name="sensorId", required = true) Long sensorId) {
        List<PhMeasure> measures = this.phMeasureRepository.findBySensorId(sensorId);
        model.addAttribute("measures", measures);
        return "measures.html";
    }

    // phsensor/new?parcelId=9
    @GetMapping("/phsensor/new")
    public String createSensor(@RequestParam(name = "parcelId", required = true) Long parcelId,
                                HttpServletRequest request) {
        // como para já n há info associada aos sensores
        // podemos fazer logo a criacao do objeto na base de dados
        // pq o utilizador nao teria que preencher nenhum campo de formulário
        PhSensor sensor2save = new PhSensor();
        
        Parcel parcel = this.parcelRepository.getOne(parcelId);
        parcel.getPhSensors().add(sensor2save);
        this.parcelRepository.save(parcel);

        sensor2save.setParcel(parcel);
        sensor2save.setAssocDate(new Date(System.currentTimeMillis()));
        this.phSensorRepository.save(sensor2save);
        return "redirect:" + request.getHeader("Referer");
    }
}