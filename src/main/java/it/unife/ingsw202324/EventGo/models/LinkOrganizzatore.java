package it.unife.ingsw202324.EventGo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * Classe che rappresenta il link di un organizzatore nel sistema.
 */
@Getter
@Setter
@Entity
@Table(name = "link_organizzatore")
public class LinkOrganizzatore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nome_social", nullable = false, length = 20)
    private String nomeSocial;

    @Column(name = "url", nullable = false, length = 2000)
    private String url;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_organizzatore", nullable = false)
    @JsonIgnore
    private Organizzatore organizzatore;

}