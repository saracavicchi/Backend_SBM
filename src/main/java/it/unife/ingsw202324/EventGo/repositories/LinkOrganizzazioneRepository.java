package it.unife.ingsw202324.EventGo.repositories;

import it.unife.ingsw202324.EventGo.models.LinkOrganizzazione;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interfaccia repository per l'entità LinkOrganizzazione.
 * Estende JpaRepository per fornire funzionalità CRUD (Create, Read, Update, Delete)
 * per la gestione dei link delle organizzazioni nel database.
 *
 * JpaRepository richiede la specifica dell'entità da gestire, in questo caso LinkOrganizzazione,
 * e il tipo della chiave primaria, qui Long, per abilitare le operazioni di persistenza.
 */
public interface LinkOrganizzazioneRepository extends JpaRepository<LinkOrganizzazione, Long> {
    // Qui possono essere definiti metodi personalizzati per eseguire query specifiche
    // che non sono direttamente supportate dalle operazioni CRUD standard di JpaRepository.
}