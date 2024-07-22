package it.unife.ingsw202324.EventGo.services;

import it.unife.ingsw202324.EventGo.models.Organizzatore;
import it.unife.ingsw202324.EventGo.models.Organizzazione;
import it.unife.ingsw202324.EventGo.repositories.OrganizzatoreRepository;
import it.unife.ingsw202324.EventGo.repositories.OrganizzazioneRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Transactional
@Service
public class OrganizzazioneService {

    private final OrganizzazioneRepository organizzazioneRepository;

    @Autowired
    public OrganizzazioneService(OrganizzazioneRepository organizzazioneRepository) {
        this.organizzazioneRepository = organizzazioneRepository;
    }

    public Organizzazione getOrganizzazione(Long idOrganizzazione) {
        return organizzazioneRepository.findById(idOrganizzazione).orElse(null);
    }

}
