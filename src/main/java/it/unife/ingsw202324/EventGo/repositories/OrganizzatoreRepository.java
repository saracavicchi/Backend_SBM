package it.unife.ingsw202324.EventGo.repositories;

import it.unife.ingsw202324.EventGo.models.Organizzatore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface OrganizzatoreRepository extends JpaRepository<Organizzatore, Long> {
    boolean existsByMail(String mail);
    Optional<Organizzatore> findByMail(String mail);
}
