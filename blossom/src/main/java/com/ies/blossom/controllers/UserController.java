package com.ies.blossom.controllers;

import java.text.ParseException;
import com.ies.blossom.entitys.User;
import com.ies.blossom.repositorys.UserRepository;
import com.ies.blossom.security.CustomUserDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class UserController {

	@Autowired
	private UserRepository userRepository;

    @GetMapping("/profile")
    public String getIssues(Model model, Authentication auth) throws ParseException {
        CustomUserDetails userLogged = (CustomUserDetails) auth.getPrincipal();

        User user = this.userRepository.findByEmail(userLogged.getUsername());
	    model.addAttribute("user", user);
	    return "user.html";
    	
    }
    
}
