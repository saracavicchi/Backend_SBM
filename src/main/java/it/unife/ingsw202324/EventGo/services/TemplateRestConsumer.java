package it.unife.ingsw202324.EventGo.services;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestClient;

/**
 * Classe TemplateRestConsumer per consumare servizi REST.
 * Questa classe dimostra come utilizzare un client REST per effettuare chiamate a servizi esterni.
 * È annotata con @SpringBootApplication, indicando che è il punto di ingresso per l'applicazione Spring Boot.
 */
@SpringBootApplication
public class TemplateRestConsumer {

    // URI base per il mock server. Utilizzato per testare l'applicazione con dati fittizi.
    static String uriBaseMock = "http://localhost:3000/api/";

    /**
     * Effettua una chiamata REST a una risorsa specificata.
     *
     * @param resourceName Il nome della risorsa a cui fare la chiamata.
     * @param uriBase L'URI base del servizio a cui fare la chiamata.
     * @param useMock Flag booleano per determinare se utilizzare o meno l'URI mock.
     * @return La risposta del servizio come stringa.
     */
    public static String callREST(String resourceName, String uriBase, boolean useMock) {
        RestClient restClient = RestClient.create();

        // Se useMock è true, sostituisce uriBase con uriBaseMock per utilizzare il mock server.
        if(useMock)
            uriBase = uriBaseMock;

        // Effettua una chiamata GET al servizio REST e ritorna il corpo della risposta come stringa.
        return restClient.get()
                .uri(uriBase + resourceName)
                .retrieve()
                .body(String.class);
    }
}