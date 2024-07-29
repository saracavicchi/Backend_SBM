package it.unife.ingsw202324.EventGo.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

/**
 * Service per la gestione delle immagini.
 * Fornisce funzionalità per il caricamento, il salvataggio e la gestione delle immagini.
 *
 * Le immagini sono organizzate in diverse directory basate sul tipo:
 * - organizzatore
 * - organizzazione
 * - mock
 *
 * Configurazioni per le directory di salvataggio sono iniettate tramite valori di configurazione
 * definiti nel file di proprietà dell'applicazione.
 */
@Service
public class ImageService {

    // Directory di salvataggio delle immagini per gli organizzatori
    @Value("${app.upload.dir}organizzatoriImg")
    private String organizzatoreDir;

    // Directory di salvataggio delle immagini per le organizzazioni
    @Value("${app.upload.dir}organizzazioniImg")
    private String organizzazioneDir;

    // Directory di salvataggio delle immagini mock
    @Value("${app.upload.dir}mockImg")
    private String mockDir;


    /**
     * Carica un'immagine dal filesystem basata sulla tipologia e il nome forniti.
     *
     * @param tipologia la tipologia dell'immagine (organizzatore, organizzazione, mock)
     * @param name il nome dell'immagine da caricare
     * @return la risorsa dell'immagine caricata
     * @throws Exception se l'immagine non può essere caricata
     */
    public Resource loadImage(String tipologia, String name) throws Exception {

        // Determina la directory in base alla tipologia fornita
        String directory;

        if ("organizzatore".equals(tipologia)) {
            directory = organizzatoreDir.replace("file:", "");
        } else if ("mock".equals(tipologia)) {
            directory = mockDir.replace("file:", "");
        } else if ("organizzazione".equals(tipologia)) {
            directory = organizzazioneDir.replace("file:", "");
        } else {
            // Lancia un'eccezione se la tipologia non è riconosciuta
            throw new IllegalArgumentException("Tipologia non valida");
        }

        Path directoryPath = Paths.get(directory);
        // Risolve il percorso completo del file basato sul nome fornito
        Path fileStorageLocation = directoryPath.resolve(name).normalize();
        Resource image = new UrlResource(fileStorageLocation.toUri());

        // Verifica se l'immagine esiste ed è leggibile
        if (image.exists() && image.isReadable()) {
            return image;
        } else {
            throw new FileNotFoundException("File non trovato o non leggibile");
        }
    }


    /**
     * Restituisce il tipo di contenuto di un file specificato.
     *
     * @param fileStorageLocation il percorso del file
     * @return il tipo di contenuto del file
     * @throws Exception se il tipo di contenuto non può essere determinato
     */
    public String getContentType(Path fileStorageLocation) throws Exception {
        // Utilizza il metodo probeContentType per ottenere il MIME type del file
        return Files.probeContentType(fileStorageLocation);
    }


    /**
     * Salva un'immagine nel filesystem, gestendo il caso di un'immagine precedente e di un'eventuale cancellazione.
     *
     * @param type il tipo di immagine (organizzatore, organizzazione)
     * @param foto il file dell'immagine da salvare
     * @param oldFoto l'URL dell'immagine precedente, se presente
     * @param deletedPhoto flag che indica se l'immagine precedente deve essere cancellata
     * @return il nome del file salvato
     */
    public String saveImage(String type, MultipartFile foto, Optional<String> oldFoto, String deletedPhoto) {

        String destDirectory;

        // URL dell'immagine precedente, se presente
        String urlFoto = null;
        if (oldFoto.isPresent()) {
            urlFoto = oldFoto.get();
        }

        // Se c'è un nuovo file da salvare
        if (foto != null && !foto.isEmpty()) {

            try {

                // Genera un nuovo nome di file unico utilizzando UUID
                String fileName = UUID.randomUUID().toString() + "_" + foto.getOriginalFilename();

                // Determina la directory di destinazione in base al tipo
                if (type.equals("organizzatore")) {
                    destDirectory = organizzatoreDir;
                } else if (type.equals("organizzazione")) {
                    destDirectory = organizzazioneDir;
                } else {
                    // Lancia un'eccezione se la tipologia non è riconosciuta
                    throw new IllegalArgumentException("Tipologia non valida");
                }

                Path directory = Paths.get(destDirectory.replace("file:./", ""));
                // Crea le directory se non esistono
                Files.createDirectories(directory);
                Path savePath = directory.resolve(fileName);

                // Copia il file nella directory di destinazione
                try (InputStream inputStream = foto.getInputStream()) {
                    Files.copy(inputStream, savePath, StandardCopyOption.REPLACE_EXISTING);
                }

                // Restituisce il nome del file salvato
                return fileName;

            } catch (IOException e) {
                // Lancia un'eccezione runtime in caso di errore durante il salvataggio
                throw new RuntimeException("Errore nel salvataggio dell'immagine");
            }

        } else {
            // Gestione della rimozione dell'immagine precedente
            if (deletedPhoto != null && deletedPhoto.equals("true")) {
                return null;
            } else if (urlFoto != null) {
                // Restituisce l'URL della vecchia immagine se esiste e non è stata rimossa
                return urlFoto;
            }
        }

        // Lancia un'eccezione runtime se nessuna condizione è stata soddisfatta
        throw new RuntimeException("Errore nel salvataggio dell'immagine");
    }


}
