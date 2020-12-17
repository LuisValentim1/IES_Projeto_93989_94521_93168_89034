package com.ies.blossom.controllers;

import java.text.ParseException;
import com.ies.blossom.entitys.User;
import com.ies.blossom.repositorys.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
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


    @GetMapping("/profile/{id}")
    public String getIssues(Model model, @PathVariable(value = "id") Long userId) throws ParseException {
		User user = this.userRepository.getOne(userId);
	    model.addAttribute("user", user);
	    return "user.html";
    	
    }
    
}
