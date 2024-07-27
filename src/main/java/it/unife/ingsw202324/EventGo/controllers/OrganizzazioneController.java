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
 * Controller per la gestione delle organizzazioni.
 * Fornisce endpoint per operazioni di creazione, aggiornamento, eliminazione e gestione degli organizzatori associati.
 */
@RestController
@RequestMapping("/api/organizzazione")
public class OrganizzazioneController {

    // Servizio per la gestione delle organizzazioni
    private final OrganizzazioneService organizzazioneService;
    // Servizio per l'invio di email
    private final EmailService emailService;


    /**
     * Costruttore per l'iniezione dei servizi di gestione delle organizzazioni e delle email.
     *
     * @param organizzazioneService il servizio utilizzato per la gestione delle organizzazioni.
     * @param emailService il servizio utilizzato per l'invio delle email.
     */
    @Autowired
    public OrganizzazioneController(OrganizzazioneService organizzazioneService, EmailService emailService) {
        this.organizzazioneService = organizzazioneService;
        this.emailService = emailService;
    }

    /**
     * Endpoint per recuperare una specifica organizzazione.
     *
     * @param idOrganizzazione l'ID dell'organizzazione da recuperare.
     * @return l'organizzazione richiesta.
     */
    @GetMapping("/getOrganizzazione")
    public Organizzazione getOrganizzazione(@RequestParam("id") Long idOrganizzazione) {
        return organizzazioneService.getOrganizzazione(idOrganizzazione);
    }


    /*@PostMapping("/creaOrganizzazione")
    public ResponseEntity<String> creaOrganizzazione(@ModelAttribute Organizzazione organizzazione,
                                                     @RequestParam(value = "sito", required = false) String sito,
                                                     @RequestParam(value = "instagram", required = false) String instagram,
                                                     @RequestParam(value = "twitter", required = false) String twitter,
                                                     @RequestParam(value = "facebook", required = false) String facebook,
                                                     @RequestParam(value = "linkedin", required = false) String linkedin,
                                                     @RequestParam(value = "foto", required = false) MultipartFile foto,
                                                     @RequestParam("idAdmin") Long idAdmin) {

        // Sanitizza l'oggetto organizzazione (null al posto dei campi vuoti)
        organizzazione = organizzazioneService.sanitizeOrganizzazione(organizzazione);

        // Aggiunge i link social all'organizzazione
        linkOrganizzazioneService.aggiungiLinkOrganizzazione(organizzazione, "Instagram", instagram);
        linkOrganizzazioneService.aggiungiLinkOrganizzazione(organizzazione, "Twitter", twitter);
        linkOrganizzazioneService.aggiungiLinkOrganizzazione(organizzazione, "Facebook", facebook);
        linkOrganizzazioneService.aggiungiLinkOrganizzazione(organizzazione, "LinkedIn", linkedin);
        linkOrganizzazioneService.aggiungiLinkOrganizzazione(organizzazione, "Sito", sito);

        // Imposta l'admin dell'organizzazione
        Organizzatore admin = new Organizzatore();
        admin.setId(idAdmin);
        organizzazione.setAdmin(admin);

        // Gestione del caricamento dell'immagine
        if (foto != null && !foto.isEmpty()) {

            try {

                // Genera un nome unico per il file dell'immagine
                String fileName = UUID.randomUUID().toString() + "_" + foto.getOriginalFilename();
                Path directory = Paths.get(uploadDir.replace("file:./", "")).resolve("organizzazioniImg");
                Files.createDirectories(directory);
                Path savePath = directory.resolve(fileName);

                // Salva il file nel percorso specificato
                try (InputStream inputStream = foto.getInputStream()) {
                    Files.copy(inputStream, savePath, StandardCopyOption.REPLACE_EXISTING);
                }
                organizzazione.setUrlFoto(fileName);

            } catch (IOException e) {
                // Gestione dell'errore nel salvataggio dell'immagine
                System.out.println(e.getMessage());
                return new ResponseEntity<>("Errore nel salvataggio dell'immagine", HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }


        try {

            Path directory = Paths.get(uploadDir.replace("file:./", "")).resolve("organizzazioniImg");
            Path imagePath = directory.resolve(organizzazione.getUrlFoto());

            byte[] imageBytes = Files.readAllBytes(imagePath);

            String contentType = servletContext.getMimeType(imagePath.toString());
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(imageBytes);

        } catch (IOException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>("Errore nel caricamento dell'immagine", HttpStatus.INTERNAL_SERVER_ERROR);
        }


        // Tenta di creare la nuova organizzazione
        try {
            Organizzazione newOrganizzazione = organizzazioneService.salvaOrganizzazione(organizzazione, Optional.empty(), idAdmin);

            return new ResponseEntity<>(newOrganizzazione.getId().toString(), HttpStatus.OK);
        } catch (Exception e) {
            // Gestione di errori generali durante la creazione dell'organizzazione
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }*/

    /**
     * Endpoint per creare una nuova organizzazione.
     *
     * @param organizzazione l'organizzazione da creare.
     * @param sito il sito web dell'organizzazione (opzionale).
     * @param instagram il profilo Instagram dell'organizzazione (opzionale).
     * @param twitter il profilo Twitter dell'organizzazione (opzionale).
     * @param facebook il profilo Facebook dell'organizzazione (opzionale).
     * @param linkedin il profilo LinkedIn dell'organizzazione (opzionale).
     * @param foto la foto dell'organizzazione (opzionale).
     * @param idAdmin l'ID dell'amministratore che crea l'organizzazione.
     * @return la ResponseEntity contenente l'ID della nuova organizzazione o un codice di errore.
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
            // Tenta di creare la nuova organizzazione e restituisce l'ID nella ResponseEntity
            Organizzazione newOrganizzazione = organizzazioneService.creaMofificaOrganizzazione(organizzazione, Optional.empty(), idAdmin, Optional.ofNullable(foto), sito, instagram, facebook, twitter, linkedin, Optional.empty(), Optional.empty());
            return new ResponseEntity<>(newOrganizzazione.getId().toString(), HttpStatus.OK);
        } catch (Exception e) {
            // Gestione di errori generali durante la creazione dell'organizzazione
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /*
    @PutMapping("/update/{id}")
    public ResponseEntity<String> aggiornaOrganizzazione(
            @PathVariable("id") Long organizzazioneId,
            @ModelAttribute Organizzazione organizzazioneModificata,
            @RequestParam(value = "sito", required = false) String sito,
            @RequestParam(value = "instagram", required = false) String instagram,
            @RequestParam(value = "twitter", required = false) String twitter,
            @RequestParam(value = "facebook", required = false) String facebook,
            @RequestParam(value = "linkedin", required = false) String linkedin,
            @RequestParam("idAdmin") Long idAdmin,
            @RequestParam(name = "deleted") String deletedPhoto,
            @RequestParam(value = "foto", required = false) MultipartFile foto) {

        try {
            // Log dei dettagli dell'organizzazione modificata
            System.out.println(organizzazioneModificata.getId() + " " + organizzazioneModificata.getNome());

            // Recupera l'organizzazione esistente
            Organizzazione organizzazioneEsistente = organizzazioneService.getOrganizzazione(organizzazioneId);
            if (organizzazioneEsistente == null) {
                return new ResponseEntity<>("Organizzazione non trovata", HttpStatus.NOT_FOUND);
            }

            // Salva l'URL della foto esistente
            String urlFoto = organizzazioneEsistente.getUrlFoto();
            System.out.println(deletedPhoto);
            System.out.println("Foto: " + foto);

            // Sanitizza l'oggetto organizzazione modificata
            organizzazioneEsistente = organizzazioneService.sanitizeOrganizzazione(organizzazioneModificata);

            // Modifica i link social dell'organizzazione
            linkOrganizzazioneService.modificaLinkOrganizzazione(organizzazioneEsistente, "Instagram", instagram);
            linkOrganizzazioneService.modificaLinkOrganizzazione(organizzazioneEsistente, "Twitter", twitter);
            linkOrganizzazioneService.modificaLinkOrganizzazione(organizzazioneEsistente, "Facebook", facebook);
            linkOrganizzazioneService.modificaLinkOrganizzazione(organizzazioneEsistente, "LinkedIn", linkedin);
            linkOrganizzazioneService.modificaLinkOrganizzazione(organizzazioneEsistente, "Sito", sito);

            // Gestione del caricamento dell'immagine
            if (foto != null && !foto.isEmpty()) {
                System.out.println("Foto non vuota");
                String fileName = UUID.randomUUID().toString() + "_" + foto.getOriginalFilename();
                Path directory = Paths.get(uploadDir.replace("file:./", "")).resolve("organizzazioniImg");
                Files.createDirectories(directory);
                Path savePath = directory.resolve(fileName);
                try (InputStream inputStream = foto.getInputStream()) {
                    Files.copy(inputStream, savePath, StandardCopyOption.REPLACE_EXISTING);
                }
                organizzazioneEsistente.setUrlFoto(fileName);
            } else {
                if (deletedPhoto != null && deletedPhoto.equals("true")) {
                    System.out.println("Eliminata" + deletedPhoto);
                    organizzazioneEsistente.setUrlFoto(null);
                } else {
                    organizzazioneEsistente.setUrlFoto(urlFoto);
                }
            }

            // Salva l'organizzazione aggiornata
            Organizzazione organizzazioneAggiornata = organizzazioneService.salvaOrganizzazione(organizzazioneEsistente, Optional.of(organizzazioneModificata.getId()), idAdmin);
            return new ResponseEntity<>(organizzazioneAggiornata.getId().toString(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/


    /**
     * Endpoint per aggiornare una specifica organizzazione.
     *
     * @param organizzazioneId l'ID dell'organizzazione da aggiornare.
     * @param organizzazioneModificata l'organizzazione con i nuovi dati.
     * @param sito il sito web dell'organizzazione.
     * @param instagram il profilo Instagram dell'organizzazione (opzionale).
     * @param facebook il profilo Facebook dell'organizzazione (opzionale).
     * @param twitter il profilo Twitter dell'organizzazione (opzionale).
     * @param linkedin il profilo LinkedIn dell'organizzazione (opzionale).
     * @param idAdmin l'ID dell'amministratore che aggiorna l'organizzazione.
     * @param deletedPhoto l'URL della foto da eliminare (opzionale).
     * @param foto la nuova foto dell'organizzazione (opzionale).
     * @return la ResponseEntity contenente l'ID dell'organizzazione aggiornata o un codice di errore.
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

            // Recupera l'organizzazione esistente
            Organizzazione organizzazioneEsistente = organizzazioneService.getOrganizzazione(organizzazioneId);
            if (organizzazioneEsistente == null) {
                return new ResponseEntity<>("Organizzazione non trovata", HttpStatus.NOT_FOUND);
            }

            String urlFoto = organizzazioneEsistente.getUrlFoto();

            // Aggiorna l'organizzazione con i nuovi dati
            Organizzazione organizzazioneAggiornata = organizzazioneService.creaMofificaOrganizzazione(organizzazioneModificata, Optional.of(organizzazioneModificata.getId()), idAdmin, Optional.of(foto), sito, instagram, facebook, twitter, linkedin, Optional.ofNullable(urlFoto), Optional.ofNullable(deletedPhoto));
            return new ResponseEntity<>(organizzazioneAggiornata.getId().toString(), HttpStatus.OK);
        } catch (Exception e) {
            // Gestione di errori generali durante l'aggiornamento dell'organizzazione
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Endpoint per rimuovere un organizzatore da un'organizzazione e inviare una email di notifica.
     *
     * @param idOrganizzatore l'ID dell'organizzatore da eliminare.
     * @param idOrganizzazione l'ID dell'organizzazione da cui eliminare l'organizzatore.
     * @return la ResponseEntity contenente un messaggio di successo o un codice di errore.
     */
    @GetMapping("/deleteOrganizzatore")
    public ResponseEntity<String> deleteOrganizzatore(@RequestParam("idOrganizzatore") Long idOrganizzatore,
                                                      @RequestParam("idOrganizzazione") Long idOrganizzazione) {
        try {
            // Rimuove l'organizzatore e invia una email di notifica, ritorna una ResponseEntity con un messaggio di successo
            organizzazioneService.deleteOrganizzatore(idOrganizzatore);
            emailService.sendRemEmail(idOrganizzatore, idOrganizzazione);
            return new ResponseEntity<>("Organizzatore eliminato", HttpStatus.OK);
        } catch(MailAuthenticationException e) {
            // Gestione dell'errore nell'invio dell'email di notifica
            return new ResponseEntity<>("Organizzatore rimosso con successo ma impossibile inviare email di notifica", HttpStatus.OK);
        } catch (Exception e) {
            // Gestione di errori generali durante l'eliminazione dell'organizzatore
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Endpoint per eliminare una specifica organizzazione.
     *
     * @param id l'ID dell'organizzazione da eliminare.
     * @return la ResponseEntity contenente un messaggio di successo o un codice di errore.
     */
    @GetMapping("/deleteOrganizzazione")
    public ResponseEntity<String> deleteOrganizzazione(@RequestParam("id") Long id) {
        try {
            // Elimina l'organizzazione e ritorna una ResponseEntity con un messaggio di successo
            organizzazioneService.deleteOrganizzazione(id);
            return new ResponseEntity<>("Organizzazione eliminata", HttpStatus.OK);
        } catch (Exception e) {
            // Gestione di errori generali durante l'eliminazione dell'organizzazione
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint per aggiungere un organizzatore a un'organizzazione e inviare una email di notifica.
     *
     * @param email l'email dell'organizzatore da aggiungere.
     * @param idOrganizzazione l'ID dell'organizzazione a cui aggiungere l'organizzatore.
     * @return la ResponseEntity contenente un messaggio di successo o un codice di errore.
     */
    @PostMapping("/addOrganizzatore")
    public ResponseEntity<String> addOrganizzatore(@RequestParam("emailAddress") String email,
                                                   @RequestParam("idOrganizzazione") Long idOrganizzazione) {

        try {
            // Aggiunge l'organizzatore e invia una email di notifica, ritorna una ResponseEntity con un messaggio di successo
            organizzazioneService.addOrganizzatore(email, idOrganizzazione);
            emailService.sendAddEmail(email, idOrganizzazione);
            return new ResponseEntity<>("Organizzatore aggiunto con successo", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            // Gestione dell'errore se l'organizzatore non esiste
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (DuplicatedEntityException e) {
            // Gestione dell'errore se l'organizzatore fa già parte di un'organizzazione
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (MailAuthenticationException e) {
            // Gestione dell'errore nell'invio dell'email di notifica
            return new ResponseEntity<>("Organizzatore aggiunto con successo ma impossibile inviare email di notifica", HttpStatus.OK);
        }
        catch (Exception e) {
            // Gestione di errori generali durante l'aggiunta dell'organizzatore
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /*
    private void aggiungiLinkOrganizzazione(Organizzazione organizzazione, String nomeSocial, String url) {
        if (url.isEmpty()) {
            return;
        }
        LinkOrganizzazione link = new LinkOrganizzazione();
        link.setOrganizzazione(organizzazione);
        link.setNomeSocial(nomeSocial);
        link.setUrl(url);
        organizzazione.getLink().add(link);
    }

     */


}
