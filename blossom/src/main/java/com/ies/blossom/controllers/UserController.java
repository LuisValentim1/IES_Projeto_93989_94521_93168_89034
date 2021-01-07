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

import org.springframework.beans.factory.annotation.Autowired;
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
        Map<Parcel, GoodPlantModel> mapa = new HashMap<Parcel, GoodPlantModel>();
        Boolean everythingGood = null;
        for (Parcel parcel : user.getParcels()) {
			GoodPlantModel gpm = parcel.checkPlantConditions();
			if (everythingGood != false) {
				everythingGood = gpm.isGood();
			}
			mapa.put(parcel, gpm);
		}
        
        model.addAttribute("ParcelConditions", mapa);
        model.addAttribute("noError", everythingGood);
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
