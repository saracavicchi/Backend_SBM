package it.unife.ingsw202324.EventGo.controllers;

import it.unife.ingsw202324.EventGo.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Controller REST per la gestione delle immagini.
 * Fornisce endpoint per il recupero delle immagini basate sulla tipologia e il nome.
 */
@RestController
@RequestMapping("/api/images")
public class ImageController {

    private final ImageService imageService;

    /**
     * Costruttore per l'iniezione del servizio ImageService.
     *
     * @param imageService il servizio per la gestione delle immagini
     */
    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }


    /**
     * Endpoint per recuperare un'immagine specificata dalla tipologia e dal nome.
     *
     * @param tipologia la tipologia dell'immagine (organizzatore, organizzazione, mock)
     * @param name il nome dell'immagine da recuperare
     * @return ResponseEntity contenente la risorsa dell'immagine
     */
    @GetMapping("/{tipologia}")
    public ResponseEntity<Resource> getImage(@PathVariable String tipologia, @RequestParam("name") String name) {
        try {
            // Carica l'immagine utilizzando il servizio ImageService
            Resource image = imageService.loadImage(tipologia, name);
            Path fileStorageLocation = Paths.get(image.getURI());

            // Ottiene il tipo di contenuto dell'immagine
            String contentType = imageService.getContentType(fileStorageLocation);
            contentType = contentType == null ? "application/octet-stream" : contentType;

            // Costruisce la risposta con l'immagine e i relativi header
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFilename() + "\"")
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(image);
        } catch (IllegalArgumentException e) {
            // Gestisce il caso di una tipologia non valida
            return ResponseEntity.badRequest().build();
        } catch (FileNotFoundException e) {
            // Gestisce il caso in cui il file non è stato trovato
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            // Gestisce gli altri tipi di eccezioni
            return ResponseEntity.internalServerError().build();
        }
    }
}