package pt.ua.Blossom_Gen.Blossom_Gerador;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/getmeasures")
public class RController {

    @CrossOrigin(origins = "*")
    @GetMapping
    public void generateMeasures() {
        try {
            Gerador.main(null);
        } catch (Exception e) {
            System.out.println("N funcionou");
        }
        
    }

}