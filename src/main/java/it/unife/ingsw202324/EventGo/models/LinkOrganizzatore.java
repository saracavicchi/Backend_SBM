package it.unife.ingsw202324.EventGo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * Classe modello per rappresentare i link dei social media degli organizzatori di eventi.
 * Utilizza Lombok per generare automaticamente getter e setter.
 * Utilizza JPA per mappare l'oggetto a una tabella nel database.
 */
@Getter
@Setter
@Entity
@Table(name = "link_organizzatore") // Per mappare la classe sulla tabella "link_organizzatore"
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Necessario per evitare errori di deserializzazione
public class LinkOrganizzatore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id; // Identificativo univoco del link

    @Column(name = "nome_social", nullable = false, length = 20)
    private String nomeSocial; // Nome della piattaforma social (es. Facebook, Twitter)

    @Column(name = "url", nullable = false, length = 2000)
    private String url; // URL del profilo o della pagina social dell'organizzatore

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_organizzatore", nullable = false)
    @JsonIgnore
    private Organizzatore organizzatore; // Riferimento all'organizzatore associato al link. L'annotazione @JsonIgnore previene la serializzazione di questa proprietà per evitare cicli infiniti in JSON.

    /**
     * Costruttore di default necessario per JPA.
     */
    public LinkOrganizzatore() {
    }

    // Metodi getter e setter generati automaticamente da Lombok
}