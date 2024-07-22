package it.unife.ingsw202324.EventGo.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "organizzatore")
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

    @Column(name = "data_nascita")
    private LocalDate dataNascita;

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

    @JsonProperty("pIva")
    @Column(name = "p_iva", columnDefinition = "CHAR(11)")
    private String pIva;

    @Column(name = "iban", length = 34)
    private String iban;

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
    private Organizzazione organizzazione;

    @OneToMany(mappedBy = "organizzatore")
    private Set<CarteOrganizzatore> carte = new LinkedHashSet<>();

    @OneToMany(mappedBy = "organizzatore")
    private Set<LinkOrganizzatore> link = new LinkedHashSet<>();

    @OneToOne(mappedBy = "admin")
    private Organizzazione orgAmministrata;

}