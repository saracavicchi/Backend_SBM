package it.unife.ingsw202324.EventGo.controllers;

import it.unife.ingsw202324.EventGo.services.ImageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.io.FileNotFoundException;
import java.nio.file.Path;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ImageController.class)
@ActiveProfiles("test")
@PropertySource("classpath:application-test.yaml")
public class ImageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ImageService imageService;

    @Test
    void testGetImageOrganizzatoreExists() throws Exception {
        Path imagePath = new ClassPathResource("static/images/organizzatoriImg/test-image.jpg").getFile().toPath();
        Resource resource = new UrlResource(imagePath.toUri());

        when(imageService.loadImage("organizzatore", "test-image.jpg")).thenReturn(resource);
        when(imageService.getContentType(imagePath)).thenReturn("image/jpeg");

        mockMvc.perform(get("/api/images/organizzatore")
                        .param("name", "test-image.jpg"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"test-image.jpg\""))
                .andExpect(content().contentType("image/jpeg"));
    }

    @Test
    void testGetImageOrganizzazioneExists() throws Exception {
        Path imagePath = new ClassPathResource("static/images/organizzazioniImg/test-image.jpg").getFile().toPath();
        Resource resource = new UrlResource(imagePath.toUri());

        when(imageService.loadImage("organizzazione", "test-image.jpg")).thenReturn(resource);
        when(imageService.getContentType(imagePath)).thenReturn("image/jpeg");

        mockMvc.perform(get("/api/images/organizzazione")
                        .param("name", "test-image.jpg"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"test-image.jpg\""))
                .andExpect(content().contentType("image/jpeg"));
    }

    @Test
    void testGetImageMockExists() throws Exception {
        Path imagePath = new ClassPathResource("static/images/mockImg/test-image.jpg").getFile().toPath();
        Resource resource = new UrlResource(imagePath.toUri());

        when(imageService.loadImage("mock", "test-image.jpg")).thenReturn(resource);
        when(imageService.getContentType(imagePath)).thenReturn("image/jpeg");

        mockMvc.perform(get("/api/images/mock")
                        .param("name", "test-image.jpg"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"test-image.jpg\""))
                .andExpect(content().contentType("image/jpeg"));
    }

    @Test
    void testGetImageOrganizzazioneNotExists() throws Exception {

        doThrow(new FileNotFoundException("File non trovato o non leggibile")).when(imageService).loadImage("organizzazione", "non-existent-image.jpg");

        mockMvc.perform(get("/api/images/organizzazione")
                        .param("name", "non-existent-image.jpg"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetImageOrganizzatoreNotExists() throws Exception {

        doThrow(new FileNotFoundException("File non trovato o non leggibile")).when(imageService).loadImage("organizzatore", "non-existent-image.jpg");

        mockMvc.perform(get("/api/images/organizzatore")
                        .param("name", "non-existent-image.jpg"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetImageMockNotExists() throws Exception {

        doThrow(new FileNotFoundException("File non trovato o non leggibile")).when(imageService).loadImage("mock", "non-existent-image.jpg");

        mockMvc.perform(get("/api/images/mock")
                        .param("name", "non-existent-image.jpg"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetImageInvalidTipologia() throws Exception {

        doThrow(new IllegalArgumentException("Tipologia non valida")).when(imageService).loadImage("invalid", "test-image.jpg");

        mockMvc.perform(get("/api/images/invalid")
                        .param("name", "test-image.jpg"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetImageServerError() throws Exception {

        doThrow(new RuntimeException("Simulated server error")).when(imageService).loadImage("organizzazione", "test-image.jpg");

        mockMvc.perform(get("/api/images/organizzazione")
                        .param("name", "test-image.jpg"))
                .andExpect(status().isInternalServerError());
    }
}
