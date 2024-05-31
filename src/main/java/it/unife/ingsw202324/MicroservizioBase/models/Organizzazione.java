package it.unife.ingsw202324.MicroservizioBase.models;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/* Modello della taballa
 * Le annotation indicano che questa classe è un entity bean,
 * mappa una tabella che ha un nome fisico "my_table",
 * che i costruttori sono generici e auto creati dal plugin lombok,
 * e che lombok creerà anche tutti i getter e setter */
@Entity
@Table(name = "organizzazione")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Organizzazione {
    @Id /* Annotation per definire la primary key della tabella  */
    private Long id;
    private String nome;
    private String descrizione;
    private String mail;
    private String telefono;
    private String url_foto;
    private String stato;
    private String provincia;
    private String città;
    private String cap;
    private String via;
    private String num_civico;

    @OneToMany
    private List<Organizzatore> organizzatori;
    @OneToOne
    private Admin admin;
    @OneToMany
    private List<Link_organizzazione> link_organizzazione;



}
