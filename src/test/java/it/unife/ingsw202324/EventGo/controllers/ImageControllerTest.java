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

/**
 * Classe di test per il controller delle immagini (ImageController).
 * Utilizza MockMvc per simulare le richieste HTTP e verificare le risposte.
 */
@WebMvcTest(ImageController.class)
@ActiveProfiles("test")
@PropertySource("classpath:application-test.yaml")
public class ImageControllerTest {

    // MockMvc per simulare le richieste HTTP
    @Autowired
    private MockMvc mockMvc;

    // Mock del servizio per la gestione delle immagini
    @MockBean
    private ImageService imageService;

    /**
     * Testa il recupero di un'immagine di un organizzatore esistente.
     *
     * @throws Exception se si verifica un errore durante il test.
     */
    @Test
    void testGetImageOrganizzatoreExists() throws Exception {

        // Percorso dell'immagine di test
        Path imagePath = new ClassPathResource("static/images/organizzatoriImg/test-image.jpg").getFile().toPath();
        Resource resource = new UrlResource(imagePath.toUri());

        // Mock delle chiamate al servizio
        when(imageService.loadImage("organizzatore", "test-image.jpg")).thenReturn(resource);
        when(imageService.getContentType(imagePath)).thenReturn("image/jpeg");

        // Simula una richiesta GET e verifica la risposta
        mockMvc.perform(get("/api/images/organizzatore")
                        .param("name", "test-image.jpg"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"test-image.jpg\""))
                .andExpect(content().contentType("image/jpeg"));
    }

    /**
     * Testa il recupero di un'immagine di un'organizzazione esistente.
     *
     * @throws Exception se si verifica un errore durante il test.
     */
    @Test
    void testGetImageOrganizzazioneExists() throws Exception {

        // Percorso dell'immagine di test
        Path imagePath = new ClassPathResource("static/images/organizzazioniImg/test-image.jpg").getFile().toPath();
        Resource resource = new UrlResource(imagePath.toUri());

        // Mock delle chiamate al servizio
        when(imageService.loadImage("organizzazione", "test-image.jpg")).thenReturn(resource);
        when(imageService.getContentType(imagePath)).thenReturn("image/jpeg");

        // Simula una richiesta GET e verifica la risposta
        mockMvc.perform(get("/api/images/organizzazione")
                        .param("name", "test-image.jpg"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"test-image.jpg\""))
                .andExpect(content().contentType("image/jpeg"));
    }

    /**
     * Testa il recupero di un'immagine mock esistente.
     *
     * @throws Exception se si verifica un errore durante il test.
     */
    @Test
    void testGetImageMockExists() throws Exception {

        // Percorso dell'immagine di test
        Path imagePath = new ClassPathResource("static/images/mockImg/test-image.jpg").getFile().toPath();
        Resource resource = new UrlResource(imagePath.toUri());

        // Mock delle chiamate al servizio
        when(imageService.loadImage("mock", "test-image.jpg")).thenReturn(resource);
        when(imageService.getContentType(imagePath)).thenReturn("image/jpeg");

        // Simula una richiesta GET e verifica la risposta
        mockMvc.perform(get("/api/images/mock")
                        .param("name", "test-image.jpg"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"test-image.jpg\""))
                .andExpect(content().contentType("image/jpeg"));
    }

    /**
     * Testa il recupero di un'immagine di un'organizzazione non esistente.
     *
     * @throws Exception se si verifica un errore durante il test.
     */
    @Test
    void testGetImageOrganizzazioneNotExists() throws Exception {

        // Simula un'eccezione per un'immagine non trovata
        doThrow(new FileNotFoundException("File non trovato o non leggibile")).when(imageService).loadImage("organizzazione", "non-existent-image.jpg");

        // Simula una richiesta GET e verifica la risposta
        mockMvc.perform(get("/api/images/organizzazione")
                        .param("name", "non-existent-image.jpg"))
                .andExpect(status().isNotFound());
    }

    /**
     * Testa il recupero di un'immagine di un organizzatore non esistente.
     *
     * @throws Exception se si verifica un errore durante il test.
     */
    @Test
    void testGetImageOrganizzatoreNotExists() throws Exception {

        // Simula un'eccezione per un'immagine non trovata
        doThrow(new FileNotFoundException("File non trovato o non leggibile")).when(imageService).loadImage("organizzatore", "non-existent-image.jpg");

        // Simula una richiesta GET e verifica la risposta
        mockMvc.perform(get("/api/images/organizzatore")
                        .param("name", "non-existent-image.jpg"))
                .andExpect(status().isNotFound());
    }

    /**
     * Testa il recupero di un'immagine mock non esistente.
     *
     * @throws Exception se si verifica un errore durante il test.
     */
    @Test
    void testGetImageMockNotExists() throws Exception {

        // Simula un'eccezione per un'immagine non trovata
        doThrow(new FileNotFoundException("File non trovato o non leggibile")).when(imageService).loadImage("mock", "non-existent-image.jpg");

        // Simula una richiesta GET e verifica la risposta
        mockMvc.perform(get("/api/images/mock")
                        .param("name", "non-existent-image.jpg"))
                .andExpect(status().isNotFound());
    }

    /**
     * Testa il recupero di un'immagine con tipologia non valida.
     *
     * @throws Exception se si verifica un errore durante il test.
     */
    @Test
    void testGetImageInvalidTipologia() throws Exception {

        // Simula un'eccezione per una tipologia non valida
        doThrow(new IllegalArgumentException("Tipologia non valida")).when(imageService).loadImage("invalid", "test-image.jpg");

        // Simula una richiesta GET e verifica la risposta
        mockMvc.perform(get("/api/images/invalid")
                        .param("name", "test-image.jpg"))
                .andExpect(status().isBadRequest());
    }

    /**
     * Testa il recupero di un'immagine con errore del server.
     *
     * @throws Exception se si verifica un errore durante il test.
     */
    @Test
    void testGetImageServerError() throws Exception {

        // Simula un'eccezione per un errore del server
        doThrow(new RuntimeException("Simulated server error")).when(imageService).loadImage("organizzazione", "test-image.jpg");

        // Simula una richiesta GET e verifica la risposta
        mockMvc.perform(get("/api/images/organizzazione")
                        .param("name", "test-image.jpg"))
                .andExpect(status().isInternalServerError());
    }
}
