package it.unife.ingsw202324.EventGo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Classe modello per rappresentare un organizzatore di eventi.
 * Questa classe utilizza Lombok per generare automaticamente i metodi getter e setter,
 * facilitando così l'accesso e la modifica delle proprietà dell'oggetto.
 * Inoltre, utilizza le annotazioni di JPA (Java Persistence API) per mappare l'oggetto
 * a una tabella nel database, permettendo operazioni di persistenza come il salvataggio
 * e la lettura dei dati.
 */
@Getter
@Setter
@Entity
@Table(name = "organizzatore")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Organizzatore {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id; // Identificativo univoco per l'organizzatore.

    @Column(name = "cod_fiscale", columnDefinition = "CHAR(16)")
    private String codFiscale; // Codice fiscale dell'organizzatore, lunghezza fissa di 16 caratteri.

    @Column(name = "nome", nullable = false, length = 45)
    private String nome; // Nome dell'organizzatore, lunghezza massima di 45 caratteri.

    @Column(name = "cognome", nullable = false, length = 45)
    private String cognome; // Cognome dell'organizzatore, lunghezza massima di 45 caratteri.

    @Column(name = "data_nascita")
    private LocalDate dataNascita; // Data di nascita dell'organizzatore.

    @Column(name = "username", nullable = false, length = 20)
    private String username; // Username dell'organizzatore, lunghezza massima di 20 caratteri.

    @Column(name = "mail", nullable = false, length = 45)
    private String mail; // Indirizzo email dell'organizzatore, lunghezza massima di 45 caratteri.

    @Column(name = "telefono", length = 15)
    private String telefono; // Numero di telefono dell'organizzatore, lunghezza massima di 15 caratteri.

    @Lob
    @Column(name = "bio", columnDefinition = "TEXT")
    private String bio; // Biografia dell'organizzatore, memorizzata come testo.

    @Column(name = "url_foto", length = 2000)
    private String urlFoto; // URL della foto dell'organizzatore, lunghezza massima di 2000 caratteri.

    @JsonProperty("pIva")
    @Column(name = "p_iva", columnDefinition = "CHAR(11)")
    private String pIva; // Partita IVA dell'organizzatore, lunghezza fissa di 11 caratteri.

    @Column(name = "iban", length = 34)
    private String iban; // IBAN dell'organizzatore, lunghezza massima di 34 caratteri.

    @Column(name = "stato", length = 45)
    private String stato; // Stato di residenza dell'organizzatore, lunghezza massima di 45 caratteri.

    @Column(name = "provincia", length = 45)
    private String provincia; // Provincia di residenza dell'organizzatore, lunghezza massima di 45 caratteri.

    @Column(name = "`città`", length = 45)
    private String città; // Città di residenza dell'organizzatore, lunghezza massima di 45 caratteri.

    @Column(name = "cap", columnDefinition = "CHAR(5)")
    private String cap; // Codice di avviamento postale (CAP) dell'organizzatore, lunghezza fissa di 5 caratteri.

    @Column(name = "via", length = 45)
    private String via; // Via di residenza dell'organizzatore, lunghezza massima di 45 caratteri.

    @Column(name = "num_civico", length = 10)
    private String numCivico; // Numero civico della residenza dell'organizzatore, lunghezza massima di 10 caratteri.

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "id_organizzazione")
    @JsonIgnore
    private Organizzazione organizzazione; // Riferimento all'organizzazione a cui l'organizzatore appartiene. L'annotazione @JsonIgnore previene la serializzazione di questa proprietà.

    @OneToMany(mappedBy = "organizzatore")
    private Set<CarteOrganizzatore> carte = new LinkedHashSet<>(); // Insieme delle carte di credito associate all'organizzatore.

    @OneToMany(mappedBy = "organizzatore")
    private Set<LinkOrganizzatore> link = new LinkedHashSet<>(); // Insieme dei link ai social media associati all'organizzatore.

    @OneToOne(mappedBy = "admin")
    @JsonIgnore
    private Organizzazione orgAmministrata; // Organizzazione amministrata dall'organizzatore, se presente.
}