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
import com.ies.blossom.model.ChangePlantModel;
import com.ies.blossom.model.ParcelModel;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ParcelController {

    @Autowired
    private ParcelRepository parcelRepository;
    @Autowired
    private PlantRepository plantRepository;
    @Autowired
    private UserRepository userRepository;

    // TODO Change the URL MAPPING TO parcel NAME DYNAMICALY //

    @GetMapping("/parcel/{id}")
    public String getParcel(Model model, @PathVariable(value="id") Long parcelId) {
        Parcel parcel = this.parcelRepository.getOne(parcelId);

        // ir buscar todos as ultimas medidas relativas aos sensores de ph
        if (!parcel.getPhSensors().isEmpty()) {
            Map<PhSensor, PhMeasure> retPh = new HashMap<PhSensor, PhMeasure>();
            
            for (PhSensor sensor : parcel.getPhSensors()) {
                if (!sensor.getMeasures().isEmpty()) {
                    retPh.put(sensor, sensor.getMeasures().get(0)); // em vez de ir buscar o primeiro pode ir buscar o ultimo
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
                    retHum.put(sensor, sensor.getMeasures().get(0)); // em vez de ir buscar o primeiro pode ir buscar o ultimo
                }else{
                    retHum.put(sensor, null);
                }
            }
            // há sensores de humidade mas n há medicoes
            
            model.addAttribute("humSensorsLastMeasures", retHum);
        }

        // ir buscar todas as plantas na bd
        // talvez seja melhor colocar noutro método, esta funcionalidade é chamada poucas vezes
        List<Plant> plants = this.plantRepository.findAll();
        model.addAttribute("plantForm", new ChangePlantModel());
        model.addAttribute("plants", plants);

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
    public String newParcel(Model model, @ModelAttribute ParcelModel parcel) {
        // TODO verificar se a parcela já n tinha sido criada previamente
        Parcel parcel2save = new Parcel();
        parcel2save.setLocation(parcel.getLocation());
        
        if (parcel.getPlant() != 0) {
            Plant plant = this.plantRepository.getOne(parcel.getPlant());
            plant.getParcels().add(parcel2save);
            parcel2save.setPlant(plant);
            this.plantRepository.save(plant);
        }

        // TODO tem de ser alterado para se associar à pessoa q fez login
        User user = this.userRepository.getOne(1L);
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

        if (change.getCurrentPlantId() == 0L) {
            Plant previous = this.plantRepository.getOne(change.getPreviousPlantId());
            previous.getParcels().remove(parcel);
            this.plantRepository.save(previous);

            parcel.setPlant(null);
            this.parcelRepository.save(parcel);
            return "redirect:" + request.getHeader("Referer");
        }
        Plant current = this.plantRepository.getOne(change.getCurrentPlantId());

        if (change.getPreviousPlantId() != null) {
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
    
    // private static void makeData(User user, Parcel parcel, Collection<Plant> plants) throws ParseException {
    	
    // 	user.setName("Jaime");
    	
    // 	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    	
    // 	parcel.setLocation("Aveiro");
    //     parcel.setOwner(user);
        
    //     Plant plant01 = new Plant("planta in parcel", "parcel planta", 2.0, 1.0, 1.0, 1.8);
    //     parcel.setPlant(plant01);
        
    //     HumSensor humsensor = new HumSensor(parcel, new Date((sdf.parse("2019-01-01 00:00:00")).getTime()));
    //     PhSensor phsensor = new PhSensor(parcel, new Date((sdf.parse("2019-01-01 00:00:00")).getTime()));
    //     parcel.addHumSensor(humsensor);
    //     parcel.addPhSensor(phsensor);
    //     humsensor.addHumMeasure(new HumMeasure(humsensor, new Timestamp((sdf.parse("2020-06-01 00:00:00")).getTime()), 2.1));
    //     humsensor.addHumMeasure(new HumMeasure(humsensor, new Timestamp((sdf.parse("2020-06-03 00:00:00")).getTime()), 1.9));
    //     humsensor.addHumMeasure(new HumMeasure(humsensor, new Timestamp((sdf.parse("2020-06-05 00:00:00")).getTime()), 1.7));
    //     phsensor.addPhMeasure(new PhMeasure(phsensor, new Timestamp((sdf.parse("2020-06-01 00:00:00")).getTime()), 2.1));
    //     phsensor.addPhMeasure(new PhMeasure(phsensor, new Timestamp((sdf.parse("2020-06-03 00:00:00")).getTime()), 1.9));
    //     phsensor.addPhMeasure(new PhMeasure(phsensor, new Timestamp((sdf.parse("2020-06-05 00:00:00")).getTime()), 1.7));
        
    //     humsensor = new HumSensor(parcel, new Date((sdf.parse("2019-01-01 00:00:00")).getTime()));
    //     phsensor = new PhSensor(parcel, new Date((sdf.parse("2019-01-01 00:00:00")).getTime()));
    //     parcel.addHumSensor(humsensor);
    //     parcel.addPhSensor(phsensor);
    //     humsensor.addHumMeasure(new HumMeasure(humsensor, new Timestamp((sdf.parse("2020-06-01 00:00:00")).getTime()), 2.1));
    //     humsensor.addHumMeasure(new HumMeasure(humsensor, new Timestamp((sdf.parse("2020-06-03 00:00:00")).getTime()), 1.9));
    //     humsensor.addHumMeasure(new HumMeasure(humsensor, new Timestamp((sdf.parse("2020-06-05 00:00:00")).getTime()), 1.7));
    //     phsensor.addPhMeasure(new PhMeasure(phsensor, new Timestamp((sdf.parse("2020-06-01 00:00:00")).getTime()), 2.1));
    //     phsensor.addPhMeasure(new PhMeasure(phsensor, new Timestamp((sdf.parse("2020-06-03 00:00:00")).getTime()), 1.9));
    //     phsensor.addPhMeasure(new PhMeasure(phsensor, new Timestamp((sdf.parse("2020-06-05 00:00:00")).getTime()), 1.7));
        

    //     plants.add(plant01);
    //     plants.add(new Plant("planta02", "planta02", 2.0, 1.0, 1.0, 2.5));
    //     plants.add(new Plant("planta03", "planta03", 2.0, 1.0, 1.0, 2.0));
    //     plants.add(new Plant("plantatusCuatro", "planta04", 2.0, 1.0, 1.0, 1.5));
    	
    // }
    
}