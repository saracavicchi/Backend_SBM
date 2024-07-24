package it.unife.ingsw202324.EventGo.repositories;

import it.unife.ingsw202324.EventGo.models.Organizzatore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrganizzatoreRepository extends JpaRepository<Organizzatore, Long> {
    Organizzatore findByMail(String email);
}