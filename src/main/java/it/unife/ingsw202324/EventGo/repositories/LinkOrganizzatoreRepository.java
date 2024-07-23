package it.unife.ingsw202324.EventGo.repositories;

import it.unife.ingsw202324.EventGo.models.LinkOrganizzatore;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * Interfaccia repository per l'entità LinkOrganizzatore.
 * Estende JpaRepository, fornendo un'ampia gamma di operazioni CRUD (Create, Read, Update, Delete)
 * per la gestione delle carte degli organizzatori nel database.
 *
 * JpaRepository richiede la specifica dell'entità da gestire, in questo caso LinkOrganizzatore,
 * e il tipo della chiave primaria, qui Long.
 */
public interface LinkOrganizzatoreRepository extends JpaRepository<LinkOrganizzatore, Long> {
}