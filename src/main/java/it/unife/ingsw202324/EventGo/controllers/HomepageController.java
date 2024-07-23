package it.unife.ingsw202324.EventGo.controllers;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import it.unife.ingsw202324.EventGo.models.Organizzatore;
import it.unife.ingsw202324.EventGo.services.OrganizzatoreService;
import it.unife.ingsw202324.EventGo.services.TemplateRestConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/homepage")
public class HomepageController {

    private final OrganizzatoreService organizzatoreService;

    @Autowired
    public HomepageController(OrganizzatoreService organizzatoreService) {
        this.organizzatoreService = organizzatoreService;
    }

    @GetMapping("/notifiche")
    public String fetchNotifications() {
        return TemplateRestConsumer.callREST("getNotifications", null, true);

    }

    @GetMapping("/utente")
    public String fetchLoggedUser() {
        String loggedUser = TemplateRestConsumer.callREST("getLoggedUser", null, true);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        Organizzatore organizzatore = null;
        try {
            organizzatore = objectMapper.readValue(loggedUser, Organizzatore.class);
            organizzatoreService.findOrCreateOrganizzatore(organizzatore);
            System.out.println(loggedUser);
            return loggedUser;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "{}";

    }

    @GetMapping("/eventiConclusi")
    public String fetchPastEvents() {
        return TemplateRestConsumer.callREST("getPastEvents", null, true);

    }

    @GetMapping("/eventiFuturi")
    public String fetchNextEvents() {
        return TemplateRestConsumer.callREST("getNextEvents", null, true);

    }
}
