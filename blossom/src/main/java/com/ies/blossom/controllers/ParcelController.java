package com.ies.blossom.controllers;

import com.ies.blossom.entitys.Parcel;
import com.ies.blossom.entitys.User;
import com.ies.blossom.repositorys.ParcelRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ParcelController {

    ParcelRepository parcelRepository;

    public ParcelController(ParcelRepository parcelRepository) {
        this.parcelRepository = parcelRepository;
    }

    // TODO Change the URL MAPPING TO parcel NAME DYNAMICALY //

    @GetMapping("/parcel")
    public String getIssues(Model model) {
        User Jaime = new User();
        model.addAttribute("parcel", new Parcel(Jaime, "Aveiro"));
        return "parcel.html";
    }
}