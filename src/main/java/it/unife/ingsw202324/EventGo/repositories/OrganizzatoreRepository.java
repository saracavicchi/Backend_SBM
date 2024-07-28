package it.unife.ingsw202324.EventGo.repositories;

import it.unife.ingsw202324.EventGo.models.Organizzatore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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

    @Query("SELECT CASE WHEN COUNT(o) > 0 THEN true ELSE false END FROM Organizzatore o WHERE o.mail = :mail AND (:id IS NULL OR o.id <> :id)")
    boolean existsByMail(String mail, Long id);

    @Query("SELECT CASE WHEN COUNT(o) > 0 THEN true ELSE false END FROM Organizzatore o WHERE o.telefono = :telefono AND (:id IS NULL OR o.id <> :id)")
    boolean existsByTelefono(String telefono, Long id);

    @Query("SELECT CASE WHEN COUNT(o) > 0 THEN true ELSE false END FROM Organizzatore o WHERE o.iban = :iban AND (:id IS NULL OR o.id <> :id)")
    boolean existsByIban(String iban, Long id);

    @Query("SELECT CASE WHEN COUNT(o) > 0 THEN true ELSE false END FROM Organizzatore o WHERE o.codFiscale = :codFiscale AND (:id IS NULL OR o.id <> :id)")
    boolean existsByCodFiscale(String codFiscale, Long id);

    @Query("SELECT CASE WHEN COUNT(o) > 0 THEN true ELSE false END FROM Organizzatore o WHERE o.username = :username AND (:id IS NULL OR o.id <> :id)")
    boolean existsByUsername(String username, Long id);

    @Query("SELECT CASE WHEN COUNT(o) > 0 THEN true ELSE false END FROM Organizzatore o WHERE o.partitaIva = :partitaIva AND (:id IS NULL OR o.id <> :id)")
    boolean existsByPartitaIva(String partitaIva, Long id);
}