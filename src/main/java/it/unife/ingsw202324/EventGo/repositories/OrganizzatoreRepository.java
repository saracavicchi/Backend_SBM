package it.unife.ingsw202324.EventGo.repositories;

import it.unife.ingsw202324.EventGo.models.Organizzatore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OrganizzatoreRepository extends JpaRepository<Organizzatore, Long> {

    @Query("SELECT o FROM Organizzatore o WHERE o.codFiscale = :codFiscale")
    Optional<Organizzatore> findByCodFiscale(String codFiscale);
}