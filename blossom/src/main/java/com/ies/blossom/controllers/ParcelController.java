package com.ies.blossom.controllers;

import com.ies.blossom.entitys.HumMeasure;
import com.ies.blossom.entitys.HumSensor;
import com.ies.blossom.entitys.Parcel;
import com.ies.blossom.entitys.PhMeasure;
import com.ies.blossom.entitys.PhSensor;
import com.ies.blossom.entitys.Plant;
import com.ies.blossom.entitys.User;
import com.ies.blossom.repositorys.ParcelRepository;
import com.ies.blossom.repositorys.PlantRepository;
import com.ies.blossom.repositorys.UserRepository;
import com.ies.blossom.security.CustomUserDetails;
import com.ies.blossom.model.ChangePlantModel;
import com.ies.blossom.model.ParcelModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ParcelController {

    @Autowired
    private ParcelRepository parcelRepository;
    @Autowired
    private PlantRepository plantRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/parcel/{id}")
    public String getParcel(Model model, @PathVariable(value="id") Long parcelId, Authentication auth) {
        CustomUserDetails userLogged = (CustomUserDetails) auth.getPrincipal();
        Parcel parcel = this.parcelRepository.getOne(parcelId);

        if (!parcel.getOwner().getUserId().equals(userLogged.getId())) {
            model.addAttribute("notOwned", true);
            return "parcel.html";
        }
        model.addAttribute("notOwned", false);      
     // ir buscar todos as ultimas medidas relativas aos sensores de ph
        if (!parcel.getPhSensors().isEmpty()) {
            Map<PhSensor, PhMeasure> retPh = new HashMap<PhSensor, PhMeasure>();
            
            for (PhSensor sensor : parcel.getPhSensors()) {
                if (!sensor.getMeasures().isEmpty()) {
                    retPh.put(sensor, sensor.getMeasures().get(sensor.getMeasures().size()-1));
                }else{
                    retPh.put(sensor, null);
                }
            }
            // se houver sensores mais ainda n houver medicoes            
            model.addAttribute("phSensorsLastMeasures", retPh);
        }

        // ir buscar todos as ultimas medidas relativas aos sensores de hum
        if (!parcel.getHumSensors().isEmpty()) {
            Map<HumSensor, HumMeasure> retHum = new HashMap<HumSensor, HumMeasure>();
            for (HumSensor sensor : parcel.getHumSensors()) {
                if (!sensor.getMeasures().isEmpty()) {
                    retHum.put(sensor, sensor.getMeasures().get(sensor.getMeasures().size()-1));
                }else{
                    retHum.put(sensor, null);
                }
            }
            
            // há sensores de humidade mas n há medicoes
            model.addAttribute("humSensorsLastMeasures", retHum);
        }
        
        model.addAttribute("goodPlant", parcel.checkPlantConditions());
        // ir buscar todas as plantas na bd
        // talvez seja melhor colocar noutro método, esta funcionalidade é chamada poucas vezes
        List<Plant> plants = this.plantRepository.findAll();
        List<Plant> matchPlants = new ArrayList<Plant>(plants);
        
        matchPlants.remove(parcel.getPlant());
        
        model.addAttribute("plantForm", new ChangePlantModel());
        model.addAttribute("plants", plants);
        model.addAttribute("matchPlants", parcel.bestPlants(matchPlants));

        model.addAttribute("parcel", parcel);
        
        
        
        return "parcel.html";
    }

    @GetMapping("/parcel/new")
    public String parcelForm(Model model) {
        ParcelModel form = new ParcelModel();

        // ir buscar todas as plantas na bd
        List<Plant> plants = this.plantRepository.findAll();
        
        // adicionar as plantas existentes ao form
        form.setPlants(plants);        

        model.addAttribute("form", form);
        model.addAttribute("created", false);
        return "forms/parcelForm.html";
    }
    
    @PostMapping("/parcel/new")
    public String newParcel(Model model, @ModelAttribute ParcelModel parcel, Authentication auth) {
        CustomUserDetails userLogged = (CustomUserDetails) auth.getPrincipal();
        
        // TODO verificar se a parcela já n tinha sido criada previamente
        Parcel parcel2save = new Parcel();
        parcel2save.setLocation(parcel.getLocation());
        
        if (!parcel.getPlant().equals(0L)) {
            // plant == 0 means no plant is associated with parcel
            Plant plant = this.plantRepository.getOne(parcel.getPlant());
            plant.getParcels().add(parcel2save);
            parcel2save.setPlant(plant);
            this.plantRepository.save(plant);
        }

        User user = this.userRepository.getOne(userLogged.getId());
        user.getParcels().add(parcel2save);
        this.userRepository.save(user);

        parcel2save.setOwner(user);
        this.parcelRepository.save(parcel2save);
        

        model.addAttribute("created", true);
        return "forms/parcelForm.html";
    }

    @PostMapping("/parcel/editplant")
    public String changePlant(@ModelAttribute ChangePlantModel change,
                                HttpServletRequest request) {

        Parcel parcel = this.parcelRepository.getOne(change.getParcelId());

        if (change.getCurrentPlantId().equals(0L)) {
            // parcel without plant
            Plant previous = this.plantRepository.getOne(change.getPreviousPlantId());
            previous.getParcels().remove(parcel);
            this.plantRepository.save(previous);

            parcel.setPlant(null);
            this.parcelRepository.save(parcel);
            return "redirect:" + request.getHeader("Referer");
        }
        Plant current = this.plantRepository.getOne(change.getCurrentPlantId());

        if (change.getPreviousPlantId() != null) {
            // if changed plant
            Plant previous = this.plantRepository.getOne(change.getPreviousPlantId());
            previous.getParcels().remove(parcel);
            this.plantRepository.save(previous);
        }

        current.getParcels().add(parcel);
        this.plantRepository.save(current);

        parcel.setPlant(current);
        this.parcelRepository.save(parcel);
        return "redirect:" + request.getHeader("Referer");
    }
    
    
}