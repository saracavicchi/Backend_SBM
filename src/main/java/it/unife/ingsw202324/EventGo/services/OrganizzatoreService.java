package it.unife.ingsw202324.EventGo.services;

import it.unife.ingsw202324.EventGo.repositories.OrganizzatoreRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Transactional
@Service
public class OrganizzatoreService {

    private final OrganizzatoreRepository organizzatoreRepository;

    @Autowired
    public OrganizzatoreService(OrganizzatoreRepository organizzatoreRepository) {
        this.organizzatoreRepository = organizzatoreRepository;
    }

    public Long hasOrganizzazione(Long idOrganizzatore) {
        return organizzatoreRepository.findById(idOrganizzatore).map(organizzatore -> Objects.nonNull(organizzatore.getOrganizzazione()) ? organizzatore.getOrganizzazione().getId() : null).orElse(null);
    }
}
