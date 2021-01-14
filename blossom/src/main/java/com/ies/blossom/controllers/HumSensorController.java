package com.ies.blossom.controllers;

import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ies.blossom.entitys.Parcel;
import com.ies.blossom.entitys.HumMeasure;
import com.ies.blossom.entitys.HumSensor;
import com.ies.blossom.repositorys.HumMeasureRepository;
import com.ies.blossom.repositorys.HumSensorRepository;
import com.ies.blossom.repositorys.ParcelRepository;
import com.ies.blossom.security.CustomUserDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HumSensorController {

    @Autowired
    private HumSensorRepository humSensorRepository;

    @Autowired
    private HumMeasureRepository humMeasureRepository;

    @Autowired
    private ParcelRepository parcelRepository;

    @GetMapping("/humSensor/{id}")
    public String getIssues(Model model, @PathVariable(value = "id") Long sensorId, Authentication auth) {
        CustomUserDetails userLogged = (CustomUserDetails) auth.getPrincipal();

        // TODO colocar excecoes
        HumSensor sensor = this.humSensorRepository.getOne(sensorId);
        
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

        model.addAttribute("humSensor", sensor);

        if (!sensor.getMeasures().isEmpty())
            model.addAttribute("last_med", sensor.getMeasures().get(sensor.getMeasures().size()-1));
        return "humSensor.html";
    }
    // humSensor/measures?sensorId=5
    @GetMapping("/humSensor/measures")
    public String showMeasures(Model model, @RequestParam(name="sensorId", required = true) Long sensorId, Authentication auth) {
        CustomUserDetails userLogged = (CustomUserDetails) auth.getPrincipal();

        List<HumMeasure> measures = this.humMeasureRepository.findBySensorId(sensorId);

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

        model.addAttribute("data", this.getData(measures));
        model.addAttribute("type", "Humidity");
        return "measures.html";
    }

    private String getData(List<HumMeasure> measures) {
        String ret = "";
        
        for (int i = 0; ; i++) {
		    		
            ret += String.valueOf(i) + " " + measures.get(measures.size()-1 - i).getValue().toString();

            if (i > 49 || i+1 >= measures.size())
                break;
            
            ret += "DELIMITER";
        }

        return ret;
    }

    // humsensor/new?parcelId=9
    @GetMapping("/humsensor/new")
    public String createSensor(@RequestParam(name = "parcelId", required = true) Long parcelId,
                                HttpServletRequest request, Authentication auth, Model model) {
        CustomUserDetails userLogged = (CustomUserDetails) auth.getPrincipal();
        // como para já n há info associada aos sensores
        // podemos fazer logo a criacao do objeto na base de dados
        // pq o utilizador nao teria que preencher nenhum campo de formulário
        HumSensor sensor2save = new HumSensor();
        
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

        parcel.getHumSensors().add(sensor2save);
        this.parcelRepository.save(parcel);

        sensor2save.setParcel(parcel);
        sensor2save.setAssocDate(new Date(System.currentTimeMillis()));
        this.humSensorRepository.save(sensor2save);
        return "redirect:" + request.getHeader("Referer");
    }
}
