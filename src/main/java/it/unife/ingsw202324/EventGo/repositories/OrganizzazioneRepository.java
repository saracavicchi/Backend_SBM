package it.unife.ingsw202324.EventGo.repositories;

import it.unife.ingsw202324.EventGo.models.Organizzatore;
import it.unife.ingsw202324.EventGo.models.Organizzazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * Repository per gestire le operazioni di accesso ai dati per l'entità Organizzazione.
 * Estende JpaRepository per fornire metodi CRUD e query personalizzate.
 */
public interface OrganizzazioneRepository extends JpaRepository<Organizzazione, Long> {

    /**
     * Verifica se esiste un'organizzazione con l'indirizzo email specificato.
     *
     * @param mail l'indirizzo email da verificare.
     * @param id l'ID dell'organizzazione da eventualmente escludere dalla verifica.
     * @return true se esiste un'organizzazione con l'email specificata, altrimenti false.
     */
    @Query("SELECT CASE WHEN COUNT(o) > 0 THEN true ELSE false END FROM Organizzazione o WHERE o.mail = :mail AND (:id IS NULL OR o.id <> :id)")
    boolean existsByMail(String mail, Optional<Long> id);

    /**
     * Verifica se esiste un'organizzazione con il numero di telefono specificato.
     *
     * @param telefono il numero di telefono da verificare.
     * @param id l'ID dell'organizzazione da eventualmente escludere dalla verifica.
     * @return true se esiste un'organizzazione con il telefono specificato, altrimenti false.
     */
    @Query("SELECT CASE WHEN COUNT(o) > 0 THEN true ELSE false END FROM Organizzazione o WHERE o.telefono = :telefono AND (:id IS NULL OR o.id <> :id)")
    boolean existsByTelefono(String telefono, Optional<Long> id);

    /**
     * Verifica se esiste un'organizzazione con l'IBAN specificato.
     *
     * @param iban l'IBAN da verificare.
     * @param id l'ID dell'organizzazione da eventualmente escludere dalla verifica.
     * @return true se esiste un'organizzazione con l'IBAN specificato, altrimenti false.
     */
    @Query("SELECT CASE WHEN COUNT(o) > 0 THEN true ELSE false END FROM Organizzazione o WHERE o.iban = :iban AND (:id IS NULL OR o.id <> :id)")
    boolean existsByIban(String iban, Optional<Long> id);

    /**
     * Verifica se esiste un'organizzazione con l'amministratore specificato.
     *
     * @param admin l'amministratore da verificare.
     * @param id l'ID dell'organizzazione da eventualmente escludere dalla verifica.
     * @return true se esiste un'organizzazione con l'amministratore specificato, altrimenti false.
     */
    @Query("SELECT CASE WHEN COUNT(o) > 0 THEN true ELSE false END FROM Organizzazione o WHERE o.admin = :admin AND (:id IS NULL OR o.id <> :id)")
    boolean existsByAdmin(Organizzatore admin, Optional<Long> id);
}