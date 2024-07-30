package it.unife.ingsw202324.EventGo.controllers;

import it.unife.ingsw202324.EventGo.services.ImageService;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

    private static final Logger logger = LoggerFactory.getLogger(ImageControllerTest.class);

    // MockMvc per simulare le richieste HTTP
    @Autowired
    private MockMvc mockMvc;

    // Mock del servizio per la gestione delle immagini
    @MockBean
    private ImageService imageService;


    /**
     * Stampa un messaggio di log all'inizio dell'esecuzione del test.
     */
    @BeforeEach
    public void setup() {
        logger.info("Inizio esecuzione metodo di test");
    }

    /**
     * Stampa un messaggio di log alla fine dell'esecuzione del test.
     */
    @AfterEach
    public void tearDown() {
        logger.info("Fine esecuzione metodo di test");
    }

    /**
     * Testa il recupero di un'immagine di un organizzatore esistente.
     *
     * @throws Exception se si verifica un errore durante il test.
     */
    @Test
    @DisplayName("Test recupero immagine esistente di un organizzatore")
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
                .andExpect(content().contentType("image/jpeg"))
                .andExpect(content().bytes(Files.readAllBytes(Paths.get("src/test/resources/static/images/organizzatoriImg/test-image.jpg"))));

    }

    /**
     * Testa il recupero di un'immagine di un'organizzazione esistente.
     *
     * @throws Exception se si verifica un errore durante il test.
     */
    @Test
    @DisplayName("Test recupero immagine esistente di un'organizzazione")
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
                .andExpect(content().contentType("image/jpeg"))
                .andExpect(content().bytes(Files.readAllBytes(Paths.get("src/test/resources/static/images/organizzazioniImg/test-image.jpg"))));

    }

    /**
     * Testa il recupero di un'immagine mock esistente.
     *
     * @throws Exception se si verifica un errore durante il test.
     */
    @Test
    @DisplayName("Test recupero immagine di mock esistente")
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
                .andExpect(content().contentType("image/jpeg"))
                .andExpect(content().bytes(Files.readAllBytes(Paths.get("src/test/resources/static/images/mockImg/test-image.jpg"))));

    }

    /**
     * Testa il recupero di un'immagine di un'organizzazione non esistente.
     *
     * @throws Exception se si verifica un errore durante il test.
     */
    @Test
    @DisplayName("Test recupero immagine non esistente di un'organizzazione")
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
    @DisplayName("Test recupero immagine non esistente di un organizzatore")
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
    @DisplayName("Test recupero immagine di mock non esistente")
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
    @DisplayName("Test recupero immagine con tipologia non valida")
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
    @DisplayName("Test recupero immagine con errore del server")
    void testGetImageServerError() throws Exception {

        // Simula un'eccezione per un errore del server
        doThrow(new RuntimeException("Simulated server error")).when(imageService).loadImage("organizzazione", "test-image.jpg");

        // Simula una richiesta GET e verifica la risposta
        mockMvc.perform(get("/api/images/organizzazione")
                        .param("name", "test-image.jpg"))
                .andExpect(status().isInternalServerError());

    }
}
