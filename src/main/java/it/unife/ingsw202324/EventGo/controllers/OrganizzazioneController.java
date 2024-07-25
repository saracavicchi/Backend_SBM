package it.unife.ingsw202324.EventGo.controllers;

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

@RestController
@RequestMapping("/api/organizzazione")
public class OrganizzazioneController {

    private final OrganizzazioneService organizzazioneService;
    private final LinkOrganizzazioneService linkOrganizzazioneService;

    @Value("${app.upload.dir}")
    private String uploadDir;

    @Autowired
    public OrganizzazioneController(OrganizzazioneService organizzazioneService, LinkOrganizzazioneService linkOrganizzazioneService) {
        this.organizzazioneService = organizzazioneService;
        this.linkOrganizzazioneService = linkOrganizzazioneService;
    }

    @GetMapping("/getOrganizzazione")
    public Organizzazione getOrganizzazione(@RequestParam("id") Long idOrganizzazione) {
        System.out.println("idOrganizzazione: " + idOrganizzazione);
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


        linkOrganizzazioneService.aggiungiLinkOrganizzazione(organizzazione, "Instagram", instagram);
        linkOrganizzazioneService.aggiungiLinkOrganizzazione(organizzazione, "Twitter", twitter);
        linkOrganizzazioneService.aggiungiLinkOrganizzazione(organizzazione, "Facebook", facebook);
        linkOrganizzazioneService.aggiungiLinkOrganizzazione(organizzazione, "LinkedIn", linkedin);
        linkOrganizzazioneService.aggiungiLinkOrganizzazione(organizzazione, "Sito", sito);

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
            Organizzazione newOrganizzazione = organizzazioneService.salvaOrganizzazione(organizzazione, Optional.empty(), idAdmin);

            return new ResponseEntity<>(newOrganizzazione.getId().toString(), HttpStatus.OK);
        } catch (Exception e) {
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




}
