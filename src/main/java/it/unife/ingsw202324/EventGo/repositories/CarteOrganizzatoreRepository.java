package it.unife.ingsw202324.EventGo.repositories;

import it.unife.ingsw202324.EventGo.models.CarteOrganizzatore;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository per gestire le operazioni di accesso ai dati per l'entità CarteOrganizzatore.
 * Estende JpaRepository per fornire metodi CRUD e query personalizzate.
 */
public interface CarteOrganizzatoreRepository extends JpaRepository<CarteOrganizzatore, Long> {
}