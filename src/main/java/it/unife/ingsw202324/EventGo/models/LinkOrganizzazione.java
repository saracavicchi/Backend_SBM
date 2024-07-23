package it.unife.ingsw202324.EventGo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * Classe modello per rappresentare i link dei social media delle organizzazioni di eventi.
 * Questa classe utilizza Lombok per generare automaticamente i metodi getter e setter,
 * facilitando così l'accesso e la modifica delle proprietà dell'oggetto.
 * Inoltre, utilizza le annotazioni di JPA (Java Persistence API) per mappare l'oggetto
 * a una tabella nel database, permettendo operazioni di persistenza come il salvataggio
 * e la lettura dei dati.
 */
@Getter
@Setter
@Entity
@Table(name = "link_organizzazione") // Per mappare la classe sulla tabella "link_organizzazione"
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Necessario per evitare errori di deserializzazione
public class LinkOrganizzazione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id; // Identificativo univoco per ogni link di organizzazione.

    @Column(name = "nome_social", nullable = false, length = 20)
    private String nomeSocial; // Nome della piattaforma social (es. Facebook, Twitter), limitato a 20 caratteri.

    @Column(name = "url", nullable = false, length = 2000)
    private String url; // URL del profilo o della pagina social dell'organizzazione, limitato a 2000 caratteri.

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_organizzazione", nullable = false)
    @JsonIgnore
    private Organizzazione organizzazione; // Riferimento all'organizzazione associata a questo link. L'annotazione @JsonIgnore previene la serializzazione di questa proprietà per evitare cicli infiniti in JSON.

    /**
     * Costruttore di default necessario per JPA.
     * JPA richiede un costruttore vuoto per poter creare un'istanza della classe quando recupera gli oggetti dal database.
     */
    public LinkOrganizzazione() {
    }
}