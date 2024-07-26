package it.unife.ingsw202324.EventGo.services;

import it.unife.ingsw202324.EventGo.repositories.OrganizzatoreRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Servizio per la gestione delle operazioni relative agli organizzatori.
 */
@Transactional
@Service
public class OrganizzatoreService {

    // Repository per l'accesso ai dati degli organizzatori
    private final OrganizzatoreRepository organizzatoreRepository;

    /**
     * Costruttore del servizio con iniezione delle dipendenze.
     *
     * @param organizzatoreRepository repository per la gestione degli organizzatori
     */
    @Autowired
    public OrganizzatoreService(OrganizzatoreRepository organizzatoreRepository) {
        this.organizzatoreRepository = organizzatoreRepository;
    }

    /**
     * Verifica se un organizzatore appartiene a un'organizzazione.
     *
     * @param idOrganizzatore l'ID dell'organizzatore
     * @return l'ID dell'organizzazione a cui appartiene l'organizzatore, o null se non appartiene a nessuna
     */
    public Long hasOrganizzazione(Long idOrganizzatore) {
        // Trova l'organizzatore per ID e verifica se appartiene a un'organizzazione
        return organizzatoreRepository.findById(idOrganizzatore)
                .map(organizzatore ->
                        // Verifica se l'organizzatore ha un'organizzazione associata
                        Objects.nonNull(organizzatore.getOrganizzazione())
                                ? organizzatore.getOrganizzazione().getId()
                                : null
                )
                // Ritorna null se l'organizzatore non esiste
                .orElse(null);
    }
}
