package it.unife.ingsw202324.EventGo.services;

import it.unife.ingsw202324.EventGo.models.Organizzatore;
import it.unife.ingsw202324.EventGo.repositories.OrganizzatoreRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Servizio per la gestione degli organizzatori.
 * Fornisce funzionalità per cercare o creare nuovi organizzatori nel sistema.
 */
@Service
public class OrganizzatoreService {

    private final OrganizzatoreRepository organizzatoreRepository;

    /**
     * Costruttore con iniezione della dipendenza verso OrganizzatoreRepository.
     * @param organizzatoreRepository Il repository per gli organizzatori, utilizzato per accedere al database.
     */
    @Autowired
    public OrganizzatoreService(OrganizzatoreRepository organizzatoreRepository) {
        this.organizzatoreRepository = organizzatoreRepository;
    }

    /**
     * Cerca un organizzatore nel database utilizzando il suo ID. Se non presente, lo crea.
     * Questo metodo garantisce che ogni organizzatore sia univocamente identificato nel sistema.
     *
     * @param newOrganizzatore L'organizzatore da cercare o creare.
     */
    @Transactional
    public void findOrCreateOrganizzatore(Organizzatore newOrganizzatore) {
        Optional<Organizzatore> organizzatoreOptional = organizzatoreRepository.findById(newOrganizzatore.getId());
        if (organizzatoreOptional.isPresent()) {
            System.out.println("Organizzatore già presente");
        } else {
            organizzatoreRepository.save(newOrganizzatore);
        }
    }
}