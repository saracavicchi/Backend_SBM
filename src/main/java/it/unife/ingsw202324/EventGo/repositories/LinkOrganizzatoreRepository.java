package it.unife.ingsw202324.EventGo.repositories;

import it.unife.ingsw202324.EventGo.models.LinkOrganizzatore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * Repository per gestire le operazioni di accesso ai dati per l'entità LinkOrganizzatore.
 * Estende JpaRepository per fornire metodi CRUD e query personalizzate.
 */
public interface LinkOrganizzatoreRepository extends JpaRepository<LinkOrganizzatore, Long> {

    /**
     * Verifica se esiste un link con il nome social specificato per un organizzatore specificato.
     *
     * @param nomeSocial il nome social da verificare.
     * @param idOrganizzatore l'ID dell'organizzazione a cui il link deve essere associato.
     * @return true se esiste un link con il nome social specificato per l'organizzatore specificato, altrimenti false.
     */
    @Query("SELECT l FROM LinkOrganizzatore l WHERE l.nomeSocial = :nomeSocial AND l.organizzatore.id = :idOrganizzatore")
    Optional<LinkOrganizzatore> findByNomeSocialAndOrganizzatoreId(String nomeSocial, Long idOrganizzatore);
}