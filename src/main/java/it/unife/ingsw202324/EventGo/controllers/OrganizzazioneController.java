package it.unife.ingsw202324.EventGo.controllers;

import it.unife.ingsw202324.EventGo.models.Organizzatore;
import it.unife.ingsw202324.EventGo.models.Organizzazione;
import it.unife.ingsw202324.EventGo.services.OrganizzazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/organizzazione")
public class OrganizzazioneController {

    private final OrganizzazioneService organizzazioneService;

    @Autowired
    public OrganizzazioneController(OrganizzazioneService organizzazioneService) {
        this.organizzazioneService = organizzazioneService;
    }

    @GetMapping("/getOrganizzazione")
    public Organizzazione getOrganizzazione(@RequestParam("id") Long idOrganizzazione) {
        return organizzazioneService.getOrganizzazione(idOrganizzazione);
    }

}
