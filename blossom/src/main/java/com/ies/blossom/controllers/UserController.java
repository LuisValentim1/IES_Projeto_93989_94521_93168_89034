package com.ies.blossom.controllers;

import java.security.Principal;
import java.text.ParseException;
import com.ies.blossom.entitys.User;
import com.ies.blossom.repositorys.UserRepository;
import com.ies.blossom.security.CustomUserDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class UserController {

	@Autowired
	private UserRepository userRepository;
	/*
    UserRepository userRepository;
	ParcelRepository parcelRepository;

    public UserController(UserRepository userRepository, ParcelRepository parcelRepository) {
        this.userRepository = userRepository;
        this.parcelRepository = parcelRepository;
    }*/


    @GetMapping("/profile")
    public String getIssues(Model model, Authentication auth) throws ParseException {
        CustomUserDetails userLogged = (CustomUserDetails) auth.getPrincipal();

        User user = this.userRepository.findByEmail(userLogged.getUsername());
	    model.addAttribute("user", user);
	    return "user.html";
    	
    }
    
}
