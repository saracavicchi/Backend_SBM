package it.unife.ingsw202324.EventGo.controllers;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import it.unife.ingsw202324.EventGo.models.Organizzatore;
import it.unife.ingsw202324.EventGo.services.OrganizzatoreService;
import it.unife.ingsw202324.EventGo.services.TemplateRestConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Controller per la gestione delle richieste relative alla homepage.
 * Fornisce endpoint per recuperare notifiche, informazioni sull'utente loggato e sugli eventi.
 */
@RestController
@RequestMapping("/api/homepage")
public class HomepageController {

    private final OrganizzatoreService organizzatoreService;

    /**
     * Costruttore con iniezione di dipendenza per OrganizzatoreService.
     *
     * @param organizzatoreService Servizio per la gestione degli organizzatori.
     */
    @Autowired
    public HomepageController(OrganizzatoreService organizzatoreService) {
        this.organizzatoreService = organizzatoreService;
    }

    /**
     * Endpoint per recuperare le notifiche dell'utente.
     *
     * @return Stringa JSON contenente le notifiche.
     */
    @GetMapping("/notifiche")
    public String fetchNotifications() {
        return TemplateRestConsumer.callREST("getNotifications", null, true);
    }

    /**
     * Endpoint per recuperare i dati dell'utente loggato da mock o da un altro microservizio.
     * Se l'utente non è presente nel database, lo crea.
     * @return Stringa JSON contenente i dati dell'utente loggato.
     */
    @GetMapping("/utente")
    public String fetchLoggedUser() {
        String loggedUser = TemplateRestConsumer.callREST("getLoggedUser", null, true); // Chiamata al microservizio/mock per recuperare l'utente loggato
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Modulo per la gestione delle date
        Organizzatore organizzatore = null;
        try {
            organizzatore = objectMapper.readValue(loggedUser, Organizzatore.class); // Conversione della stringa JSON (da mock o microservizio) in oggetto Organizzatore
            organizzatoreService.findOrCreateOrganizzatore(organizzatore);
            System.out.println(loggedUser);
            return loggedUser;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "{}";
    }

    /**
     * Endpoint per recuperare gli eventi conclusi da mock o da un altro microservizio.
     *
     * @return Stringa JSON contenente gli eventi passati.
     */
    @GetMapping("/eventiConclusi")
    public String fetchPastEvents() {
        return TemplateRestConsumer.callREST("getPastEvents", null, true);
    }

    /**
     * Endpoint per recuperare gli eventi futuri da mock o da un altro microservizio.
     *
     * @return Stringa JSON contenente gli eventi futuri.
     */
    @GetMapping("/eventiFuturi")
    public String fetchNextEvents() {
        return TemplateRestConsumer.callREST("getNextEvents", null, true);
    }
}