package it.unife.ingsw202324.EventGo.repositories;

import it.unife.ingsw202324.EventGo.models.Organizzazione;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * Interfaccia repository per l'entità Organizzazione.
 * Estende JpaRepository, fornendo un'ampia gamma di operazioni CRUD (Create, Read, Update, Delete)
 * per la gestione delle carte degli organizzatori nel database.
 *
 * JpaRepository richiede la specifica dell'entità da gestire, in questo caso Organizzazione,
 * e il tipo della chiave primaria, qui Long.
 */
public interface OrganizzazioneRepository extends JpaRepository<Organizzazione, Long> {
}