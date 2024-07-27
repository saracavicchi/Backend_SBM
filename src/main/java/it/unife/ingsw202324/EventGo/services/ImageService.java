package it.unife.ingsw202324.EventGo.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Servizio per la gestione delle immagini.
 * Fornisce metodi per caricare immagini in base alla tipologia e per ottenere il tipo di contenuto delle immagini.
 */
@Service
public class ImageService {

    // Directory per le immagini degli organizzatori, risolta da application.yaml
    @Value("${app.upload.dir}organizzatoriImg")
    private String organizzatoreDir;

    // Directory per le immagini delle organizzazioni, risolta da application.yaml
    @Value("${app.upload.dir}organizzazioniImg")
    private String organizzazioneDir;

    // Directory per le immagini di mock, risolta da application.yaml
    @Value("${app.upload.dir}mockImg")
    private String mockDir;

    /**
     * Carica un'immagine in base alla tipologia e al nome forniti.
     *
     * @param tipologia la tipologia dell'immagine (organizzatore, mock, organizzazione).
     * @param name il nome dell'immagine da caricare.
     * @return la risorsa immagine caricata.
     * @throws Exception se l'immagine non viene trovata o non è leggibile.
     */
    public Resource loadImage(String tipologia, String name) throws Exception {

        String directory;

        // Determina la directory basata sulla tipologia fornita
        if ("organizzatore".equals(tipologia)) {
            directory = organizzatoreDir.replace("file:", "");
        } else if ("mock".equals(tipologia)) {
            directory = mockDir.replace("file:", "");
        } else if ("organizzazione".equals(tipologia)) {
            directory = organizzazioneDir.replace("file:", "");
        } else {
            // Lancia un'eccezione se la tipologia non è valida
            throw new IllegalArgumentException("Tipologia non valida");
        }

        // Costruisce il percorso del file e lo normalizza
        Path directoryPath = Paths.get(directory);
        Path fileStorageLocation = directoryPath.resolve(name).normalize();
        Resource image = new UrlResource(fileStorageLocation.toUri());

        // Verifica se l'immagine esiste ed è leggibile
        if (image.exists() && image.isReadable()) {
            return image;
        } else {
            // Lancia un'eccezione se l'immagine non viene trovata o non è leggibile
            throw new FileNotFoundException("File non trovato o non leggibile");
        }
    }

    /**
     * Ottiene il tipo di contenuto di un file basato sulla sua estensione.
     *
     * @param fileStorageLocation il percorso del file di cui determinare il tipo di contenuto.
     * @return il tipo di contenuto del file.
     * @throws Exception se si verifica un errore nel determinare il tipo di contenuto.
     */
    public String getContentType(Path fileStorageLocation) throws Exception {
        // Utilizza Files.probeContentType per determinare il tipo di contenuto del file
        return Files.probeContentType(fileStorageLocation);
    }
}
