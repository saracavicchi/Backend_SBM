package it.unife.ingsw202324.EventGo.services;

import it.unife.ingsw202324.EventGo.exceptions.DuplicatedEntityException;
import it.unife.ingsw202324.EventGo.models.Organizzatore;
import it.unife.ingsw202324.EventGo.models.Organizzazione;
import it.unife.ingsw202324.EventGo.repositories.OrganizzatoreRepository;
import it.unife.ingsw202324.EventGo.repositories.OrganizzazioneRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servizio per la gestione delle operazioni relative alle organizzazioni.
 */
@Transactional
@Service
public class OrganizzazioneService {

    // Repository per l'accesso ai dati delle organizzazioni
    private final OrganizzazioneRepository organizzazioneRepository;

    // Repository per l'accesso ai dati degli organizzatori
    private final OrganizzatoreRepository organizzatoreRepository;

    /**
     * Costruttore del servizio con iniezione delle dipendenze.
     *
     * @param organizzazioneRepository repository per la gestione delle organizzazioni
     * @param organizzatoreRepository  repository per la gestione degli organizzatori
     */
    @Autowired
    public OrganizzazioneService(OrganizzazioneRepository organizzazioneRepository, OrganizzatoreRepository organizzatoreRepository) {
        this.organizzazioneRepository = organizzazioneRepository;
        this.organizzatoreRepository = organizzatoreRepository;
    }

    /**
     * Recupera un'organizzazione dato il suo ID.
     *
     * @param idOrganizzazione l'ID dell'organizzazione
     * @return l'organizzazione trovata, o null se non esiste
     */
    public Organizzazione getOrganizzazione(Long idOrganizzazione) {
        return organizzazioneRepository.findById(idOrganizzazione).orElse(null);
    }

    /**
     * Pulisce i campi vuoti di un'istanza di Organizzazione.
     *
     * @param organizzazione l'istanza di Organizzazione da sanitizzare
     * @return l'istanza sanitizzata di Organizzazione
     */
    public Organizzazione sanitizeOrganizzazione(Organizzazione organizzazione) {

        // Verifica se il campo è vuoto e lo imposta a null

        if (organizzazione.getNome() != null && organizzazione.getNome().isEmpty()) {
            organizzazione.setNome(null);
        }
        if (organizzazione.getDescrizione() != null && organizzazione.getDescrizione().isEmpty()) {
            organizzazione.setDescrizione(null);
        }
        if (organizzazione.getTelefono() != null && organizzazione.getTelefono().isEmpty()) {
            organizzazione.setTelefono(null);
        }
        if (organizzazione.getStato() != null && organizzazione.getStato().isEmpty()) {
            organizzazione.setStato(null);
        }
        if (organizzazione.getProvincia() != null && organizzazione.getProvincia().isEmpty()) {
            organizzazione.setProvincia(null);
        }
        if (organizzazione.getCittà() != null && organizzazione.getCittà().isEmpty()) {
            organizzazione.setCittà(null);
        }
        if (organizzazione.getCap() != null && organizzazione.getCap().isEmpty()) {
            organizzazione.setCap(null);
        }
        if (organizzazione.getVia() != null && organizzazione.getVia().isEmpty()) {
            organizzazione.setVia(null);
        }
        if (organizzazione.getNumCivico() != null && organizzazione.getNumCivico().isEmpty()) {
            organizzazione.setNumCivico(null);
        }
        if (organizzazione.getIban() != null && organizzazione.getIban().isEmpty()) {
            organizzazione.setIban(null);
        }

        return organizzazione;
    }

    /**
     * Salva un'organizzazione dopo aver effettuato le verifiche necessarie.
     *
     * @param organizzazione l'istanza di Organizzazione da salvare
     * @return l'istanza salvata di Organizzazione
     * @throws DuplicatedEntityException se un'entity con gli stessi dati esiste già
     */
    public Organizzazione salvaOrganizzazione(Organizzazione organizzazione, Optional<Long> id, Long idAdmin) {

        if (organizzazioneRepository.existsByMail(organizzazione.getMail(), id.isPresent() ? id : Optional.empty())) {
            throw new DuplicatedEntityException("Indirizzo email già in uso");
        }

        if (organizzazione.getTelefono() != null && organizzazioneRepository.existsByTelefono(organizzazione.getTelefono(), id.isPresent() ? id : Optional.empty())) {
            throw new DuplicatedEntityException("Numero di telefono già in uso");
        }

        if (organizzazione.getIban() != null && organizzazioneRepository.existsByIban(organizzazione.getIban(), id.isPresent() ? id : Optional.empty())) {
            throw new DuplicatedEntityException("IBAN già in uso");
        }

        Organizzatore admin = organizzatoreRepository.findById(idAdmin).orElse(null);
        if (admin == null) {
            throw new RuntimeException("Organizzatore non trovato");
        }
        if(id.isPresent()){
            organizzazione.setAdmin(admin);
        }
        admin.setOrganizzazione(organizzazione);


        return organizzazioneRepository.save(organizzazione);

    }

    /**
     * Rimuove l'appartenenza di un organizzatore a un'organizzazione.
     *
     * @param idOrganizzatore l'ID dell'organizzatore da rimuovere
     * @throws RuntimeException se l'organizzatore non viene trovato
     */
    public void deleteOrganizzatore(Long idOrganizzatore) {

        // Trova l'organizzatore per ID
        Organizzatore organizzatore = organizzatoreRepository.findById(idOrganizzatore).orElse(null);
        if (organizzatore == null) {
            throw new RuntimeException("Organizzatore non trovato");
        }

        // Setta a null l'organizzazione e salva l'organizzatore
        organizzatore.setOrganizzazione(null);
        organizzatoreRepository.save(organizzatore);

    }

    /**
     * Elimina un'organizzazione dato il suo ID.
     *
     * @param id l'ID dell'organizzazione da eliminare
     * @throws RuntimeException se l'organizzazione non viene trovata
     */
    public void deleteOrganizzazione(Long id) {
        // Trova l'organizzazione per ID
        Organizzazione organizzazione = organizzazioneRepository.findById(id).orElse(null);
        if (organizzazione == null) {
            throw new RuntimeException("Organizzazione non trovata");
        }
        // Elimina l'organizzazione (e i dati collegati)
        organizzazioneRepository.delete(organizzazione);
    }

    /**
     * Aggiunge un organizzatore a un'organizzazione.
     *
     * @param email             l'email dell'organizzatore da aggiungere
     * @param idOrganizzazione  l'ID dell'organizzazione a cui aggiungere l'organizzatore
     * @throws IllegalArgumentException se l'organizzatore non esiste
     * @throws DuplicatedEntityException se l'organizzatore appartiene già a un'organizzazione
     * @throws RuntimeException se l'organizzazione non viene trovata
     */
    public void addOrganizzatore(String email, Long idOrganizzazione) {

        // Trova l'organizzatore per email
        Organizzatore organizzatore = organizzatoreRepository.findByMail(email);
        if (organizzatore == null) {
            throw new IllegalArgumentException("Organizzatore inesistente");
        }

        // Verifica se l'organizzatore appartiene già a un'organizzazione
        if (organizzatore.getOrganizzazione() != null) {
            throw new DuplicatedEntityException("L'organizzatore fa già parte di un'organizzazione");
        }

        // Trova l'organizzazione per ID
        Organizzazione organizzazione = organizzazioneRepository.findById(idOrganizzazione).orElse(null);
        if (organizzazione == null) {
            throw new RuntimeException("Organizzazione non trovata");
        }

        // Setta l'organizzazione e aggiorna l'organizzatore
        organizzatore.setOrganizzazione(organizzazione);
        organizzatoreRepository.save(organizzatore);

    }
}
