package it.unife.ingsw202324.EventGo.controllers;

import it.unife.ingsw202324.EventGo.services.TemplateRestConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/homepage")
public class HomepageController {

    @GetMapping("/notifiche")
    public String fetchNotifications() {
        return TemplateRestConsumer.callREST("getNotifications", null, true);

    }

    @GetMapping("/utente")
    public String fetchLoggedUser() {
        return TemplateRestConsumer.callREST("getLoggedUser", null, true);

    }

    @GetMapping("/eventiConclusi")
    public String fetchPastEvents() {
        return TemplateRestConsumer.callREST("getPastEvents", null, true);

    }

    @GetMapping("/eventiFuturi")
    public String fetchNextEvents() {
        return TemplateRestConsumer.callREST("getNextEvents", null, true);

    }

    @GetMapping("/marzel")
    public String fetchMarzel() {
        return TemplateRestConsumer.callREST("getElMarzel", null, true);

    }

}
