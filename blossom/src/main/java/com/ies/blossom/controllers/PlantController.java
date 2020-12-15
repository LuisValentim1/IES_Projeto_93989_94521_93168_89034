package com.ies.blossom.controllers;

import com.ies.blossom.model.PlantModel;
import com.ies.blossom.repositorys.PlantRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
    public String getPlant(Model model, @PathVariable(value = "id") Long plantId) {
        model.addAttribute("plant", this.plantRepository.getOne(plantId));
        return "plant.html";
    }

    @GetMapping("/plant/new")
    public String getForm(Model model){
        model.addAttribute("form", new PlantModel());
        return "forms/plantForm.html";
    }
}