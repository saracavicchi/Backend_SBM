package it.unife.ingsw202324.EventGo.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Controller per la gestione delle immagini caricate.
 * Fornisce un endpoint per recuperare le immagini in base alla tipologia.
 */
@RestController
@RequestMapping("/api/images")
public class ImageController {

    // Directory per le immagini degli organizzatori, path a partire dal file di configurazione
    @Value("${app.upload.dir}organizzatoriImg")
    private String organizzatoreDir;

    // Directory per le immagini delle organizzazioni, path a partire dal file di configurazione
    @Value("${app.upload.dir}organizzazioniImg")
    private String organizzazioneDir;

    // Directory per le immagini di mock
    @Value("${app.upload.dir}mockImg")
    private String mockDir;

    public ImageController() {
    }

    /**
     * Recupera un'immagine basata sulla tipologia e sul nome forniti.
     *
     * @param tipologia la tipologia dell'immagine (organizzatore, organizzazione, mock).
     * @param name il nome dell'immagine da recuperare.
     * @return la risposta contenente l'immagine come Resource.
     */
    @GetMapping("/{tipologia}")
    public ResponseEntity<Resource> getImage(@PathVariable String tipologia, @RequestParam("name") String name) {

        String directory;

        // Determina la directory in base alla tipologia fornita
        if ("organizzatore".equals(tipologia)) {
            directory = organizzatoreDir.replace("file:", "");
        } else if ("mock".equals(tipologia)) {
            directory = mockDir.replace("file:", "");
        } else if ("organizzazione".equals(tipologia)) {
            directory = organizzazioneDir.replace("file:", "");
        } else {
            directory = null;
            // Restituisce una risposta di richiesta non valida se la tipologia non è riconosciuta
            return ResponseEntity.badRequest().build();
        }

        try {
            // Crea il percorso della directory
            Path directoryPath = Paths.get(directory);

            // Crea il percorso completo del file immagine
            Path fileStorageLocation = directoryPath.resolve(name).normalize();
            Resource image = new UrlResource(fileStorageLocation.toUri());

            // Verifica se l'immagine esiste ed è leggibile
            if (image.exists() || image.isReadable()) {

                // Determina il tipo di contenuto dell'immagine
                String contentType = Files.probeContentType(fileStorageLocation);
                contentType = contentType == null ? "application/octet-stream" : contentType;

                // Restituisce l'immagine come risposta con i relativi header
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFilename() + "\"")
                        .contentType(MediaType.parseMediaType(contentType))
                        .body(image);
            } else {
                // Restituisce una risposta di immagine non trovata se l'immagine non esiste o non è leggibile
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            // Gestisce eventuali eccezioni restituendo una risposta di errore interno del server
            return ResponseEntity.internalServerError().build();
        }
    }
}