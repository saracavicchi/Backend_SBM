package it.unife.ingsw202324.EventGo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

/**
 * Classe che rappresenta le carte di un organizzatore nel sistema.
 */
@Getter
@Setter
@Entity
@Table(name = "carte_organizzatore")
public class CarteOrganizzatore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "numero", nullable = false, columnDefinition = "CHAR(16)")
    private String numero;

    @Column(name = "data_scadenza", nullable = false)
    private LocalDate dataScadenza;

    @Column(name = "cvv", nullable = false, columnDefinition = "CHAR(3)")
    private String cvv;

    @Column(name = "nome", nullable = false, length = 45)
    private String nome;

    @Column(name = "cognome", nullable = false, length = 45)
    private String cognome;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_organizzatore", nullable = false)
    @JsonIgnore
    private Organizzatore organizzatore;

    public CarteOrganizzatore() {
    }

    public CarteOrganizzatore(Long id, String numero, String cvv, String nome, String cognome, Organizzatore organizzatore) {
        this.id = id;
        this.numero = numero;
        this.cvv = cvv;
        this.nome = nome;
        this.cognome = cognome;
        this.organizzatore = organizzatore;
    }

}