package it.unife.ingsw202324.EventGo.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@PropertySource("classpath:application-test.yaml")
public class ImageControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testGetImageOrganizzatoreExists() throws Exception {
        mockMvc.perform(get("/api/images/organizzatore")
                        .param("name", "test-image.jpg"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"test-image.jpg\""))
                .andExpect(content().contentType("image/jpeg"));
    }

    @Test
    void testGetImageOrganizzazioneExists() throws Exception {
        mockMvc.perform(get("/api/images/organizzazione")
                        .param("name", "test-image.jpg"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"test-image.jpg\""))
                .andExpect(content().contentType("image/jpeg"));
    }

    @Test
    void testGetImageMockExists() throws Exception {
        mockMvc.perform(get("/api/images/mock")
                        .param("name", "test-image.jpg"))
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"test-image.jpg\""))
                .andExpect(content().contentType("image/jpeg"));
    }


    @Test
    void testGetImageOrganizzatoreNotExists() throws Exception {
        mockMvc.perform(get("/api/images/organizzatore")
                        .param("name", "non-existent-image.jpg"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetImageOrganizzazioneNotExists() throws Exception {
        mockMvc.perform(get("/api/images/organizzazione")
                        .param("name", "non-existent-image.jpg"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetImageMockNotExists() throws Exception {
        mockMvc.perform(get("/api/images/mock")
                        .param("name", "non-existent-image.jpg"))
                .andExpect(status().isNotFound());
    }


    @Test
    void testGetImageInvalidTipologia() throws Exception {
        mockMvc.perform(get("/api/images/invalid")
                        .param("name", "test-image.jpg"))
                .andExpect(status().isBadRequest());
    }


}
