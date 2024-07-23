package it.unife.ingsw202324.EventGo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Classe che rappresenta l'entità Organizzazione all'interno del sistema.
 * Questa classe è mappata a una tabella nel database che conserva le informazioni
 * relative alle organizzazioni che gestiscono eventi.
 */
@Getter
@Setter
@Entity
@Table(name = "organizzazione")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Organizzazione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id; // Identificativo univoco dell'organizzazione.

    @Column(name = "nome", nullable = false, length = 45)
    private String nome; // Nome dell'organizzazione.

    @Lob
    @Column(name = "descrizione", columnDefinition = "TEXT")
    private String descrizione; // Descrizione dettagliata dell'organizzazione.

    @Column(name = "mail", nullable = false, length = 45)
    private String mail; // Indirizzo email di contatto dell'organizzazione.

    @Column(name = "telefono", length = 15)
    private String telefono; // Numero di telefono di contatto dell'organizzazione.

    @Column(name = "url_foto", length = 2000)
    private String urlFoto; // URL della foto o del logo dell'organizzazione.

    @Column(name = "iban", length = 34)
    private String iban; // IBAN per le transazioni finanziarie dell'organizzazione.

    @Column(name = "stato", length = 45)
    private String stato; // Stato di residenza dell'organizzazione.

    @Column(name = "provincia", length = 45)
    private String provincia; // Provincia di residenza dell'organizzazione.

    @Column(name = "`città`", length = 45)
    private String città; // Città di residenza dell'organizzazione.

    @Column(name = "cap", columnDefinition = "CHAR(5)")
    private String cap; // Codice di avviamento postale (CAP) dell'organizzazione.

    @Column(name = "via", length = 45)
    private String via; // Via di residenza dell'organizzazione.

    @Column(name = "num_civico", length = 10)
    private String numCivico; // Numero civico dell'organizzazione.

    @OneToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_admin")
    private Organizzatore admin; // Riferimento all'organizzatore che amministra l'organizzazione.

    @OneToMany(mappedBy = "organizzazione")
    private Set<LinkOrganizzazione> link = new LinkedHashSet<>(); // Insieme dei link associati all'organizzazione.

    @OneToMany(mappedBy = "organizzazione")
    private Set<Organizzatore> organizzatori = new LinkedHashSet<>(); // Insieme degli organizzatori associati all'organizzazione.
}