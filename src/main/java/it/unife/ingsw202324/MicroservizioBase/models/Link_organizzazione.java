package it.unife.ingsw202324.MicroservizioBase.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/* Modello della taballa
 * Le annotation indicano che questa classe è un entity bean,
 * mappa una tabella che ha un nome fisico "my_table",
 * che i costruttori sono generici e auto creati dal plugin lombok,
 * e che lombok creerà anche tutti i getter e setter */
@Entity
@Table(name = "link_organizzazione")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Link_organizzazione {
    @Id /* Annotation per definire la primary key della tabella  */
    private Long id;
    private String nome_social;
    private String url;
    private Long id_organizzazione;



}
