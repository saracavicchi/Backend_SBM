package it.unife.ingsw202324.EventGo.repositories;

import it.unife.ingsw202324.EventGo.models.LinkOrganizzazione;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository per gestire le operazioni di accesso ai dati per l'entità LinkOrganizzazione.
 * Estende JpaRepository per fornire metodi CRUD e query personalizzate.
 */
public interface LinkOrganizzazioneRepository extends JpaRepository<LinkOrganizzazione, Long> {
}