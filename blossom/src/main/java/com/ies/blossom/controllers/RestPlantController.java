package com.ies.blossom.controllers;

import com.ies.blossom.entitys.Plant;
import com.ies.blossom.exceptions.ResourceNotFoundException;
import com.ies.blossom.repositorys.PlantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/plants")
public class RestPlantController {
    @Autowired
    private PlantRepository plantRepository;

    @GetMapping
    public List<Plant> getAllPlants() {
        List<Plant> ret = new ArrayList<Plant>();
        this.plantRepository.findAll().forEach(ret::add);
        return ret;
    }

    @GetMapping("/{id}")
    public Plant getPlant(@PathVariable (value = "id") Long plantId) {
        return this.plantRepository.findById(plantId).orElseThrow(
                () -> new ResourceNotFoundException("Plant not found with id: " + plantId)
        );
    }

    @PostMapping
    public Plant createPlant(@RequestBody Plant plant) {
        return this.plantRepository.save(plant);
    }
}
