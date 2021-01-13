package com.ies.blossom.controllers;

import com.ies.blossom.entitys.Plant;
import com.ies.blossom.model.PlantModel;
import com.ies.blossom.repositorys.PlantRepository;
import com.ies.blossom.security.CustomUserDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PlantController {

    @Autowired
    private PlantRepository plantRepository;

    @GetMapping("/plant/{id}")
    public String getPlant(Model model, @PathVariable(value = "id") Long plantId) {
        model.addAttribute("plant", this.plantRepository.getOne(plantId));
        return "plant.html";
    }

    @GetMapping("/plant/new")
    public String getForm(Model model, Authentication auth){
        CustomUserDetails userLogged = (CustomUserDetails) auth.getPrincipal();

        if (!userLogged.isAdmin()) {
            model.addAttribute("httpError", "401");
            model.addAttribute("errorMessage", userLogged.getName() + ", you are not allowed to see this content.");
            return "messageError.html";
        }

        model.addAttribute("form", new PlantModel());
        return "forms/plantForm.html";
    }

    @PostMapping("/plant/new")
    public String createPlant(Model model, @ModelAttribute PlantModel plant) {

        // TODO permitir adicionar plantas apenas a users com role admin
        
        Plant plant2save = new Plant();
        plant2save.setCientificName(plant.getCientificName());
        plant2save.setEnglishName(plant.getEnglishName());
        plant2save.setPhMax(plant.getPhMax());
        plant2save.setPhMin(plant.getPhMin());
        plant2save.setHumMax(plant.getHumMax());
        plant2save.setHumMin(plant.getHumMin());

        this.plantRepository.save(plant2save);

        model.addAttribute("created", true);
        return "forms/plantForm.html";
    }
}