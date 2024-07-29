package it.unife.ingsw202324.EventGo.controllers;

import it.unife.ingsw202324.EventGo.exceptions.DuplicatedEntityException;
import it.unife.ingsw202324.EventGo.models.Organizzazione;
import it.unife.ingsw202324.EventGo.services.EmailService;
import it.unife.ingsw202324.EventGo.services.OrganizzazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

/**
 * Controller REST per la gestione delle organizzazioni.
 * Fornisce endpoint per creare, aggiornare, eliminare e gestire organizzatori all'interno delle organizzazioni.
 */
@RestController
@RequestMapping("/api/organizzazione")
public class OrganizzazioneController {


    private final OrganizzazioneService organizzazioneService;
    private final EmailService emailService;

    /**
     * Costruttore per l'iniezione dei servizi OrganizzazioneService ed EmailService.
     *
     * @param organizzazioneService il servizio per la gestione delle organizzazioni
     * @param emailService il servizio per la gestione delle email
     */
    @Autowired
    public OrganizzazioneController(OrganizzazioneService organizzazioneService, EmailService emailService) {
        this.organizzazioneService = organizzazioneService;
        this.emailService = emailService;
    }


    /**
     * Endpoint per recuperare le informazioni di un'organizzazione.
     *
     * @param idOrganizzazione l'ID dell'organizzazione
     * @return l'organizzazione corrispondente all'ID fornito
     */
    @GetMapping("/getOrganizzazione")
    public Organizzazione getOrganizzazione(@RequestParam("id") Long idOrganizzazione) {
        return organizzazioneService.getOrganizzazione(idOrganizzazione);
    }


    /**
     * Endpoint per creare una nuova organizzazione.
     *
     * @param organizzazione l'oggetto Organizzazione da creare
     * @param sito l'URL del sito web dell'organizzazione
     * @param instagram l'URL del profilo Instagram dell'organizzazione
     * @param twitter l'URL del profilo Twitter dell'organizzazione
     * @param facebook l'URL del profilo Facebook dell'organizzazione
     * @param linkedin l'URL del profilo LinkedIn dell'organizzazione
     * @param foto il file della foto dell'organizzazione
     * @param idAdmin l'ID dell'amministratore dell'organizzazione
     * @return ResponseEntity con il risultato dell'operazione di creazione
     */
    @PostMapping("/creaOrganizzazione")
    public ResponseEntity<String> creaOrganizzazione(@ModelAttribute Organizzazione organizzazione,
                                                     @RequestParam(value = "sito", required = false) String sito,
                                                     @RequestParam(value = "instagram", required = false) String instagram,
                                                     @RequestParam(value = "twitter", required = false) String twitter,
                                                     @RequestParam(value = "facebook", required = false) String facebook,
                                                     @RequestParam(value = "linkedin", required = false) String linkedin,
                                                     @RequestParam(value = "foto", required = false) MultipartFile foto,
                                                     @RequestParam("idAdmin") Long idAdmin) {

        try {
            // Crea una nuova organizzazione utilizzando il servizio
            Organizzazione newOrganizzazione = organizzazioneService.creaMofificaOrganizzazione(organizzazione, Optional.empty(), idAdmin, Optional.ofNullable(foto), sito, instagram, facebook, twitter, linkedin, Optional.empty(), Optional.empty());
            return new ResponseEntity<>(newOrganizzazione.getId().toString(), HttpStatus.OK);
        } catch (Exception e) {
            // Gestisce eventuali eccezioni durante la creazione dell'organizzazione
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    /**
     * Endpoint per aggiornare le informazioni di un'organizzazione esistente.
     *
     * @param organizzazioneId l'ID dell'organizzazione da aggiornare
     * @param organizzazioneModificata l'oggetto Organizzazione con le nuove informazioni
     * @param sito l'URL del sito web dell'organizzazione
     * @param instagram l'URL del profilo Instagram dell'organizzazione
     * @param facebook l'URL del profilo Facebook dell'organizzazione
     * @param twitter l'URL del profilo Twitter dell'organizzazione
     * @param linkedin l'URL del profilo LinkedIn dell'organizzazione
     * @param idAdmin l'ID dell'amministratore dell'organizzazione
     * @param deletedPhoto flag che indica se la foto dell'organizzazione deve essere cancellata
     * @param foto il file della nuova foto dell'organizzazione
     * @return ResponseEntity con il risultato dell'operazione di aggiornamento
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<String> aggiornaOrganizzazione(
            @PathVariable("id") Long organizzazioneId,
            @ModelAttribute Organizzazione organizzazioneModificata,
            @RequestParam(value = "sito") String sito,
            @RequestParam(value = "instagram", required = false) String instagram,
            @RequestParam(value = "facebook", required = false) String facebook,
            @RequestParam(value = "twitter", required = false) String twitter,
            @RequestParam(value = "linkedin", required = false) String linkedin,
            @RequestParam("idAdmin") Long idAdmin,
            @RequestParam(name = "deleted") String deletedPhoto,
            @RequestParam(value = "foto", required = false) MultipartFile foto) {

        try {
            // Recupera l'organizzazione esistente dal servizio
            Organizzazione organizzazioneEsistente = organizzazioneService.getOrganizzazione(organizzazioneId);
            if (organizzazioneEsistente == null) {
                return new ResponseEntity<>("Organizzazione non trovata", HttpStatus.NOT_FOUND);
            }

            // Ottiene l'URL della foto esistente
            String urlFoto = organizzazioneEsistente.getUrlFoto();

            // Modifica l'organizzazione esistente con le nuove informazioni
            Organizzazione organizzazioneAggiornata = organizzazioneService.creaMofificaOrganizzazione(organizzazioneModificata, Optional.of(organizzazioneModificata.getId()), idAdmin, Optional.of(foto), sito, instagram, facebook, twitter, linkedin, Optional.ofNullable(urlFoto), Optional.ofNullable(deletedPhoto));
            return new ResponseEntity<>(organizzazioneAggiornata.getId().toString(), HttpStatus.OK);
        } catch (Exception e) {
            // Gestisce eventuali eccezioni durante l'aggiornamento dell'organizzazione
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Endpoint per rimuovere un organizzatore da un'organizzazione.
     *
     * @param idOrganizzatore l'ID dell'organizzatore da rimuovere
     * @param idOrganizzazione l'ID dell'organizzazione da cui rimuovere l'organizzatore
     * @return ResponseEntity con il risultato dell'operazione di rimozione
     */
    @GetMapping("/deleteOrganizzatore")
    public ResponseEntity<String> deleteOrganizzatore(@RequestParam("idOrganizzatore") Long idOrganizzatore,
                                                      @RequestParam("idOrganizzazione") Long idOrganizzazione) {
        try {
            // Rimuove l'organizzatore utilizzando il servizio
            organizzazioneService.deleteOrganizzatore(idOrganizzatore);
            // Invia un'email di notifica di rimozione
            emailService.sendRemEmail(idOrganizzatore, idOrganizzazione);
            return new ResponseEntity<>("Organizzatore eliminato", HttpStatus.OK);
        } catch(MailAuthenticationException e) {
            // Gestisce l'eccezione di autenticazione dell'email, ma l'organizzatore è stato eliminato con successo
            return new ResponseEntity<>("Organizzatore rimosso con successo ma impossibile inviare email di notifica", HttpStatus.OK);
        } catch (Exception e) {
            // Gestisce eventuali altre eccezioni durante l'eliminazione dell'organizzatore
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Endpoint per eliminare un'organizzazione.
     *
     * @param id l'ID dell'organizzazione da eliminare
     * @return ResponseEntity con il risultato dell'operazione di eliminazione
     */
    @GetMapping("/deleteOrganizzazione")
    public ResponseEntity<String> deleteOrganizzazione(@RequestParam("id") Long id) {
        try {
            // Elimina l'organizzazione utilizzando il servizio
            organizzazioneService.deleteOrganizzazione(id);
            return new ResponseEntity<>("Organizzazione eliminata", HttpStatus.OK);
        } catch (Exception e) {
            // Gestisce eventuali eccezioni durante l'eliminazione dell'organizzazione
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint per aggiungere un organizzatore a un'organizzazione.
     *
     * @param email l'indirizzo email dell'organizzatore da aggiungere
     * @param idOrganizzazione l'ID dell'organizzazione a cui aggiungere l'organizzatore
     * @return ResponseEntity con il risultato dell'operazione di aggiunta
     */
    @PostMapping("/addOrganizzatore")
    public ResponseEntity<String> addOrganizzatore(@RequestParam("emailAddress") String email,
                                                   @RequestParam("idOrganizzazione") Long idOrganizzazione) {

        try {
            // Aggiunge l'organizzatore utilizzando il servizio
            organizzazioneService.addOrganizzatore(email, idOrganizzazione);
            // Invia un'email di notifica di aggiunta
            emailService.sendAddEmail(email, idOrganizzazione);
            return new ResponseEntity<>("Organizzatore aggiunto con successo", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            // Gestisce l'eccezione di argomento non valido
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (DuplicatedEntityException e) {
            // Gestisce l'eccezione di entità duplicata
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (MailAuthenticationException e) {
            // Gestisce l'eccezione di autenticazione dell'email, ma l'organizzatore è stato aggiunto con successo
            return new ResponseEntity<>("Organizzatore aggiunto con successo ma impossibile inviare email di notifica", HttpStatus.OK);
        }
        catch (Exception e) {
            // Gestisce eventuali altre eccezioni durante l'aggiunta dell'organizzatore
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
