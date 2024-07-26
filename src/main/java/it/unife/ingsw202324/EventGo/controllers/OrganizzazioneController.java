package it.unife.ingsw202324.EventGo.controllers;

import it.unife.ingsw202324.EventGo.exceptions.DuplicatedEntityException;
import it.unife.ingsw202324.EventGo.models.LinkOrganizzazione;
import it.unife.ingsw202324.EventGo.models.Organizzatore;
import it.unife.ingsw202324.EventGo.models.Organizzazione;
import it.unife.ingsw202324.EventGo.services.LinkOrganizzazioneService;
import it.unife.ingsw202324.EventGo.services.OrganizzazioneService;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * Controller REST per la gestione delle operazioni relative alle organizzazioni.
 */
@RestController
@RequestMapping("/api/organizzazione")
public class OrganizzazioneController {

    // Service che gestisce la logica delle organizzazioni
    private final OrganizzazioneService organizzazioneService;
    private final LinkOrganizzazioneService linkOrganizzazioneService;

    // Directory di upload foto, letta dal file di configurazione
    @Value("${app.upload.dir}")
    private String uploadDir;

    /**
     * Costruttore del controller con iniezione delle dipendenze.
     *
     * @param organizzazioneService servizio per la gestione delle organizzazioni
     * @param servletContext contesto del servlet per l'applicazione
     */
    @Autowired
    public OrganizzazioneController(OrganizzazioneService organizzazioneService, LinkOrganizzazioneService linkOrganizzazioneService, ServletContext servletContext) {
        this.organizzazioneService = organizzazioneService;
        this.linkOrganizzazioneService = linkOrganizzazioneService;
    }

    /**
     * Endpoint per ottenere i dettagli di una specifica organizzazione tramite ID.
     *
     * @param idOrganizzazione l'ID dell'organizzazione
     * @return l'organizzazione corrispondente all'ID
     */
    @GetMapping("/getOrganizzazione")
    public Organizzazione getOrganizzazione(@RequestParam("id") Long idOrganizzazione) {
        return organizzazioneService.getOrganizzazione(idOrganizzazione);
    }

    /**
     * Endpoint per creare una nuova organizzazione.
     *
     * @param organizzazione l'oggetto Organizzazione da creare
     * @param sito URL del sito web dell'organizzazione
     * @param instagram URL del profilo Instagram
     * @param twitter URL del profilo Twitter
     * @param facebook URL del profilo Facebook
     * @param linkedin URL del profilo LinkedIn
     * @param foto file dell'immagine dell'organizzazione
     * @param idAdmin ID dell'amministratore dell'organizzazione
     * @return ResponseEntity contenente l'ID della nuova organizzazione o un messaggio di errore
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

        /*
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
         */

        // Tenta di creare la nuova organizzazione
        try {
            Organizzazione newOrganizzazione = organizzazioneService.salvaOrganizzazione(organizzazione, Optional.empty(), idAdmin);

            return new ResponseEntity<>(newOrganizzazione.getId().toString(), HttpStatus.OK);
        } catch (Exception e) {
            // Gestione di errori generali durante la creazione dell'organizzazione
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

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
            @RequestParam(name= "deleted") String deletedPhoto,
            @RequestParam(value = "foto", required = false) MultipartFile foto) {

        try {
            System.out.println(organizzazioneModificata.getId() + " " + organizzazioneModificata.getNome());
            Organizzazione organizzazioneEsistente = organizzazioneService.getOrganizzazione(organizzazioneId);
            if (organizzazioneEsistente == null) {
                return new ResponseEntity<>("Organizzazione non trovata", HttpStatus.NOT_FOUND);
            }
            String urlFoto = organizzazioneEsistente.getUrlFoto();
            System.out.println(deletedPhoto);
            System.out.println("Foto: " + foto);
            organizzazioneEsistente = organizzazioneService.sanitizeOrganizzazione(organizzazioneModificata);

            linkOrganizzazioneService.modificaLinkOrganizzazione(organizzazioneEsistente, "Instagram", instagram);
            linkOrganizzazioneService.modificaLinkOrganizzazione(organizzazioneEsistente, "Twitter", twitter);
            linkOrganizzazioneService.modificaLinkOrganizzazione(organizzazioneEsistente, "Facebook", facebook);
            linkOrganizzazioneService.modificaLinkOrganizzazione(organizzazioneEsistente, "LinkedIn", linkedin);
            linkOrganizzazioneService.modificaLinkOrganizzazione(organizzazioneEsistente, "Sito", sito);

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
            }else{
                if(deletedPhoto != null && deletedPhoto.equals("true")){
                    System.out.println("Eliminata" + deletedPhoto);
                    organizzazioneEsistente.setUrlFoto(null);
                }
                else {
                    organizzazioneEsistente.setUrlFoto(urlFoto);
                }
            }

            Organizzazione organizzazioneAggiornata = organizzazioneService.salvaOrganizzazione(organizzazioneEsistente, Optional.of(organizzazioneModificata.getId()), idAdmin);
            return new ResponseEntity<>(organizzazioneAggiornata.getId().toString(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint per rimuovere un organizzatore dalla sua organizzazione tramite ID.
     *
     * @param idOrganizzatore l'ID dell'organizzatore da rimuovere
     * @return ResponseEntity contenente un messaggio di successo o errore
     */
    @GetMapping("/deleteOrganizzatore")
    public ResponseEntity<String> deleteOrganizzatore(@RequestParam("idOrganizzatore") Long idOrganizzatore) {
        try {
            organizzazioneService.deleteOrganizzatore(idOrganizzatore);
            return new ResponseEntity<>("Organizzatore eliminato", HttpStatus.OK);
        } catch (Exception e) {
            // Gestione di errori durante l'eliminazione dell'organizzatore
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Endpoint per eliminare un'organizzazione tramite ID.
     *
     * @param id l'ID dell'organizzazione da eliminare
     * @return ResponseEntity contenente un messaggio di successo o errore
     */
    @GetMapping("/deleteOrganizzazione")
    public ResponseEntity<String> deleteOrganizzazione(@RequestParam("id") Long id) {
        try {
            organizzazioneService.deleteOrganizzazione(id);
            return new ResponseEntity<>("Organizzazione eliminata", HttpStatus.OK);
        } catch (Exception e) {
            // Gestione di errori durante l'eliminazione dell'organizzazione
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint per aggiungere un organizzatore a un'organizzazione tramite email.
     *
     * @param email l'email dell'organizzatore da aggiungere
     * @param idOrganizzazione l'ID dell'organizzazione a cui aggiungere l'organizzatore
     * @return ResponseEntity contenente un messaggio di successo o errore
     */
    @PostMapping("/addOrganizzatore")
    public ResponseEntity<String> addOrganizzatore(@RequestParam("emailAddress") String email,
                                                   @RequestParam("idOrganizzazione") Long idOrganizzazione) {

        try {
            organizzazioneService.addOrganizzatore(email, idOrganizzazione);
            return new ResponseEntity<>("Organizzatore aggiunto con successo", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            // Gestione di errori di argomento non valido
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (DuplicatedEntityException e) {
            // Gestione di errore di entità duplicata
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            // Gestione di errori generali durante l'aggiunta dell'organizzatore
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    /**
     * Metodo di utilità per aggiungere un link social all'organizzazione.
     *
     * @param organizzazione l'organizzazione a cui aggiungere il link
     * @param nomeSocial il nome del social network
     * @param url l'URL del profilo social
     */
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


}
