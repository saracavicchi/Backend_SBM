package it.unife.ingsw202324.EventGo.repositories;

import it.unife.ingsw202324.EventGo.models.Organizzatore;
import it.unife.ingsw202324.EventGo.models.Organizzazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OrganizzazioneRepository extends JpaRepository<Organizzazione, Long> {

    @Query("SELECT CASE WHEN COUNT(o) > 0 THEN true ELSE false END FROM Organizzazione o WHERE o.admin = :admin AND (:id IS NULL OR o.id <> :id)")
    boolean existsByAdmin(Organizzatore admin, Optional<Long> id);

    @Query("SELECT CASE WHEN COUNT(o) > 0 THEN true ELSE false END FROM Organizzazione o WHERE o.mail = :mail AND (:id IS NULL OR o.id <> :id)")
    boolean existsByMail(String mail, Optional<Long> id);

    @Query("SELECT CASE WHEN COUNT(o) > 0 THEN true ELSE false END FROM Organizzazione o WHERE o.telefono = :telefono AND (:id IS NULL OR o.id <> :id)")
    boolean existsByTelefono(String telefono, Optional<Long> id);

    @Query("SELECT CASE WHEN COUNT(o) > 0 THEN true ELSE false END FROM Organizzazione o WHERE o.iban = :iban AND (:id IS NULL OR o.id <> :id)")
    boolean existsByIban(String iban, Optional<Long> id);
}