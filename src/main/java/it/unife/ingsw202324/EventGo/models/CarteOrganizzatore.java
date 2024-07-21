package it.unife.ingsw202324.EventGo.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

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
    private String nomeOrganizzatore;

    @Column(name = "cognome", nullable = false, length = 45)
    private String cognomeOrganizzatore;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_organizzatore", nullable = false)
    private Organizzatore organizzatore;

}