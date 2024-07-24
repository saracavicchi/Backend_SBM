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

@Transactional
@Service
public class OrganizzazioneService {

    private final OrganizzazioneRepository organizzazioneRepository;
    private final OrganizzatoreRepository organizzatoreRepository;

    @Autowired
    public OrganizzazioneService(OrganizzazioneRepository organizzazioneRepository, OrganizzatoreRepository organizzatoreRepository) {
        this.organizzazioneRepository = organizzazioneRepository;
        this.organizzatoreRepository = organizzatoreRepository;
    }

    public Organizzazione getOrganizzazione(Long idOrganizzazione) {
        return organizzazioneRepository.findById(idOrganizzazione).orElse(null);
    }

    public Organizzazione sanitizeOrganizzazione(Organizzazione organizzazione) {

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

    public Organizzazione creaOrganizzazione(Organizzazione organizzazione) {

        if (organizzazioneRepository.existsByMail(organizzazione.getMail())) {
            throw new DuplicatedEntityException("Indirizzo email già in uso");
        }

        if (organizzazione.getTelefono() != null && organizzazioneRepository.existsByTelefono(organizzazione.getTelefono())) {
            throw new DuplicatedEntityException("Numero di telefono già in uso");
        }

        if (organizzazione.getIban() != null && organizzazioneRepository.existsByIban(organizzazione.getIban())) {
            throw new DuplicatedEntityException("IBAN già in uso");
        }

        Organizzatore admin = organizzatoreRepository.findById(organizzazione.getAdmin().getId()).orElse(null);
        if (admin == null) {
            throw new RuntimeException("Organizzatore non trovato");
        }
        admin.setOrganizzazione(organizzazione);

        return organizzazioneRepository.save(organizzazione);

    }

    public void deleteOrganizzatore(Long idOrganizzatore) {

        Organizzatore organizzatore = organizzatoreRepository.findById(idOrganizzatore).orElse(null);
        if (organizzatore == null) {
            throw new RuntimeException("Organizzatore non trovato");
        }
        organizzatore.setOrganizzazione(null);
        organizzatoreRepository.save(organizzatore);

    }

    public void deleteOrganizzazione(Long id) {
        Organizzazione organizzazione = organizzazioneRepository.findById(id).orElse(null);
        if (organizzazione == null) {
            throw new RuntimeException("Organizzazione non trovata");
        }
        organizzazioneRepository.delete(organizzazione);
    }

    public void addOrganizzatore(String email, Long idOrganizzazione) {

        Organizzatore organizzatore = organizzatoreRepository.findByMail(email);
        if (organizzatore == null) {
            throw new IllegalArgumentException("Organizzatore inesistente");
        }

        if (organizzatore.getOrganizzazione() != null) {
            throw new IllegalArgumentException("L'organizzatore fa già parte di un'organizzazione");
        }

        Organizzazione organizzazione = organizzazioneRepository.findById(idOrganizzazione).orElse(null);
        if (organizzazione == null) {
            throw new RuntimeException("Organizzazione non trovata");
        }

        organizzatore.setOrganizzazione(organizzazione);
        organizzatoreRepository.save(organizzatore);

    }
}
