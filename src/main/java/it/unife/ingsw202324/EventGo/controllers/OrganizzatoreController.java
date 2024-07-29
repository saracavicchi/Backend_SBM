package it.unife.ingsw202324.EventGo.controllers;

import it.unife.ingsw202324.EventGo.models.CarteOrganizzatore;
import it.unife.ingsw202324.EventGo.models.Organizzatore;
import it.unife.ingsw202324.EventGo.models.Organizzazione;
import it.unife.ingsw202324.EventGo.services.OrganizzatoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Optional;

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

    /**
     * Endpoint per ottenere un organizzatore.
     *
     * @param idOrganizzatore l'ID dell'organizzatore da ottenere.
     * @return l'organizzatore richiesto.
     */
    @GetMapping("/getOrganizzatore")
    public Organizzatore getOrganizzatore(@RequestParam("id") Long idOrganizzatore) {
        return organizzatoreService.getOrganizzatore(idOrganizzatore);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateOrganizzatore(@PathVariable("id") Long idOrganizzatore,
                                                      @ModelAttribute Organizzatore organizzatore,
                                                      @RequestParam(value = "sito", required = false) String sito,
                                                      @RequestParam(value = "instagram", required = false) String instagram,
                                                      @RequestParam(value = "facebook", required = false) String facebook,
                                                      @RequestParam(value = "twitter", required = false) String twitter,
                                                      @RequestParam(value = "linkedin", required = false) String linkedin,
                                                      @RequestParam(value = "idCarta1", required = false) Long idCarta1,
                                                      @RequestParam(value = "numero1", required = false) String numero1,
                                                      @RequestParam(value = "dataScadenza1", required = false) String dataScadenza1,
                                                      @RequestParam(value = "cvv1", required = false) String cvv1,
                                                      @RequestParam(value = "nome1", required = false) String nome1,
                                                      @RequestParam(value = "cognome1", required = false) String cognome1,
                                                      @RequestParam(value = "idCarta2", required = false) Long idCarta2,
                                                      @RequestParam(value = "numero2", required = false) String numero2,
                                                      @RequestParam(value = "dataScadenza2", required = false) String dataScadenza2,
                                                      @RequestParam(value = "cvv2", required = false) String cvv2,
                                                      @RequestParam(value = "nome2", required = false) String nome2,
                                                      @RequestParam(value = "cognome2", required = false) String cognome2,
                                                      @RequestParam(value = "idCarta3", required = false) Long idCarta3,
                                                      @RequestParam(value = "numero3", required = false) String numero3,
                                                      @RequestParam(value = "dataScadenza3", required = false) String dataScadenza3,
                                                      @RequestParam(value = "cvv3", required = false) String cvv3,
                                                      @RequestParam(value = "nome3", required = false) String nome3,
                                                      @RequestParam(value = "cognome3", required = false) String cognome3,
                                                      @RequestParam(name = "deleted") String deletedPhoto,
                                                      @RequestParam(value = "foto", required = false) MultipartFile foto) {


        try {


            organizzatore.setId(idOrganizzatore);

            CarteOrganizzatore carta1 = new CarteOrganizzatore(idCarta1, numero1, cvv1, nome1, cognome1, organizzatore);
            if (!dataScadenza1.isEmpty()) {
                carta1.setDataScadenza(LocalDate.parse(dataScadenza1));
            }
            CarteOrganizzatore carta2 = new CarteOrganizzatore(idCarta2, numero2, cvv2, nome2, cognome2, organizzatore);
            if (!dataScadenza2.isEmpty()) {
                carta2.setDataScadenza(LocalDate.parse(dataScadenza2));
            }
            CarteOrganizzatore carta3 = new CarteOrganizzatore(idCarta3, numero3, cvv3, nome3, cognome3, organizzatore);
            if (!dataScadenza3.isEmpty()) {
                carta3.setDataScadenza(LocalDate.parse(dataScadenza3));
            }

            // Recupera l'organizzatore esistente
            Organizzatore organizzatoreEsistente = organizzatoreService.getOrganizzatore(idOrganizzatore);
            if (organizzatoreEsistente == null) {
                return new ResponseEntity<>("Organizzatore non trovato", HttpStatus.NOT_FOUND);
            }

            String urlFoto = organizzatoreEsistente.getUrlFoto();

            // Aggiorna l'organizzatore con i nuovi dati
            Organizzatore organizzatoreAggiornato = organizzatoreService.modificaOrganizzatore(organizzatore, Optional.of(foto), sito, instagram, facebook, twitter, linkedin, Optional.ofNullable(urlFoto), Optional.ofNullable(deletedPhoto), carta1, carta2, carta3);
            return new ResponseEntity<>("Organizzatore aggiornato con successo", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            // Gestione di errori generali durante l'aggiornamento dell'organizzatore
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

}
