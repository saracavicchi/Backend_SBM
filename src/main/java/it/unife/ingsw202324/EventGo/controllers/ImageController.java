package it.unife.ingsw202324.EventGo.controllers;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    @GetMapping("/mockImg/{imageName:.+}")
    public ResponseEntity<Resource> getMockImage(@PathVariable String imageName) {
        try {
            Path fileStorageLocation = Paths.get(System.getProperty("user.dir") + "/src/main/resources/images/mockImg/");
            Path filePath = fileStorageLocation.resolve(imageName).normalize();
            Resource image = new UrlResource(filePath.toUri());

            if (image.exists() || image.isReadable()) {
                String contentType = Files.probeContentType(filePath);
                if (contentType == null) {
                    // Fallback se il tipo non può essere determinato
                    contentType = "application/octet-stream";
                }

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

    @GetMapping("/profileImg/{imageName:.+}")
    public ResponseEntity<Resource> getProfileImage(@PathVariable String imageName) {
        try {
            Path fileStorageLocation = Paths.get(System.getProperty("user.dir") + "/src/main/resources/images/profileImg/");
            Path filePath = fileStorageLocation.resolve(imageName).normalize();
            Resource image = new UrlResource(filePath.toUri());
            System.out.println(fileStorageLocation);
            if (image.exists() || image.isReadable()) {
                String contentType = Files.probeContentType(filePath);
                if (contentType == null) {
                    // Fallback se il tipo non può essere determinato
                    contentType = "application/octet-stream";
                }

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