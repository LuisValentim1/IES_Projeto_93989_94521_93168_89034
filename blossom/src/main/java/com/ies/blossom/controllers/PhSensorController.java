package com.ies.blossom.controllers;

import java.sql.Date;

import com.ies.blossom.entitys.Parcel;
import com.ies.blossom.entitys.PhMeasure;
import com.ies.blossom.entitys.PhSensor;
import com.ies.blossom.repositorys.ParcelRepository;
import com.ies.blossom.repositorys.PhMeasureRepository;
import com.ies.blossom.repositorys.PhSensorRepository;
import com.ies.blossom.security.CustomUserDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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

    @GetMapping("/phSensor/{id}")
    public String getIssues(Model model, @PathVariable(value = "id") Long sensorId, Authentication auth) {
        CustomUserDetails userLogged = (CustomUserDetails) auth.getPrincipal();

        // TODO colocar excecoes
        PhSensor sensor = this.phSensorRepository.getOne(sensorId);

        if (sensor == null) {
            model.addAttribute("httpError", "404");
            model.addAttribute("errorMessage", "Sensor doesn't exist.");
            return "messageError.html";
        }

        if (!sensor.getParcel().getOwner().getUserId().equals(userLogged.getId())) {
            model.addAttribute("httpError", "401");
            model.addAttribute("errorMessage", userLogged.getName() + ", you are not allowed to see this content.");
            return "messageError.html";
        }

        model.addAttribute("phSensor", sensor);

        if (!sensor.getMeasures().isEmpty())
            model.addAttribute("last_med", sensor.getMeasures().get(sensor.getMeasures().size()-1));
        return "phSensor.html";
    }

    @GetMapping("/phSensor/measures")
    public String showMeasures(Model model, @RequestParam(name="sensorId", required = true) Long sensorId, Authentication auth) {
        CustomUserDetails userLogged = (CustomUserDetails) auth.getPrincipal();
        List<PhMeasure> measures = this.phMeasureRepository.findBySensorId(sensorId);

        if (measures == null) {
            model.addAttribute("httpError", "404");
            model.addAttribute("errorMessage", "No measures to display.");
            return "messageError.html";
        }

        if (!measures.get(0).getSensor().getParcel().getOwner().getUserId().equals(userLogged.getId())) {
            model.addAttribute("httpError", "401");
            model.addAttribute("errorMessage", userLogged.getName() + ", you are not allowed to see this content.");
            return "messageError.html";
        }

        model.addAttribute("measures", measures);
        return "measures.html";
    }

    // phsensor/new?parcelId=9
    @GetMapping("/phsensor/new")
    public String createSensor(@RequestParam(name = "parcelId", required = true) Long parcelId,
                                HttpServletRequest request, Authentication auth, Model model) {
        CustomUserDetails userLogged = (CustomUserDetails) auth.getPrincipal();
        // como para já n há info associada aos sensores
        // podemos fazer logo a criacao do objeto na base de dados
        // pq o utilizador nao teria que preencher nenhum campo de formulário
        PhSensor sensor2save = new PhSensor();
        
        Parcel parcel = this.parcelRepository.getOne(parcelId);

        if (parcel == null) {
            model.addAttribute("httpError", "404");
            model.addAttribute("errorMessage", "Parcel doesn't exist.");
            return "messageError.html";
        }

        if (!parcel.getOwner().getUserId().equals(userLogged.getId())) {
            model.addAttribute("httpError", "401");
            model.addAttribute("errorMessage", userLogged.getName() + ", you are not allowed to alter this content.");
            return "messageError.html";
        }
        
        parcel.getPhSensors().add(sensor2save);
        this.parcelRepository.save(parcel);

        sensor2save.setParcel(parcel);
        sensor2save.setAssocDate(new Date(System.currentTimeMillis()));
        this.phSensorRepository.save(sensor2save);
        return "redirect:" + request.getHeader("Referer");
    }
}