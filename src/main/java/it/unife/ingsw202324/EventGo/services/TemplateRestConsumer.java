package it.unife.ingsw202324.EventGo.services;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestClient;


/**
 * Classe per consumare servizi REST in modo template.
 * Fornisce un metodo per effettuare richieste GET a un servizio REST.
 */
@SpringBootApplication
public class TemplateRestConsumer {

    // Base URI per l'uso di mock durante il test locale
    static String uriBaseMock = "http://localhost:3000/api/";

    /**
     * Effettua una chiamata REST GET alla risorsa specificata.
     *
     * @param resourceName il nome della risorsa da richiedere.
     * @param uriBase      l'URI base del servizio REST.
     * @param useMock      flag che indica se utilizzare l'URI base del mock.
     * @return la risposta del servizio REST come stringa.
     */
    public static String callREST(String resourceName, String uriBase, boolean useMock) {

        // Crea un'istanza di RestClient
        RestClient restClient = RestClient.create();

        // Se useMock è true, usa l'URI base del mock
        if (useMock)
            uriBase = uriBaseMock;

        // Effettua una richiesta GET alla risorsa e ritorna il corpo della risposta come stringa
        return restClient.get()
                .uri(uriBase + resourceName)
                .retrieve()
                .body(String.class);
    }

}