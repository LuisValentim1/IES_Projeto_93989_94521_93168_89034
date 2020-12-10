package com.ies.blossom.controllers;

import com.ies.blossom.entitys.Plant;
import com.ies.blossom.repositorys.PlantRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PlantController {

    PlantRepository plantRepository;

    public PlantController(PlantRepository plantRepository) {
        this.plantRepository = plantRepository;
    }

    // TODO Change the URL MAPPING TO PLANT NAME DYNAMICALY //

    @GetMapping("/plant")
    public String getIssues(Model model) {
        model.addAttribute("plant", new Plant("RobertoCience", "Roberto", 2.0, 3.0, 4.0, 5.1));
        return "plant.html";
    }
}