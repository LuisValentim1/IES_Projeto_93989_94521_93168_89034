package com.ies.blossom.controllers;

import java.util.List;
import java.util.Optional;

import com.ies.blossom.entitys.User;
import com.ies.blossom.repositorys.AvaliationRepository;
import com.ies.blossom.repositorys.HumMeasureRepository;
import com.ies.blossom.repositorys.HumSensorRepository;
import com.ies.blossom.repositorys.ParcelRepository;
import com.ies.blossom.repositorys.PhMeasureRepository;
import com.ies.blossom.repositorys.PhSensorRepository;
import com.ies.blossom.repositorys.PlantRepository;
import com.ies.blossom.repositorys.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlantRepository plantRepository;

    @Autowired
    private ParcelRepository parcelRepository;

    @Autowired
    private PhSensorRepository phSensorRepository;

    @Autowired
    private PhMeasureRepository phMeasureRepository;

    @Autowired
    private HumSensorRepository humSensorRepository;

    @Autowired
    private HumMeasureRepository humMeasureRepository;

    @Autowired
    private AvaliationRepository avaliationRepository;

    // Métodos associados a users
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable(value = "id") Long userId) {
        return this.userRepository.findById(userId).get();
    }
}
