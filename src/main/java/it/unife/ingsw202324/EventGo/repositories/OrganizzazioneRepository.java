package it.unife.ingsw202324.EventGo.repositories;

import it.unife.ingsw202324.EventGo.models.Organizzazione;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository per gestire le operazioni di accesso ai dati per l'entità Organizzazione.
 * Estende JpaRepository per fornire metodi CRUD e query personalizzate.
 */
public interface OrganizzazioneRepository extends JpaRepository<Organizzazione, Long> {

    /**
     * Verifica se esiste un'organizzazione con l'indirizzo email specificato.
     *
     * @param mail l'indirizzo email da verificare.
     * @return true se esiste un'organizzazione con l'email specificata, altrimenti false.
     */
    boolean existsByMail(String mail);

    /**
     * Verifica se esiste un'organizzazione con il numero di telefono specificato.
     *
     * @param telefono il numero di telefono da verificare.
     * @return true se esiste un'organizzazione con il telefono specificato, altrimenti false.
     */
    boolean existsByTelefono(String telefono);

    /**
     * Verifica se esiste un'organizzazione con l'IBAN specificato.
     *
     * @param iban l'IBAN da verificare.
     * @return true se esiste un'organizzazione con l'IBAN specificato, altrimenti false.
     */
    boolean existsByIban(String iban);
}