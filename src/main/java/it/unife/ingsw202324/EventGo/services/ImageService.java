package it.unife.ingsw202324.EventGo.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageService {

    @Value("${app.upload.dir}organizzatoriImg")
    private String organizzatoreDir;

    @Value("${app.upload.dir}organizzazioniImg")
    private String organizzazioneDir;

    @Value("${app.upload.dir}mockImg")
    private String mockDir;

    public Resource loadImage(String tipologia, String name) throws Exception {
        String directory;

        if ("organizzatore".equals(tipologia)) {
            directory = organizzatoreDir.replace("file:", "");
        } else if ("mock".equals(tipologia)) {
            directory = mockDir.replace("file:", "");
        } else if ("organizzazione".equals(tipologia)) {
            directory = organizzazioneDir.replace("file:", "");
        } else {
            throw new IllegalArgumentException("Tipologia non valida");
        }

        Path directoryPath = Paths.get(directory);
        Path fileStorageLocation = directoryPath.resolve(name).normalize();
        Resource image = new UrlResource(fileStorageLocation.toUri());

        if (image.exists() && image.isReadable()) {
            return image;
        } else {
            throw new FileNotFoundException("File non trovato o non leggibile");
        }
    }

    public String getContentType(Path fileStorageLocation) throws Exception {
        return Files.probeContentType(fileStorageLocation);
    }
}
