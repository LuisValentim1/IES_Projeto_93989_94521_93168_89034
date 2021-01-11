package com.ies.blossom.controllers;

import java.sql.Date;
import java.sql.Timestamp;

import com.ies.blossom.dto.UserDto;
import com.ies.blossom.entitys.HumMeasure;
import com.ies.blossom.entitys.HumSensor;
import com.ies.blossom.entitys.Measure;
import com.ies.blossom.entitys.Parcel;
import com.ies.blossom.entitys.PhSensor;
import com.ies.blossom.entitys.User;
import com.ies.blossom.repositorys.AvaliationRepository;
import com.ies.blossom.repositorys.ParcelRepository;
import com.ies.blossom.repositorys.UserRepository;
import com.ies.blossom.security.CustomUserDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Controller
public class FrontController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AvaliationRepository avaliationRepository;

    @Autowired
    private ParcelRepository parcelRepository;

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

    @GetMapping("/parcelhealth/{id}")
    public String parcelHealth(Authentication auth, Model model, @PathVariable(value = "id") Long parcelId) {
        CustomUserDetails userLogged = (CustomUserDetails) auth.getPrincipal();

        if (parcelId == null || userLogged == null) {
            model.addAttribute("httpError", "500");
            model.addAttribute("errorMessage", "Error loading page.");
            return "messageError.html";
        }

        if (!userLogged.isAdmin()) {
            model.addAttribute("httpError", "401");
            model.addAttribute("errorMessage", "You're not allowed to see this secret content.");
            return "messageError.html";
        }

        Parcel parcel = this.parcelRepository.getOne(parcelId);

        if (checkHum(parcel))
            model.addAttribute("humidity", true);

        if (checkPh(parcel))
            model.addAttribute("ph", true);

        model.addAttribute("parcel", parcel);

        return "adminCheckParcel.html";
    }

    private String showIndex(Authentication auth, Model model) {
        CustomUserDetails userLogged = (CustomUserDetails) auth.getPrincipal();

        if (!userLogged.isAdmin()) {
            return "index.html";
        }

        List<User> users = this.userRepository.findAllUsersNotAdmin();

        model.addAttribute("users", users);

        model.addAttribute("comments", this.avaliationRepository.findAll(Sort.by(Sort.Direction.DESC, "timestamp")));
        model.addAttribute("joinedData", this.getJoinedData(users));
        model.addAttribute("lastJoinedData", this.getLastJoinedData(users));

        healthyPlants(users);

        for (User user : users)
            for (Parcel parcel : user.getParcels())
                System.out.println("Parcela q vai aparecer: " + parcel.getParcelId());

        return "admin.html";
    }

    private void healthyPlants(List<User> users) {
        for (User user : users) {
            for (Parcel parcel : user.getParcels()) {
                System.out.println("A tratar da parcela: " + parcel.toString());
                boolean flag1 = false, flag2 = false;
                if (!parcel.getHumSensors().isEmpty()) {
                    flag1 = checkHum(parcel);
                }
                if (!parcel.getPhSensors().isEmpty()) {
                    flag2 = checkPh(parcel);
                }

                // se uma das flags for verdadeira quer dizer que algo está errado com esta parcela
                // portanto ela continua associada ao user para que se possa trabalhar com a parcela na view
                if (flag1 || flag2)
                    continue;

                System.out.println("A remover: " + parcel.toString());
                user.getParcels().remove(parcel);
            }
        }
    }

    private boolean checkHum(Parcel parcel) {
        if (parcel.getPlant() == null)
            return false;
        
        if (parcel.getHumSensors().isEmpty())
            return false;

        double wrong = 0.0;
        double sensors = 0.0;
        for (HumSensor sensor : parcel.getHumSensors()) {
            if (sensor.getMeasures().isEmpty())
                continue;
            
            double measure = sensor.getMeasures().get(sensor.getMeasures().size()-1).getValue();
            if (measure < parcel.getPlant().getHumMin() || measure > parcel.getPlant().getHumMax())
                wrong += 1;
            sensors += 1;
        }

        // se + que 40% dos sensores detetarem valores anómalos
        // notificar que terreno pode precisar de cuidados
        double percentage = wrong/sensors;
        System.out.println("Percentage na hum " + percentage);

        if (percentage < 0.4)
            return false;

        return true;
    }

    private boolean checkPh(Parcel parcel) {
        if (parcel.getPlant() == null)
            return false;

        if (parcel.getPhSensors().isEmpty())
            return false;

        double wrong = 0.0;
        double sensors = 0.0;
        for (PhSensor sensor : parcel.getPhSensors()) {
            if (sensor.getMeasures().isEmpty())
                continue;

            double measure = sensor.getMeasures().get(sensor.getMeasures().size()-1).getValue();
            if (measure < parcel.getPlant().getPhMin() || measure > parcel.getPlant().getPhMax())
                wrong += 1;
            sensors += 1;
        }

        double percentage = wrong/sensors;
        System.out.println("Percentage no ph " + percentage);

        if (percentage < 0.4)
            return false;

        return true;
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
