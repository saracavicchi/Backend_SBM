package it.unife.ingsw202324.EventGo.repositories;

import it.unife.ingsw202324.EventGo.models.LinkOrganizzazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * Repository per gestire le operazioni di accesso ai dati per l'entità LinkOrganizzazione.
 * Estende JpaRepository per fornire metodi CRUD e query personalizzate.
 */
public interface LinkOrganizzazioneRepository extends JpaRepository<LinkOrganizzazione, Long> {

    /**
     * Verifica se esiste un link con il nome social specificato per un'organizzazione specificata.
     *
     * @param nomeSocial il nome social da verificare.
     * @param idOrganizzazione l'ID dell'organizzazione a cui il link deve essere associato.
     * @return true se esiste un link con il nome social specificato per l'organizzazione specificata, altrimenti false.
     */
    @Query("SELECT l FROM LinkOrganizzazione l WHERE l.nomeSocial = :nomeSocial AND l.organizzazione.id = :idOrganizzazione")
    Optional<LinkOrganizzazione> findByNomeSocialAndOrganizzazioneId(String nomeSocial, Long idOrganizzazione);
}