package it.unife.ingsw202324.EventGo.repositories;

import it.unife.ingsw202324.EventGo.models.Organizzatore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository per gestire le operazioni di accesso ai dati per l'entità Organizzatore.
 * Estende JpaRepository per fornire metodi CRUD e query personalizzate.
 */
public interface OrganizzatoreRepository extends JpaRepository<Organizzatore, Long> {

    /**
     * Trova un organizzatore in base all'indirizzo email specificato.
     *
     * @param email l'indirizzo email dell'organizzatore da cercare.
     * @return l'organizzatore trovato, o null se nessun organizzatore corrisponde all'email specificata.
     */
    Organizzatore findByMail(String email);
}