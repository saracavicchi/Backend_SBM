package it.unife.ingsw202324.MicroservizioBase.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/* Modello della taballa
 * Le annotation indicano che questa classe è un entity bean,
 * mappa una tabella che ha un nome fisico "my_table",
 * che i costruttori sono generici e auto creati dal plugin lombok,
 * e che lombok creerà anche tutti i getter e setter */
@Entity
@Table(name = "organizer")
@Data                       //indica che contiene dati, implementa già i getter e setter
@AllArgsConstructor
@NoArgsConstructor
public class Organizer {
    @Id /* Annotation per definire la primary key della tabella  */
    private String cod_fisc;
    private String name;
    private String surname;
    private String telephone;

}

//una classe per ogni tabella del database
