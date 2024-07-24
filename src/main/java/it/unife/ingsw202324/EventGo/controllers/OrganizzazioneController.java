package it.unife.ingsw202324.EventGo.controllers;

import it.unife.ingsw202324.EventGo.models.LinkOrganizzazione;
import it.unife.ingsw202324.EventGo.models.Organizzatore;
import it.unife.ingsw202324.EventGo.models.Organizzazione;
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
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/organizzazione")
public class OrganizzazioneController {

    private final OrganizzazioneService organizzazioneService;

    @Value("${app.upload.dir}")
    private String uploadDir;

    @Autowired
    public OrganizzazioneController(OrganizzazioneService organizzazioneService, ServletContext servletContext) {
        this.organizzazioneService = organizzazioneService;
    }

    @GetMapping("/getOrganizzazione")
    public Organizzazione getOrganizzazione(@RequestParam("id") Long idOrganizzazione) {
        return organizzazioneService.getOrganizzazione(idOrganizzazione);
    }

    @PostMapping("/creaOrganizzazione")
    public ResponseEntity<String> creaOrganizzazione(@ModelAttribute Organizzazione organizzazione,
                                                     @RequestParam(value = "sito", required = false) String sito,
                                                     @RequestParam(value = "instagram", required = false) String instagram,
                                                     @RequestParam(value = "twitter", required = false) String twitter,
                                                     @RequestParam(value = "facebook", required = false) String facebook,
                                                     @RequestParam(value = "linkedin", required = false) String linkedin,
                                                     @RequestParam(value = "foto", required = false) MultipartFile foto,
                                                     @RequestParam("idAdmin") Long idAdmin) {

        organizzazione = organizzazioneService.sanitizeOrganizzazione(organizzazione);


        aggiungiLinkOrganizzazione(organizzazione, "Instagram", instagram);
        aggiungiLinkOrganizzazione(organizzazione, "Twitter", twitter);
        aggiungiLinkOrganizzazione(organizzazione, "Facebook", facebook);
        aggiungiLinkOrganizzazione(organizzazione, "LinkedIn", linkedin);
        aggiungiLinkOrganizzazione(organizzazione, "Sito", sito);

        Organizzatore admin = new Organizzatore();
        admin.setId(idAdmin);
        organizzazione.setAdmin(admin);

        if (foto != null && !foto.isEmpty()) {

            try {
                String fileName = UUID.randomUUID().toString() + "_" + foto.getOriginalFilename();
                Path directory = Paths.get(uploadDir.replace("file:./", "")).resolve("organizzazioniImg");
                Files.createDirectories(directory);
                Path savePath = directory.resolve(fileName);
                try (InputStream inputStream = foto.getInputStream()) {
                    Files.copy(inputStream, savePath, StandardCopyOption.REPLACE_EXISTING);
                }
                organizzazione.setUrlFoto(fileName);

            } catch (IOException e) {
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

        try {
            Organizzazione newOrganizzazione = organizzazioneService.creaOrganizzazione(organizzazione);

            return new ResponseEntity<>(newOrganizzazione.getId().toString(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/deleteOrganizzatore")
    public ResponseEntity<String> deleteOrganizzatore(@RequestParam("idOrganizzatore") Long idOrganizzatore) {
        try {
            organizzazioneService.deleteOrganizzatore(idOrganizzatore);
            return new ResponseEntity<>("Organizzatore eliminato", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/deleteOrganizzazione")
    public ResponseEntity<String> deleteOrganizzazione(@RequestParam("id") Long id) {
        try {
            organizzazioneService.deleteOrganizzazione(id);
            return new ResponseEntity<>("Organizzazione eliminata", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/addOrganizzatore")
    public ResponseEntity<String> addOrganizzatore(@RequestParam("emailAddress") String email,
                                                   @RequestParam("idOrganizzazione") Long idOrganizzazione) {

        System.out.println("addOrganizzatore");

        try {
            organizzazioneService.addOrganizzatore(email, idOrganizzazione);
            return new ResponseEntity<>("Organizzatore aggiunto con successo", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            //eturn new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


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
