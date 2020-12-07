package com.ies.blossom.controllers;

import com.ies.blossom.encoders.PasswordEncoder;
import com.ies.blossom.entitys.User;
import com.ies.blossom.repositorys.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class RestUserController {

    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @GetMapping
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        Date entryDate = new Date(System.currentTimeMillis());
        Timestamp lastJoined = new Timestamp(System.currentTimeMillis());

//        String password = passwordEncoder.encode(user.getPassword());

        User user2save = new User(user.getName(), user.getEmail(), entryDate,
                user.getPassword(), user.getPhoneNumber(), lastJoined, true);
        return this.userRepository.save(user2save);
    }
}
