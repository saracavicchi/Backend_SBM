package it.unife.ingsw202324.EventGo.controllers;

import it.unife.ingsw202324.EventGo.services.OrganizzatoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/organizzatore")
public class OrganizzatoreController {

    private final OrganizzatoreService organizzatoreService;

    @Autowired
    public OrganizzatoreController(OrganizzatoreService organizzatoreService) {
        this.organizzatoreService = organizzatoreService;
    }

    @GetMapping("/hasOrganizzazione")
    public Long hasOrganizzazione(@RequestParam("id") Long idOrganizzatore) {
        return organizzatoreService.hasOrganizzazione(idOrganizzatore);
    }

}
