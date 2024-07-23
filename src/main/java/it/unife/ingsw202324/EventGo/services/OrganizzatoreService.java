package it.unife.ingsw202324.EventGo.services;
import it.unife.ingsw202324.EventGo.models.Organizzatore;
import it.unife.ingsw202324.EventGo.repositories.OrganizzatoreRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class OrganizzatoreService {

    private final OrganizzatoreRepository organizzatoreRepository;

    @Autowired
    public OrganizzatoreService(OrganizzatoreRepository organizzatoreRepository) {
        this.organizzatoreRepository = organizzatoreRepository;
    }

    // ricerca utente nel database, se non è presente significa che è un nuovo utente: salva i dati e lo crea
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