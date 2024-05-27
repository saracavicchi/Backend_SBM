package it.unife.ingsw202324.MicroservizioBase.repositories;

import it.unife.ingsw202324.MicroservizioBase.models.Organizzatore;
import org.springframework.data.jpa.repository.JpaRepository;
/* Classe che definisce il repository (database)  */
public interface OrganizzatoreRepository extends JpaRepository<Organizzatore, Long> {    //uno per ogni entità da portare sul frontend
    //bisogna solo indicare l'entità (classe che mappa la tabella) e il tipo della chiave primaria
    //dentro vuoto
}
