package com.ies.blossom.controllers;

import java.sql.Date;
import java.sql.Timestamp;

import com.ies.blossom.dto.UserDto;
import com.ies.blossom.entitys.User;
import com.ies.blossom.repositorys.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FrontController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public String viewIndex() {
        return "index.html";
    }

    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact.html";
    }

    @GetMapping("/about")
    public String about() {
        return "about.html";
    }

    @GetMapping("/login")
    public String login() {
        return "login.html";
    }

    // para o login e registo
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        // ver se dá para fazer com o User()
        model.addAttribute("user", new UserDto());

        return "forms/signupForm.html";
    }

    @PostMapping("/process_register")
    public String processRegister(Model model, UserDto user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Date entryDate = new Date(System.currentTimeMillis());
        Timestamp lastJoined = new Timestamp(System.currentTimeMillis());

        User user2save = new User(user.getName(), user.getEmail(), entryDate,
        passwordEncoder.encode(user.getPassword()), user.getPhoneNumber(), lastJoined, true);

        this.userRepository.save(user2save);
        model.addAttribute("created", true);
        return "forms/signupForm.html";
    }
}
