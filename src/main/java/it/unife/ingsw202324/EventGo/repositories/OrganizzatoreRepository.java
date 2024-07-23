package it.unife.ingsw202324.EventGo.repositories;

import it.unife.ingsw202324.EventGo.models.Organizzatore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * Interfaccia repository per l'entità Organizzatore.
 * Estende JpaRepository per fornire funzionalità CRUD (Create, Read, Update, Delete)
 * per la gestione degli organizzatori nel database.
 *
 * JpaRepository richiede la specifica dell'entità da gestire, in questo caso Organizzatore,
 * e il tipo della chiave primaria, qui Long, per abilitare le operazioni di persistenza.
 */
public interface OrganizzatoreRepository extends JpaRepository<Organizzatore, Long> {

}