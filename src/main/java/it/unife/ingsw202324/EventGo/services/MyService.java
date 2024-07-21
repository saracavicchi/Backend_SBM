package it.unife.ingsw202324.EventGo.services;

import it.unife.ingsw202324.EventGo.models.Organizzatore;
import it.unife.ingsw202324.EventGo.repositories.OrganizzatoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/* Service class per interrogare il db  */
@Service
public class MyService {

    private final OrganizzatoreRepository organizzatoreRepository;

    @Autowired
    public MyService(OrganizzatoreRepository organizzatoreRepository) {
        this.organizzatoreRepository = organizzatoreRepository;
    }

    /* Metodo che effettua una select all sulla tabella Mysql */
    public List<Organizzatore> getAll() {
        return organizzatoreRepository.findAll();
    }


}
