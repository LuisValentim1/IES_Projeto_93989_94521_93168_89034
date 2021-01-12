package com.ies.blossom.controllers;

import java.sql.Timestamp;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import com.ies.blossom.dto.UserDto;
import com.ies.blossom.encoders.PasswordEncoder;
import com.ies.blossom.entitys.User;
import com.ies.blossom.exceptions.ResourceNotFoundException;
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
}
