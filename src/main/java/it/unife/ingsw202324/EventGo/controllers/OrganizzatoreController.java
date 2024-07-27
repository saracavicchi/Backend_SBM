package it.unife.ingsw202324.EventGo.controllers;

import it.unife.ingsw202324.EventGo.services.OrganizzatoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller per la gestione degli organizzatori.
 * Fornisce endpoint per operazioni relative agli organizzatori.
 */
@RestController
@RequestMapping("/api/organizzatore")
public class OrganizzatoreController {


    // Servizio per la gestione degli organizzatori
    private final OrganizzatoreService organizzatoreService;


    /**
     * Costruttore per l'iniezione del servizio di gestione degli organizzatori.
     *
     * @param organizzatoreService il servizio utilizzato per la gestione degli organizzatori.
     */
    @Autowired
    public OrganizzatoreController(OrganizzatoreService organizzatoreService) {
        this.organizzatoreService = organizzatoreService;
    }


    /**
     * Endpoint per verificare se un organizzatore ha un'organizzazione associata.
     *
     * @param idOrganizzatore l'ID dell'organizzatore da verificare.
     * @return l'ID dell'organizzazione associata all'organizzatore, oppure null se non esiste.
     */
    @GetMapping("/hasOrganizzazione")
    public Long hasOrganizzazione(@RequestParam("id") Long idOrganizzatore) {
        return organizzatoreService.hasOrganizzazione(idOrganizzatore);
    }

}
