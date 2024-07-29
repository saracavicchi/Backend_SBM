package it.unife.ingsw202324.EventGo.controllers;

import it.unife.ingsw202324.EventGo.models.CarteOrganizzatore;
import it.unife.ingsw202324.EventGo.models.Organizzatore;
import it.unife.ingsw202324.EventGo.services.OrganizzatoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Controller REST per la gestione degli organizzatori.
 * Fornisce endpoint per recuperare, verificare e aggiornare gli organizzatori.
 */
@RestController
@RequestMapping("/api/organizzatore")
public class OrganizzatoreController {


    private final OrganizzatoreService organizzatoreService;

    /**
     * Costruttore per l'iniezione del servizio OrganizzatoreService.
     *
     * @param organizzatoreService il servizio per la gestione degli organizzatori
     */
    @Autowired
    public OrganizzatoreController(OrganizzatoreService organizzatoreService) {
        this.organizzatoreService = organizzatoreService;
    }


    /**
     * Endpoint per verificare se un organizzatore ha un'organizzazione associata.
     *
     * @param idOrganizzatore l'ID dell'organizzatore
     * @return l'ID dell'organizzazione associata, se presente
     */
    @GetMapping("/hasOrganizzazione")
    public Long hasOrganizzazione(@RequestParam("id") Long idOrganizzatore) {
        return organizzatoreService.hasOrganizzazione(idOrganizzatore);
    }


    /**
     * Endpoint per recuperare le informazioni di un organizzatore.
     *
     * @param idOrganizzatore l'ID dell'organizzatore
     * @return l'organizzatore corrispondente all'ID fornito
     */
    @GetMapping("/getOrganizzatore")
    public Organizzatore getOrganizzatore(@RequestParam("id") Long idOrganizzatore) {
        return organizzatoreService.getOrganizzatore(idOrganizzatore);
    }


    /**
     * Endpoint per aggiornare le informazioni di un organizzatore.
     *
     * @param idOrganizzatore l'ID dell'organizzatore da aggiornare
     * @param organizzatore l'oggetto Organizzatore con le nuove informazioni
     * @param sito l'URL del sito web dell'organizzatore
     * @param instagram l'URL del profilo Instagram dell'organizzatore
     * @param facebook l'URL del profilo Facebook dell'organizzatore
     * @param twitter l'URL del profilo Twitter dell'organizzatore
     * @param linkedin l'URL del profilo LinkedIn dell'organizzatore
     * @param idCarta1 l'ID della prima carta di credito
     * @param numero1 il numero della prima carta di credito
     * @param dataScadenza1 la data di scadenza della prima carta di credito
     * @param cvv1 il CVV della prima carta di credito
     * @param nome1 il nome sulla prima carta di credito
     * @param cognome1 il cognome sulla prima carta di credito
     * @param idCarta2 l'ID della seconda carta di credito
     * @param numero2 il numero della seconda carta di credito
     * @param dataScadenza2 la data di scadenza della seconda carta di credito
     * @param cvv2 il CVV della seconda carta di credito
     * @param nome2 il nome sulla seconda carta di credito
     * @param cognome2 il cognome sulla seconda carta di credito
     * @param idCarta3 l'ID della terza carta di credito
     * @param numero3 il numero della terza carta di credito
     * @param dataScadenza3 la data di scadenza della terza carta di credito
     * @param cvv3 il CVV della terza carta di credito
     * @param nome3 il nome sulla terza carta di credito
     * @param cognome3 il cognome sulla terza carta di credito
     * @param deletedPhoto flag che indica se la foto dell'organizzatore deve essere cancellata
     * @param foto il file della nuova foto dell'organizzatore
     * @return ResponseEntity con il risultato dell'operazione di aggiornamento
     */
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

            // Imposta l'ID dell'organizzatore
            organizzatore.setId(idOrganizzatore);

            // Crea e popola gli oggetti CarteOrganizzatore per ciascuna delle carte di credito
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

            // Recupera l'organizzatore esistente dal servizio
            Organizzatore organizzatoreEsistente = organizzatoreService.getOrganizzatore(idOrganizzatore);
            if (organizzatoreEsistente == null) {
                return new ResponseEntity<>("Organizzatore non trovato", HttpStatus.NOT_FOUND);
            }

            // Imposta l'organizzazione esistente nell'oggetto organizzatore
            organizzatore.setOrganizzazione(organizzatoreEsistente.getOrganizzazione());

            // Ottiene l'URL della foto esistente
            String urlFoto = organizzatoreEsistente.getUrlFoto();

            // Modifica l'organizzatore esistente con le nuove informazioni
            Organizzatore organizzatoreAggiornato = organizzatoreService.modificaOrganizzatore(organizzatore, Optional.of(foto), sito, instagram, facebook, twitter, linkedin, Optional.ofNullable(urlFoto), Optional.ofNullable(deletedPhoto), carta1, carta2, carta3);
            return new ResponseEntity<>("Organizzatore aggiornato con successo", HttpStatus.OK);
        } catch (Exception e) {
            // Gestisce eventuali eccezioni durante l'aggiornamento dell'organizzatore
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

}
