package it.unife.ingsw202324.EventGo.controllers;

import it.unife.ingsw202324.EventGo.models.Organizzatore;
import it.unife.ingsw202324.EventGo.services.OrganizzatoreService;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;


@RestController
@RequestMapping("/api/organizzatore")
public class OrganizzatoreController {

    private final OrganizzatoreService organizzatoreService;

    @Autowired
    public OrganizzatoreController(OrganizzatoreService organizzatoreService) {
        this.organizzatoreService = organizzatoreService;
    }

    // Richieste PUT
    @PutMapping("/aggiorna/{id}")
    public ResponseEntity<String> updateOrganizzatore(
            @PathVariable("id") Long id,
            @RequestBody Organizzatore aggiornamenti) {
        try {
            organizzatoreService.updateOrganizzatore(id, aggiornamenti);
            return new ResponseEntity<>("Organizzatore aggiornato con successo", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
