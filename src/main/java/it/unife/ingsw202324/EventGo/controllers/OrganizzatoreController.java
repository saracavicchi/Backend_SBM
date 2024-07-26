package it.unife.ingsw202324.EventGo.controllers;

import it.unife.ingsw202324.EventGo.services.OrganizzatoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller REST per la gestione delle operazioni relative agli organizzatori.
 */
@RestController
@RequestMapping("/api/organizzatore")
public class OrganizzatoreController {

    // Service che gestisce la logica degli organizzatori
    private final OrganizzatoreService organizzatoreService;

    /**
     * Costruttore del controller con iniezione delle dipendenze.
     *
     * @param organizzatoreService servizio per la gestione degli organizzatori
     */
    @Autowired
    public OrganizzatoreController(OrganizzatoreService organizzatoreService) {
        this.organizzatoreService = organizzatoreService;
    }

    /**
     * Endpoint per verificare se un organizzatore appartiene a un'organizzazione.
     *
     * @param idOrganizzatore l'ID dell'organizzatore
     * @return l'ID dell'organizzazione a cui appartiene l'organizzatore, o null se non ne appartiene a nessuna
     */
    @GetMapping("/hasOrganizzazione")
    public Long hasOrganizzazione(@RequestParam("id") Long idOrganizzatore) {
        return organizzatoreService.hasOrganizzazione(idOrganizzatore);
    }

}
