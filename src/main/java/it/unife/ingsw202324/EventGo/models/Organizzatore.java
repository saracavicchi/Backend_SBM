package it.unife.ingsw202324.EventGo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Classe che rappresenta un organizzatore nel sistema.
 */
@Getter
@Setter
@Entity
@Table(name = "organizzatore")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Organizzatore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "cod_fiscale", columnDefinition = "CHAR(16)")
    private String codFiscale;

    @Column(name = "nome", nullable = false, length = 45)
    private String nome;

    @Column(name = "cognome", nullable = false, length = 45)
    private String cognome;

    @Column(name = "username", nullable = false, length = 20)
    private String username;

    @Column(name = "mail", nullable = false, length = 45)
    private String mail;

    @Column(name = "telefono", length = 15)
    private String telefono;

    @Lob
    @Column(name = "bio", columnDefinition = "TEXT")
    private String bio;

    @Column(name = "url_foto", length = 2000)
    private String urlFoto;

    @Column(name = "p_iva", columnDefinition = "CHAR(11)")
    private String partitaIva;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "id_organizzazione")
    @JsonIgnore
    private Organizzazione organizzazione;

    @Column(name = "data_nascita", nullable = false)
    private LocalDate dataNascita;

    @Column(name = "iban", length = 34)
    private String iban;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "organizzatore")
    @OrderBy("id ASC")
    private Set<CarteOrganizzatore> carte = new LinkedHashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "organizzatore")
    private Set<LinkOrganizzatore> link = new LinkedHashSet<>();

    @OneToOne(mappedBy = "admin")
    @JsonIgnore
    private Organizzazione orgAmministrata;

}