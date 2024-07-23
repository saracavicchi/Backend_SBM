package it.unife.ingsw202324.EventGo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

/**
 * Classe modello per rappresentare le carte di credito degli organizzatori di eventi.
 * Utilizza Lombok per generare automaticamente getter e setter.
 * Utilizza JPA per mappare l'oggetto a una tabella nel database.
 */
@Getter
@Setter
@Entity
@Table(name = "carte_organizzatore")  // Per mappare la classe sulla tabella "carte_organizzatore"
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // Necessario per evitare errori di deserializzazione
public class CarteOrganizzatore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id; // Identificativo univoco per ogni carta di credito

    @Column(name = "numero", nullable = false, columnDefinition = "CHAR(16)")
    private String numero; // Numero della carta di credito, deve essere lungo 16 caratteri

    @Column(name = "data_scadenza", nullable = false)
    private LocalDate dataScadenza; // Data di scadenza della carta di credito

    @Column(name = "cvv", nullable = false, columnDefinition = "CHAR(3)")
    private String cvv; // Codice CVV della carta di credito, deve essere lungo 3 caratteri

    @Column(name = "nome", nullable = false, length = 45)
    private String nomeOrganizzatore; // Nome dell'organizzatore associato alla carta, lunghezza massima 45 caratteri

    @Column(name = "cognome", nullable = false, length = 45)
    private String cognomeOrganizzatore; // Cognome dell'organizzatore associato alla carta, lunghezza massima 45 caratteri

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_organizzatore", nullable = false)
    @JsonIgnore
    private Organizzatore organizzatore; // Collegamento all'organizzatore proprietario della carta. Caricamento lazy: i dati dell'organizzatore non vengono caricati finché non vengono richiesti.

    /**
     * Costruttore di default necessario per JPA.
     */
    public CarteOrganizzatore() {
    }

    // Metodi getter e setter generati automaticamente da Lombok
}