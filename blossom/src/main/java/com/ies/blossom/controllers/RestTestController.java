package com.ies.blossom.controllers;

import com.ies.blossom.dto.MeasureDto;
import com.ies.blossom.dto.ParcelDto;
import com.ies.blossom.dto.SensorDto;
import com.ies.blossom.entitys.*;
import com.ies.blossom.exceptions.ResourceNotFoundException;
import com.ies.blossom.repositorys.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Timestamp;
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
        Date entryDate = new Date(System.currentTimeMillis());
        Timestamp lastJoined = new Timestamp(System.currentTimeMillis());

//        String password = passwordEncoder.encode(user.getPassword());

        User user2save = new User(user.getName(), user.getEmail(), entryDate,
                user.getPassword(), user.getPhoneNumber(), lastJoined, true);
        return this.userRepository.save(user2save);
    }

    @GetMapping("/parcels")
    public List<Parcel> getAllParcels() {
        return this.parcelRepository.findAll();
    }

    @PostMapping("/parcels")
    public Parcel createParcel(@RequestBody ParcelDto parcelDto) {
        User user = this.userRepository.getOne(parcelDto.getOwner());
        Parcel parcel = new Parcel(user, parcelDto.getLocation());
        user.getParcels().add(parcel);
        return this.parcelRepository.save(parcel);
    }

    @GetMapping("/phsensors")
    public List<PhSensor> getAllPhSensors() {
        return this.phSensorRepository.findAll();
    }

    @PostMapping("/phsensors")
    public PhSensor createPhSensor(@RequestBody SensorDto sensorDto) {
        Parcel parcel = this.parcelRepository.getOne(sensorDto.getParcelId());
        PhSensor sensor = new PhSensor(parcel, new Date(System.currentTimeMillis()));
        parcel.getPhSensors().add(sensor);
        return this.phSensorRepository.save(sensor);
    }

    @PostMapping("/phmeasures")
    public PhMeasure createPhMeasure(@RequestBody MeasureDto measureDto) {
        PhSensor sensor = this.phSensorRepository.getOne(measureDto.getSensorId());
        PhMeasure measure = new PhMeasure(sensor, new Timestamp(System.currentTimeMillis()), measureDto.getValue());
        sensor.getMeasures().add(measure);
        return this.phMeasureRepository.save(measure);
    }

    @GetMapping("/humsensors")
    public List<HumSensor> getAllHumSensors() {
        return this.humSensorRepository.findAll();
    }

    @PostMapping("/humsensors")
    public HumSensor createHumSensor(@RequestBody SensorDto sensorDto) {
        Parcel parcel = this.parcelRepository.getOne(sensorDto.getParcelId());
        HumSensor sensor = new HumSensor(parcel, new Date(System.currentTimeMillis()));
        parcel.getHumSensors().add(sensor);
        return this.humSensorRepository.save(sensor);
    }

    @PostMapping("/hummeasures")
    public HumMeasure createHumMeasure(@RequestBody MeasureDto measureDto) {
        HumSensor sensor = this.humSensorRepository.getOne(measureDto.getSensorId());
        HumMeasure measure = new HumMeasure(sensor, new Timestamp(System.currentTimeMillis()), measureDto.getValue());
        sensor.getMeasures().add(measure);
        return this.humMeasureRepository.save(measure);
    }

    @GetMapping("/plants")
    public List<Plant> getAllPlants() {
        return this.plantRepository.findAll();
    }

    @GetMapping("/plants/{id}")
    public Plant getPlant(@PathVariable (value = "id") Long plantId) {
        return this.plantRepository.findById(plantId).orElseThrow(
                () -> new ResourceNotFoundException("Plant not found with id: " + plantId)
        );
    }

    @PostMapping("/plants")
    public Plant createPlant(@RequestBody Plant plant) {
        return this.plantRepository.save(plant);
    }
}
