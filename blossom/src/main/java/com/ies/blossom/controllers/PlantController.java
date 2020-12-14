package com.ies.blossom.controllers;

import com.ies.blossom.entitys.Plant;
import com.ies.blossom.repositorys.PlantRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PlantController {

    @Autowired
    private PlantRepository plantRepository;
    /*
    PlantRepository plantRepository;

    public PlantController(PlantRepository plantRepository) {
        this.plantRepository = plantRepository;
    }*/

    //TODO Change the URL MAPPING TO PLANT NAME DYNAMICALY //

    @GetMapping("/plant/{id}")
    public String getIssues(Model model, @PathVariable(value = "id") Long plantId) {
        model.addAttribute("plant", this.plantRepository.getOne(plantId));
        return "plant.html";
    }
}