package it.unife.ingsw202324.EventGo.repositories;

import it.unife.ingsw202324.EventGo.models.CarteOrganizzatore;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interfaccia repository per l'entità CarteOrganizzatore.
 * Estende JpaRepository, fornendo un'ampia gamma di operazioni CRUD (Create, Read, Update, Delete)
 * per la gestione delle carte degli organizzatori nel database.
 *
 * JpaRepository richiede la specifica dell'entità da gestire, in questo caso CarteOrganizzatore,
 * e il tipo della chiave primaria, qui Long.
 */
public interface CarteOrganizzatoreRepository extends JpaRepository<CarteOrganizzatore, Long> {
    // Qui possono essere aggiunti metodi personalizzati per query specifiche,
    // sfruttando le convenzioni di nome di Spring Data JPA o l'annotazione @Query per query JPQL personalizzate.
}