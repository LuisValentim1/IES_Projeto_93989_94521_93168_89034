package com.ies.blossom.controllers;

import java.sql.Date;

import com.ies.blossom.entitys.Parcel;
import com.ies.blossom.entitys.PhSensor;
import com.ies.blossom.entitys.User;
import com.ies.blossom.repositorys.PhSensorRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PhSensorController {

    PhSensorRepository phSensorRepository;

    public PhSensorController(PhSensorRepository phSensorRepository) {
        this.phSensorRepository = phSensorRepository;
    }

    // TODO Change the URL MAPPING TO parcel NAME DYNAMICALY ex. phsensor.id //

    @GetMapping("/phSensor")
    public String getIssues(Model model) {

        // Temporary Values
        Parcel tempParcel = new Parcel();
        java.sql.Date tempDate = new java.sql.Date(0);
        model.addAttribute("phSensor", new PhSensor(tempParcel, tempDate));
        return "phSensor.html";
    }
}