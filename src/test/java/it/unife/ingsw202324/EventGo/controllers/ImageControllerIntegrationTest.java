package it.unife.ingsw202324.EventGo.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Classe di test di integrazione per il controller delle immagini (ImageController).
 * Verifica il corretto funzionamento degli endpoint di recupero delle immagini.
 */
@SpringBootTest
@ActiveProfiles("test")
@PropertySource("classpath:application-test.yaml")
public class ImageControllerIntegrationTest {

    private final static Logger logger = LoggerFactory.getLogger(ImageControllerIntegrationTest.class);

    // WebApplicationContext per il contesto dell'applicazione
    @Autowired
    private WebApplicationContext webApplicationContext;

    // MockMvc per simulare le richieste HTTP
    private MockMvc mockMvc;

    /**
     * Configura il MockMvc prima di ogni test.
     *
     * Stampa un messaggio di log all'inizio dell'esecuzione del test.
     */
    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

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
     * Verifica che il recupero di un'immagine di un organizzatore esistente restituisca uno status 200 (OK)
     * e contenga i corretti header e content type.
     */
    @Test
    @DisplayName("Test recupero immagine esistente di un organizzatore")
    void testGetImageOrganizzatoreExists() throws Exception {
        mockMvc.perform(get("/api/images/organizzatore")
                        .param("name", "test-image.jpg"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"test-image.jpg\""))
                .andExpect(content().contentType("image/jpeg"))
                .andExpect(content().bytes(Files.readAllBytes(Paths.get("src/test/resources/static/images/organizzatoriImg/test-image.jpg"))));;
    }

    /**
     * Verifica che il recupero di un'immagine di un'organizzazione esistente restituisca uno status 200 (OK)
     * e contenga i corretti header e content type.
     */
    @Test
    @DisplayName("Test recupero immagine esistente di un'organizzazione")
    void testGetImageOrganizzazioneExists() throws Exception {
        mockMvc.perform(get("/api/images/organizzazione")
                        .param("name", "test-image.jpg"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"test-image.jpg\""))
                .andExpect(content().contentType("image/jpeg"))
                .andExpect(content().bytes(Files.readAllBytes(Paths.get("src/test/resources/static/images/organizzazioniImg/test-image.jpg"))));
    }

    /**
     * Verifica che il recupero di un'immagine mock esistente restituisca uno status 200 (OK)
     * e contenga i corretti header e content type.
     */
    @Test
    @DisplayName("Test recupero immagine di mock esistente")
    void testGetImageMockExists() throws Exception {
        mockMvc.perform(get("/api/images/mock")
                        .param("name", "test-image.jpg"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"test-image.jpg\""))
                .andExpect(content().contentType("image/jpeg"))
                .andExpect(content().bytes(Files.readAllBytes(Paths.get("src/test/resources/static/images/mockImg/test-image.jpg"))));
    }


    /**
     * Verifica che il recupero di un'immagine di un organizzatore non esistente restituisca uno status 404 (Not Found).
     */
    @Test
    @DisplayName("Test recupero immagine non esistente di un organizzatore")
    void testGetImageOrganizzatoreNotExists() throws Exception {
        mockMvc.perform(get("/api/images/organizzatore")
                        .param("name", "non-existent-image.jpg"))
                .andExpect(status().isNotFound());
    }

    /**
     * Verifica che il recupero di un'immagine di un'organizzazione non esistente restituisca uno status 404 (Not Found).
     */
    @Test
    @DisplayName("Test recupero immagine non esistente di un'organizzazione")
    void testGetImageOrganizzazioneNotExists() throws Exception {
        mockMvc.perform(get("/api/images/organizzazione")
                        .param("name", "non-existent-image.jpg"))
                .andExpect(status().isNotFound());
    }

    /**
     * Verifica che il recupero di un'immagine mock non esistente restituisca uno status 404 (Not Found).
     */
    @Test
    @DisplayName("Test recupero immagine di mock non esistente")
    void testGetImageMockNotExists() throws Exception {
        mockMvc.perform(get("/api/images/mock")
                        .param("name", "non-existent-image.jpg"))
                .andExpect(status().isNotFound());
    }


    /**
     * Verifica che il recupero di un'immagine con una tipologia non valida restituisca uno status 400 (Bad Request).
     */
    @Test
    @DisplayName("Test recupero immagine con tipologia non valida")
    void testGetImageInvalidTipologia() throws Exception {
        mockMvc.perform(get("/api/images/invalid")
                        .param("name", "test-image.jpg"))
                .andExpect(status().isBadRequest());
    }


}
