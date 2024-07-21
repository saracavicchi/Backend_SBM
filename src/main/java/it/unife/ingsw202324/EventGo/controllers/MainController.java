package it.unife.ingsw202324.EventGo.controllers;

import it.unife.ingsw202324.EventGo.models.Organizzatore;
import it.unife.ingsw202324.EventGo.services.MyService;
import it.unife.ingsw202324.EventGo.services.TemplateRestConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController /* Annotation per definire che la classe risponderà tramite rest  */
@RequestMapping("/api/home") /* Annotation per definire il path della classe  */
public class MainController {


    private final MyService myService;

    @Autowired
    public MainController(MyService myService) {
        this.myService = myService;
    }

    @RequestMapping("/testMysql") /* Annotation per definire il path del metodo (relativo alla classe)  */
    public void testMysql() {
        System.out.println("Ciao");
    }


    @RequestMapping("/callREST") /* Annotation per definire il path del metodo (relativo alla classe)  */
    public String callRest() {
        return TemplateRestConsumer.callREST("getRecentEvents", null, true);
    }

    @RequestMapping("/prova") /* Annotation per definire il path del metodo (relativo alla classe)  */
    public String test() {
        return "ciao";
    }
}
