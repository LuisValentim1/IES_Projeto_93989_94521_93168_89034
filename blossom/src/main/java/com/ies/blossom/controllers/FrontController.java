package com.ies.blossom.controllers;

import java.sql.Date;
import java.sql.Timestamp;

import com.ies.blossom.dto.UserDto;
import com.ies.blossom.entitys.User;
import com.ies.blossom.repositorys.UserRepository;
import com.ies.blossom.security.CustomUserDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Controller
public class FrontController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("")
    public String viewIndex(Authentication auth, Model model) {
        if (auth == null)
            return "index.html";
        return this.showIndex(auth, model);
    }

    @GetMapping("/")
    public String index(Authentication auth, Model model) {
        if (auth == null)
            return "index.html";
        return this.showIndex(auth, model);
    }

    private String showIndex(Authentication auth, Model model) {
        CustomUserDetails userLogged = (CustomUserDetails) auth.getPrincipal();

        if (!userLogged.isAdmin()) {
            return "index.html";
        }

        List<User> users = this.userRepository.findAllUsersNotAdmin();

        model.addAttribute("users", users);
        
        model.addAttribute("joinedData", this.getJoinedData(users));
        model.addAttribute("lastJoinedData", this.getLastJoinedData(users));

        return "admin.html";
    }

    private String getLastJoinedData(List<User> users) {
        String ret = "";
        Map<String, Integer> map = new TreeMap<String, Integer>();

        for (User user : users) {
            String date = new Date(user.getLastJoined().getTime()).toString();
            if (!map.containsKey(date)) {
                map.put(date, 1);
            } else {
                map.put(date, map.get(date) + 1);                
            }
        }

        Iterator<Map.Entry<String, Integer>> it = map.entrySet().iterator();

        while(it.hasNext()) {
            Map.Entry<String, Integer> entry = it.next();
            ret += entry.getKey() + " " + entry.getValue().toString();

            if (it.hasNext())
                ret += "DELIMITER";
        }

        return ret;
    }

    private String getJoinedData(List<User> users) {
        String ret = "";
        Map<String, Integer> map = new TreeMap<String, Integer>();

        for (User user : users) {
            if (!map.containsKey(user.getEntryDate().toString())) {
                map.put(user.getEntryDate().toString(), 1);
            } else {
                map.put(user.getEntryDate().toString(), map.get(user.getEntryDate().toString()) + 1);                
            }
        }

        Iterator<Map.Entry<String, Integer>> it = map.entrySet().iterator();

        while(it.hasNext()) {
            Map.Entry<String, Integer> entry = it.next();
            ret += entry.getKey() + " " + entry.getValue().toString();

            if (it.hasNext())
                ret += "DELIMITER";
        }

        return ret;
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
