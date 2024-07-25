package it.unife.ingsw202324.EventGo.repositories;

import it.unife.ingsw202324.EventGo.models.LinkOrganizzazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LinkOrganizzazioneRepository extends JpaRepository<LinkOrganizzazione, Long> {

    @Query("SELECT l FROM LinkOrganizzazione l WHERE l.nomeSocial = :nomeSocial AND l.organizzazione.id = :idOrganizzazione")
    Optional<LinkOrganizzazione> findByNomeSocialAndOrganizzazioneId(String nomeSocial, Long idOrganizzazione);
}