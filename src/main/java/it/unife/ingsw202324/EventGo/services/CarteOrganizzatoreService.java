package it.unife.ingsw202324.EventGo.services;

import it.unife.ingsw202324.EventGo.models.CarteOrganizzatore;
import it.unife.ingsw202324.EventGo.models.Organizzatore;
import it.unife.ingsw202324.EventGo.repositories.CarteOrganizzatoreRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service per la gestione delle carte di credito degli organizzatori.
 * Fornisce metodi per modificare e gestire le carte di credito associate agli organizzatori.
 */
@Transactional
@Service
public class CarteOrganizzatoreService {

    private final CarteOrganizzatoreRepository carteOrganizzatoreRepository;

    /**
     * Costruttore per l'iniezione del repository CarteOrganizzatoreRepository.
     *
     * @param carteOrganizzatoreRepository il repository per la gestione delle carte di credito degli organizzatori
     */
    @Autowired
    public CarteOrganizzatoreService(CarteOrganizzatoreRepository carteOrganizzatoreRepository) {
        this.carteOrganizzatoreRepository = carteOrganizzatoreRepository;
    }


    /**
     * Modifica le informazioni di una carta di credito associata a un organizzatore.
     * Se la carta esiste, aggiorna le sue informazioni. Se la carta non esiste e il numero non è vuoto, aggiunge la carta all'organizzatore.
     * Se il numero della carta è vuoto, rimuove la carta dall'organizzatore e la elimina dal repository.
     *
     * @param organizzatore l'organizzatore a cui la carta è associata
     * @param carta la carta di credito da modificare
     */
    public void modificaCartaOrganizzatore(Organizzatore organizzatore, CarteOrganizzatore carta) {

        // Verifica se la carta ha un ID (quindi esiste già)
        if (carta.getId() != null) {

            // Recupera la carta esistente dal repository
            CarteOrganizzatore cartaEsistente = carteOrganizzatoreRepository.findById(carta.getId()).orElse(null);

            // Lancia un'eccezione se la carta non è trovata
            if (cartaEsistente == null) {
                throw new RuntimeException("Carta non trovata");
            }

            // Se il numero della carta è vuoto, rimuove la carta dall'organizzatore e la elimina dal repository
            if (carta.getNumero().isEmpty()) {
                organizzatore.getCarte().remove(cartaEsistente);
                carteOrganizzatoreRepository.delete(cartaEsistente);
            } else {
                // Aggiorna le informazioni della carta esistente
                cartaEsistente.setNumero(carta.getNumero());
                cartaEsistente.setDataScadenza(carta.getDataScadenza());
                cartaEsistente.setCvv(carta.getCvv());
                cartaEsistente.setNome(carta.getNome());
                cartaEsistente.setCognome(carta.getCognome());
                cartaEsistente.setOrganizzatore(organizzatore);
                // Salva la carta aggiornata nel repository
                carteOrganizzatoreRepository.save(cartaEsistente);
            }

        } else if (!carta.getNumero().isEmpty()) {
            // Se la carta non ha un ID e il numero non è vuoto, aggiunge la carta all'organizzatore (viene creata)
            organizzatore.getCarte().add(carta);
        }

    }
}
