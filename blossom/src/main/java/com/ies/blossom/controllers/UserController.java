package com.ies.blossom.controllers;


import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

import com.ies.blossom.entitys.Parcel;
import com.ies.blossom.entitys.Plant;
import com.ies.blossom.entitys.User;
import com.ies.blossom.repositorys.ParcelRepository;
import com.ies.blossom.repositorys.PlantRepository;
import com.ies.blossom.repositorys.UserRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@Controller
public class UserController {


    UserRepository userRepository;
	ParcelRepository parcelRepository;

    public UserController(UserRepository userRepository, ParcelRepository parcelRepository) {
        this.userRepository = userRepository;
        this.parcelRepository = parcelRepository;
    }


    @GetMapping("/profile")
    public String getIssues(Model model) throws ParseException {

		Date date = new Date((new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2011-01-01 00:00:00")).getTime());
	    User user = new User("Carlos Gomes", "carlos.gomes@gmail.com", date, "password", "123456789", new Timestamp(System.currentTimeMillis()), true);
	    model.addAttribute("user", user);
	    List<Parcel> lista = new ArrayList<Parcel>();
	    lista.add(new Parcel(user, "aveiro"));
	    lista.add(new Parcel(user, "coimbra"));
	    lista.add(new Parcel(user, "lisboa"));    
	    model.addAttribute("parcels", lista);
	    return "user-crops.html";
    	
    }
    
}
