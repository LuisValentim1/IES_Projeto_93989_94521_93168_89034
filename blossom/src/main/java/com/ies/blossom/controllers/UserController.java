package com.ies.blossom.controllers;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import com.ies.blossom.dto.AvaliationDto;
import com.ies.blossom.entitys.Avaliation;
import com.ies.blossom.entitys.Parcel;
import com.ies.blossom.entitys.User;
import com.ies.blossom.model.GoodPlantModel;
import com.ies.blossom.repositorys.AvaliationRepository;
import com.ies.blossom.repositorys.UserRepository;
import com.ies.blossom.security.CustomUserDetails;
import com.sun.org.apache.xpath.internal.operations.Bool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.AllNestedConditions;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

	@Autowired
    private UserRepository userRepository;
    
    @Autowired
    private AvaliationRepository avaliationRepository;

    @GetMapping("/profile")
    public String getUser(Model model, Authentication auth) throws ParseException {
        CustomUserDetails userLogged = (CustomUserDetails) auth.getPrincipal();

        User user = this.userRepository.findByEmail(userLogged.getUsername());
        model.addAttribute("user", user);
        return "user.html";

    }

    @GetMapping("/myparcels")
    public String getParcels(Model model, Authentication auth) throws ParseException {    	
        CustomUserDetails userLogged = (CustomUserDetails) auth.getPrincipal();
        User user = this.userRepository.findByEmail(userLogged.getUsername());
        model.addAttribute("user", user);
                
        boolean allnull = true;
        boolean everythingGood = true;
        Map<Parcel, Boolean> mapa = new HashMap<Parcel, Boolean>();
        for (Parcel parcel : user.getParcels()) {
        	mapa.put(parcel, parcel.checkPlantConditions().isGood());
        	Boolean isgood = mapa.get(parcel);
        	if(isgood != null) {
        		allnull = false;
        		if(!isgood) {
        			everythingGood = false;
        		}
        	}
		}
        
        Boolean isGood;
        if(allnull) {
        	isGood = null;
        } else {
        	if (everythingGood) {
        		isGood = true;
        	} else {
        		isGood = false;
        	}
        }
        model.addAttribute("noError", isGood);
        model.addAttribute("goodPlants", mapa);
        
        return "myparcels.html";

    }
    
    

    @GetMapping("/comment/new")
    public String getFormComment(Model model) {
        AvaliationDto form = new AvaliationDto();

        model.addAttribute("form", form);
        model.addAttribute("created", false);
        return "forms/commentForm.html";
    }

    @PostMapping("/comment/new")
    public String newComment(Model model, @ModelAttribute AvaliationDto comment, Authentication auth) {
        CustomUserDetails userLogged = (CustomUserDetails) auth.getPrincipal();

        User user = this.userRepository.getOne(userLogged.getId());
        Avaliation aval2save = new Avaliation(user, comment.getStars(), comment.getComment());

        user.getAvaliations().add(aval2save);
        this.userRepository.save(user);

        this.avaliationRepository.save(aval2save);
        model.addAttribute("created", true);
        return "forms/commentForm.html";
    }
    
}
