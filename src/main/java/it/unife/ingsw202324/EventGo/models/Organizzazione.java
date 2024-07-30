package it.unife.ingsw202324.EventGo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Classe che rappresenta un'organizzazione nel sistema.
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
    private Long id;

    @Column(name = "nome", nullable = false, length = 45)
    private String nome;

    @Lob
    @Column(name = "descrizione", columnDefinition = "TEXT")
    private String descrizione;

    @Column(name = "mail", nullable = false, length = 45)
    private String mail;

    @Column(name = "telefono", length = 15)
    private String telefono;

    @Column(name = "url_foto", length = 2000)
    private String urlFoto;

    @Column(name = "stato", length = 45)
    private String stato;

    @Column(name = "provincia", length = 45)
    private String provincia;

    @Column(name = "`città`", length = 45)
    private String città;

    @Column(name = "cap", columnDefinition = "CHAR(5)")
    private String cap;

    @Column(name = "via", length = 45)
    private String via;

    @Column(name = "num_civico", length = 10)
    private String numCivico;

    @Column(name = "iban", length = 34)
    private String iban;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "id_admin", nullable = false)
    private Organizzatore admin;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "organizzazione")
    private Set<LinkOrganizzazione> link = new LinkedHashSet<>();

    @OneToMany(mappedBy = "organizzazione")
    @OrderBy("cognome ASC, nome ASC")
    private Set<Organizzatore> organizzatori = new LinkedHashSet<>();

}