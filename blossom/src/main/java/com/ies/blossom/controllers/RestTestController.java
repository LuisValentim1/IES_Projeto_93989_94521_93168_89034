package com.ies.blossom.controllers;

import com.ies.blossom.entitys.*;
import com.ies.blossom.repositorys.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/test")
public class RestTestController {

    @Autowired
    private HumMeasureRepository humMeasureRepository;
    @Autowired
    private HumSensorRepository humSensorRepository;
    @Autowired
    private ParcelRepository parcelRepository;
    @Autowired
    private PhMeasureRepository phMeasureRepository;
    @Autowired
    private PhSensorRepository phSensorRepository;
    @Autowired
    private PlantRepository plantRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        return new User();
    }

    @PostMapping("/parcels")
    public Parcel createParcel(@RequestBody Parcel parcel) {
        return new Parcel();
    }

    @PostMapping("/phsensors")
    public PhSensor createPhSensor(@RequestBody PhSensor phSensor) {
        return new PhSensor();
    }

    @PostMapping("/humsensors")
    public HumSensor createHumSensor(@RequestBody HumSensor humSensor) {
        return new HumSensor();
    }

    @PostMapping("/plants")
    public Plant createPlant(@RequestBody Plant plant) {
        return new Plant();
    }
}
