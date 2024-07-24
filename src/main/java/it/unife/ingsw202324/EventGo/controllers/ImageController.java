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

@RestController
@RequestMapping("/api/images")
public class ImageController {
    @Value("${app.upload.dir}organizzatoriImg")
    private String organizzatoreDir;

    @Value("${app.upload.dir}organizzazioniImg")
    private String organizzazioneDir;

    @Value("${app.upload.dir}mockImg")
    private String mockDir;

    public ImageController() {
    }

    @GetMapping("/{tipologia}")
    public ResponseEntity<Resource> getImage(@PathVariable String tipologia, @RequestParam("name") String name) {
        String directory;
        //System.out.println(tipologia);
        if ("organizzatore".equals(tipologia)) {
            directory = organizzatoreDir.replace("file:", "");
        } else if ("mock".equals(tipologia)) {
            directory = mockDir.replace("file:", "");
        } else if ("organizzazione".equals(tipologia)) {
            directory = organizzazioneDir.replace("file:", "");
        } else {
            directory = null;
            return ResponseEntity.badRequest().build();
        }

        try {
            // Converti la stringa directory in un oggetto Path
            Path directoryPath = Paths.get(directory);

            // Utilizza resolve per costruire il percorso completo
            Path fileStorageLocation = directoryPath.resolve(name).normalize();
            Resource image = new UrlResource(fileStorageLocation.toUri());

            if (image.exists() || image.isReadable()) {
                String contentType = Files.probeContentType(fileStorageLocation);
                contentType = contentType == null ? "application/octet-stream" : contentType;

                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFilename() + "\"")
                        .contentType(MediaType.parseMediaType(contentType))
                        .body(image);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}