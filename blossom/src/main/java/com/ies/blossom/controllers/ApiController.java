package com.ies.blossom.controllers;

import java.sql.Timestamp;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.ies.blossom.dto.AvaliationDto;
import com.ies.blossom.dto.MeasureDto;
import com.ies.blossom.dto.ParcelDto;
import com.ies.blossom.dto.PlantDto;
import com.ies.blossom.dto.SensorDto;
import com.ies.blossom.dto.UserDto;
import com.ies.blossom.encoders.PasswordEncoder;
import com.ies.blossom.entitys.Avaliation;
import com.ies.blossom.entitys.HumMeasure;
import com.ies.blossom.entitys.HumSensor;
import com.ies.blossom.entitys.Parcel;
import com.ies.blossom.entitys.PhMeasure;
import com.ies.blossom.entitys.PhSensor;
import com.ies.blossom.entitys.Plant;
import com.ies.blossom.entitys.User;
import com.ies.blossom.exceptions.ResourceNotFoundException;
import com.ies.blossom.exceptions.ResourceNotOwnedException;
import com.ies.blossom.repositorys.AvaliationRepository;
import com.ies.blossom.repositorys.HumMeasureRepository;
import com.ies.blossom.repositorys.HumSensorRepository;
import com.ies.blossom.repositorys.ParcelRepository;
import com.ies.blossom.repositorys.PhMeasureRepository;
import com.ies.blossom.repositorys.PhSensorRepository;
import com.ies.blossom.repositorys.PlantRepository;
import com.ies.blossom.repositorys.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    // Métodos associados a users [get post update delete]
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable(value = "id") Long userId) {
        User user = this.userRepository.findById(userId).orElseThrow(
            () -> new ResourceNotFoundException("User not found with id: " + userId)
        );
        user.setLastJoined(new Timestamp(System.currentTimeMillis()));
        return this.userRepository.save(user);
    }

    @PostMapping("/users")
    public User createUser(@RequestBody UserDto user) {
        BCryptPasswordEncoder passEncoder = new BCryptPasswordEncoder();
        Date entryDate = new Date(System.currentTimeMillis());
        Timestamp lastJoined = new Timestamp(System.currentTimeMillis());

        String password = passEncoder.encode(user.getPassword());

        User user2save = new User(
            user.getName(),
            user.getEmail(),
            entryDate,
            password,
            user.getPhoneNumber(),
            lastJoined,
            true
        );
        return this.userRepository.save(user2save);
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody UserDto user) {
        BCryptPasswordEncoder passEncoder = new BCryptPasswordEncoder();

        User user2save = this.userRepository.findById(user.getId()).orElseThrow(
            () -> new ResourceNotFoundException("User not found with id: " + user.getId())
        );

        user2save.setName(user.getName());
        user2save.setPassword(passEncoder.encode(user.getPassword()));
        user2save.setPhoneNumber(user.getPhoneNumber());
        return this.userRepository.save(user2save);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable(value = "id") Long userId) {
        User user = this.userRepository.findById(userId).orElseThrow(
            () -> new ResourceNotFoundException("User not found with id: " + userId)
        );

        this.userRepository.delete(user);
    }

    // Métodos para plantas
    @GetMapping("/plants")
    public List<Plant> getAllPlants() {
        return this.plantRepository.findAll();
    }

    @GetMapping("/plants/{id}")
    public Plant getPlant(@PathVariable(value = "id") Long plantId) {
        return this.plantRepository.findById(plantId).orElseThrow(
            () -> new ResourceNotFoundException("Plant not found with id: " + plantId)
        );
    }

    @PostMapping("/plants")
    public Plant createPlant(@RequestBody PlantDto plant) {
        Plant plant2save = new Plant(
            plant.getCientificName(),
            plant.getEnglishName(),
            plant.getPhMax(),
            plant.getPhMin(),
            plant.getHumMin(),
            plant.getHumMax()
        );
        return this.plantRepository.save(plant2save);
    }

    @PutMapping("/plants")
    public Plant updatePlant(@RequestBody PlantDto plant) {
        Plant plant2save = this.plantRepository.findById(plant.getId()).orElseThrow(
            () -> new ResourceNotFoundException("Plant not found with id: " + plant.getId())
        );
        
        plant2save.setCientificName(plant.getCientificName());
        plant2save.setEnglishName(plant.getEnglishName());
        plant2save.setPhMax(plant.getPhMax());
        plant2save.setPhMin(plant.getPhMin());
        plant2save.setHumMax(plant.getHumMax());
        plant2save.setHumMin(plant.getHumMin());

        return this.plantRepository.save(plant2save);
    }

    @DeleteMapping("/plants/{id}")
    public void deletePlant(@PathVariable(value = "id") Long plantId) {
        Plant plant = this.plantRepository.findById(plantId).orElseThrow(
            () -> new ResourceNotFoundException("Plant not found with id: " + plantId)
        );
        this.plantRepository.delete(plant);
    }

    // Métodos para parcelas
    @GetMapping("/parcels")
    public List<Parcel> getAllParcels() {
        return this.parcelRepository.findAll();
    }

    @GetMapping("/parcels/{id}")
    public Parcel getParcel(@PathVariable(value = "id") Long parcelId) {
        return this.parcelRepository.findById(parcelId).orElseThrow(
            () -> new ResourceNotFoundException("Parcel not found with id: " + parcelId)
        );
    }

    @PostMapping("/parcels")
    public Parcel createParcel(@RequestBody ParcelDto parcel) {
        User user = this.userRepository.findById(parcel.getOwner()).orElseThrow(
            () -> new ResourceNotFoundException("User not found with id: " + parcel.getOwner())
        );

        Parcel parcel2save = new Parcel();
        
        Plant plant = null;
        if (parcel.getPlantId() != null) {
            plant = this.plantRepository.findById(parcel.getPlantId()).orElseThrow(
                () -> new ResourceNotFoundException("Plant not found with id: " + parcel.getPlantId())
            );

            parcel2save.setPlant(plant);
            plant.getParcels().add(parcel2save);
            this.plantRepository.save(plant);
        }
        parcel2save.setOwner(user);
        user.getParcels().add(parcel2save);
        this.userRepository.save(user);

        parcel2save.setLocation(parcel.getLocation());

        return this.parcelRepository.save(parcel2save);
    }

    @PutMapping("/parcels")
    public Parcel updateParcel(@RequestBody ParcelDto parcel) {
        Parcel parcel2save = this.parcelRepository.findById(parcel.getId()).orElseThrow(
            () -> new ResourceNotFoundException("Parcel not found with id: " + parcel.getId())
        );

        if (parcel.getPlantId() != null) {
            if (parcel.getPlantId() != -1L) {
                Plant plant = this.plantRepository.findById(parcel.getPlantId()).orElseThrow(
                    () -> new ResourceNotFoundException("Plant not found with id: " + parcel.getPlantId())
                );

                if (parcel2save.getPlant() != null) {
                    Plant oldPlant = this.plantRepository.findById(parcel2save.getPlant().getPlantId()).get();
                    oldPlant.getParcels().remove(parcel2save);
                    this.plantRepository.save(oldPlant);
                }

                parcel2save.setPlant(plant);
                plant.getParcels().add(parcel2save);
                this.plantRepository.save(plant);
                
            } else {
                // colocar a parcela sem planta caso venha plantId == -1
                if (parcel2save.getPlant() != null) {
                    Plant oldPlant = this.plantRepository.findById(parcel2save.getPlant().getPlantId()).get();
                    oldPlant.getParcels().remove(parcel2save);
                    this.plantRepository.save(oldPlant);
                }
                
                parcel2save.setPlant(null);
            }
            
        }

        parcel2save.setLocation(parcel.getLocation());

        return this.parcelRepository.save(parcel2save);
    }

    @DeleteMapping("/parcels/{id}")
    public void deleteParcel(@PathVariable(value = "id") Long parcelId) {
        Parcel parcel = this.parcelRepository.findById(parcelId).orElseThrow(
            () -> new ResourceNotFoundException("Parcel not found with id: " + parcelId)
        );
        this.parcelRepository.delete(parcel);
    }

    // Métodos para sensores de ph
    @GetMapping("/phsensors")
    public List<PhSensor> getAllPhSensors() {
        return this.phSensorRepository.findAll();
    }

    @GetMapping("/phsensors/{id}")
    public PhSensor getPhSensor(@PathVariable(value = "id") Long sensorId) {
        return this.phSensorRepository.findById(sensorId).orElseThrow(
            () -> new ResourceNotFoundException("Ph sensor not found with id: " + sensorId)
        );
    }

    @GetMapping("/phsensors/parcel/{id}")
    public Set<PhSensor> getPhSensorsByParcel(@PathVariable(value = "id") Long parcelId) {
        Parcel parcel = this.parcelRepository.findById(parcelId).orElseThrow(
            () -> new ResourceNotFoundException("Parcel not found with id: " + parcelId)
        );
        return parcel.getPhSensors();
    }

    @GetMapping("/phsensors/owner/{id}")
    public List<PhSensor> getPhSensorByOwner(@PathVariable(value = "id") Long userId) {
        User user = this.userRepository.findById(userId).orElseThrow(
            () -> new ResourceNotFoundException("User not found with id: " + userId)
        );
        List<PhSensor> ret = new ArrayList<PhSensor>();

        user.getParcels().forEach(
            parcel -> {
                parcel.getPhSensors().forEach(
                    sensor -> ret.add(sensor)
                );
            }
        );

        return ret;
    }

    @PostMapping("/phsensors")
    public PhSensor createPhSensor(@RequestBody SensorDto sensor) {
        Parcel parcel = this.parcelRepository.findById(sensor.getParcelId()).orElseThrow(
            () -> new ResourceNotFoundException("Parcel not found with id: " + sensor.getParcelId())
        );

        PhSensor sensor2save = new PhSensor(parcel, new Date(System.currentTimeMillis()));
        parcel.getPhSensors().add(sensor2save);
        this.parcelRepository.save(parcel);

        return this.phSensorRepository.save(sensor2save);
    }

    @PutMapping("/phsensors")
    public PhSensor updatePhSensor(@RequestBody SensorDto sensor) {
        Parcel newParcel = this.parcelRepository.findById(sensor.getParcelId()).orElseThrow(
            () -> new ResourceNotFoundException("Parcel not found with id: " + sensor.getParcelId())
        );

        PhSensor sensor2save = this.phSensorRepository.findById(sensor.getId()).orElseThrow(
            () -> new ResourceNotFoundException("Sensor not found with id: " + sensor.getId())
        );

        User user = this.userRepository.findById(sensor.getOwnerId()).orElseThrow(
            () -> new ResourceNotFoundException("User not found with id: " + sensor.getOwnerId())
        );

        Parcel oldParcel = this.parcelRepository.getOne(sensor2save.getParcel().getParcelId());

        // se a nova parcela não pertencer ao user
        if (!user.getParcels().contains(newParcel))
            throw new ResourceNotOwnedException("Not owned parcel with id: " + newParcel.getParcelId());
        
        oldParcel.getPhSensors().remove(sensor2save);
        this.parcelRepository.save(oldParcel);

        newParcel.getPhSensors().add(sensor2save);
        sensor2save.setParcel(newParcel);
        this.parcelRepository.save(newParcel);

        return this.phSensorRepository.save(sensor2save); 
    }

    @DeleteMapping("/phsensors/{id}")
    public void deletePhSensor(@PathVariable(value = "id") Long sensorId) {
        PhSensor sensor = this.phSensorRepository.findById(sensorId).orElseThrow(
            () -> new ResourceNotFoundException("Ph sensor not found with id: " + sensorId)
        );
        this.phSensorRepository.delete(sensor);
    }

    // Métodos para medidas de ph
    @GetMapping("/phmeasures")
    public List<PhMeasure> getAllPhMeasures() {
        return this.phMeasureRepository.findAll();
    }

    @GetMapping("/phmeasures/sensor/{id}")
    public List<PhMeasure> getAllPhMeasuresBySensor(@PathVariable(value = "id") Long sensorId) {
        PhSensor sensor = this.phSensorRepository.findById(sensorId).orElseThrow(
            () -> new ResourceNotFoundException("Ph sensor not found with id: " + sensorId)
        );
        return sensor.getMeasures();
    }

    @PostMapping("/phmeasures")
    public PhMeasure addPhMeasure(@RequestBody MeasureDto measure) {
        PhSensor sensor = this.phSensorRepository.findById(measure.getSensorId()).orElseThrow(
            () -> new ResourceNotFoundException("Ph sensor not found with id: " + measure.getSensorId())
        );
        PhMeasure measure2save = new PhMeasure(
            sensor,
            new Timestamp(System.currentTimeMillis()),
            measure.getValue()
        );
        sensor.getMeasures().add(measure2save);
        this.phSensorRepository.save(sensor);

        return this.phMeasureRepository.save(measure2save);
    }

    // Métodos para sensores de humidade
    @GetMapping("/humsensors")
    public List<HumSensor> getAllHumSensors() {
        return this.humSensorRepository.findAll();
    }

    @GetMapping("/humsensors/{id}")
    public HumSensor getHumSensor(@PathVariable(value = "id") Long sensorId) {
        return this.humSensorRepository.findById(sensorId).orElseThrow(
            () -> new ResourceNotFoundException("Humidity sensor not found with id: " + sensorId)
        );
    }

    @GetMapping("/humsensors/parcel/{id}")
    public Set<HumSensor> getHumSensorsByParcel(@PathVariable(value = "id") Long parcelId) {
        Parcel parcel = this.parcelRepository.findById(parcelId).orElseThrow(
            () -> new ResourceNotFoundException("Parcel not found with id: " + parcelId)
        );
        return parcel.getHumSensors();
    }

    @GetMapping("/humsensors/owner/{id}")
    public List<HumSensor> getHumSensorByOwner(@PathVariable(value = "id") Long userId) {
        User user = this.userRepository.findById(userId).orElseThrow(
            () -> new ResourceNotFoundException("User not found with id: " + userId)
        );
        List<HumSensor> ret = new ArrayList<HumSensor>();

        user.getParcels().forEach(
            parcel -> {
                parcel.getHumSensors().forEach(
                    sensor -> ret.add(sensor)
                );
            }
        );

        return ret;
    }

    @PostMapping("/humsensors")
    public HumSensor createHumSensor(@RequestBody SensorDto sensor) {
        Parcel parcel = this.parcelRepository.findById(sensor.getParcelId()).orElseThrow(
            () -> new ResourceNotFoundException("Parcel not found with id: " + sensor.getParcelId())
        );

        HumSensor sensor2save = new HumSensor(parcel, new Date(System.currentTimeMillis()));
        parcel.getHumSensors().add(sensor2save);
        this.parcelRepository.save(parcel);

        return this.humSensorRepository.save(sensor2save);
    }

    @PutMapping("/humsensors")
    public HumSensor updateHumSensor(@RequestBody SensorDto sensor) {
        Parcel newParcel = this.parcelRepository.findById(sensor.getParcelId()).orElseThrow(
            () -> new ResourceNotFoundException("Parcel not found with id: " + sensor.getParcelId())
        );

        HumSensor sensor2save = this.humSensorRepository.findById(sensor.getId()).orElseThrow(
            () -> new ResourceNotFoundException("Humidity sensor not found with id: " + sensor.getId())
        );

        User user = this.userRepository.findById(sensor.getOwnerId()).orElseThrow(
            () -> new ResourceNotFoundException("User not found with id: " + sensor.getOwnerId())
        );

        Parcel oldParcel = this.parcelRepository.getOne(sensor2save.getParcel().getParcelId());

        // se a nova parcela não pertencer ao user
        if (!user.getParcels().contains(newParcel))
            throw new ResourceNotOwnedException("Not owned parcel with id: " + newParcel.getParcelId());
        
        oldParcel.getHumSensors().remove(sensor2save);
        this.parcelRepository.save(oldParcel);

        newParcel.getHumSensors().add(sensor2save);
        sensor2save.setParcel(newParcel);
        this.parcelRepository.save(newParcel);

        return this.humSensorRepository.save(sensor2save); 
    }

    @DeleteMapping("/humsensors/{id}")
    public void deleteHumSensor(@PathVariable(value = "id") Long sensorId) {
        HumSensor sensor = this.humSensorRepository.findById(sensorId).orElseThrow(
            () -> new ResourceNotFoundException("Humidity sensor not found with id: " + sensorId)
        );
        this.humSensorRepository.delete(sensor);
    }
    // Métodos para medidas de humidade
    @GetMapping("/hummeasures")
    public List<HumMeasure> getAllHumMeasures() {
        return this.humMeasureRepository.findAll();
    }

    @GetMapping("/hummeasures/sensor/{id}")
    public List<HumMeasure> getAllHumMeasuresBySensor(@PathVariable(value = "id") Long sensorId) {
        HumSensor sensor = this.humSensorRepository.findById(sensorId).orElseThrow(
            () -> new ResourceNotFoundException("Humidity sensor not found with id: " + sensorId)
        );
        return sensor.getMeasures();
    }

    @PostMapping("/hummeasures")
    public HumMeasure addHumMeasure(@RequestBody MeasureDto measure) {
        HumSensor sensor = this.humSensorRepository.findById(measure.getSensorId()).orElseThrow(
            () -> new ResourceNotFoundException("Humidity sensor not found with id: " + measure.getSensorId())
        );
        HumMeasure measure2save = new HumMeasure(
            sensor,
            new Timestamp(System.currentTimeMillis()),
            measure.getValue()
        );
        sensor.getMeasures().add(measure2save);
        this.humSensorRepository.save(sensor);

        return this.humMeasureRepository.save(measure2save);
    }

    // Métodos para avaliacões
    @GetMapping("/avaliations")
    public List<Avaliation> getAllAvaliations() {
        return this.avaliationRepository.findAll();
    }

    @GetMapping("/avaliations/{id}")
    public Avaliation getAvaliation(@PathVariable(value = "id") Long id) {
        return this.avaliationRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Avaliation not found with id: " + id)
        );
    }

    @GetMapping("/avaliations/user/{id}")
    public Set<Avaliation> getAvaliationByUser(@PathVariable(value = "id") Long id) {
        User user = this.userRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("User not found with id: " + id)
        );
        return user.getAvaliations();
    }

    @PostMapping("/avaliations")
    public Avaliation addAvaliation(@RequestBody AvaliationDto avaliation) {
        User user = this.userRepository.findById(avaliation.getUserId()).orElseThrow(
            () -> new ResourceNotFoundException("User not found with id: " + avaliation.getUserId())
        );
        
        Avaliation avaliation2save = new Avaliation(
            user,
            avaliation.getStars(),
            avaliation.getComment()
        );
        user.getAvaliations().add(avaliation2save);
        this.userRepository.save(user);

        return this.avaliationRepository.save(avaliation2save);
    }

    @PutMapping("/avaliations")
    public Avaliation updateAvaliation(@RequestBody AvaliationDto avaliation) {
        Avaliation avaliation2save = this.avaliationRepository.findById(avaliation.getId()).orElseThrow(
            () -> new ResourceNotFoundException("Avaliation not found with id: " + avaliation.getId())
        );

        avaliation2save.setStars(avaliation.getStars());
        avaliation2save.setComment(avaliation.getComment());

        return this.avaliationRepository.save(avaliation2save);
    }

    @DeleteMapping("/avaliations/{id}")
    public void deleteAvaliation(@PathVariable(value = "id") Long id) {
        Avaliation avaliation = this.avaliationRepository.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Avaliation not found with id: " + id)
        );

        this.avaliationRepository.delete(avaliation);
    }
}
