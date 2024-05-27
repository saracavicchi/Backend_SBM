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
@Table(name = "organizzatore")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Organizzatore {
    @Id /* Annotation per definire la primary key della tabella  */
    private Long id;
    private String cod_fiscale;
    private String nome;
    private String cognome;
    private String username;
    private String mail;
    private String telefono;
    private String bio;
    private String url_foto;
    private String p_iva;
    private String stato;
    private String provincia;
    private String città;
    private String cap;
    private String via;
    private String num_civico;
    private Long id_organizzazione;


}
