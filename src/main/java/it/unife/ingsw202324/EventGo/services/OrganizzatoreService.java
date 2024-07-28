package it.unife.ingsw202324.EventGo.services;

import it.unife.ingsw202324.EventGo.exceptions.DuplicatedEntityException;
import it.unife.ingsw202324.EventGo.models.Organizzatore;
import it.unife.ingsw202324.EventGo.models.CarteOrganizzatore;
import it.unife.ingsw202324.EventGo.models.LinkOrganizzatore;
import it.unife.ingsw202324.EventGo.models.Organizzazione;
import it.unife.ingsw202324.EventGo.repositories.OrganizzatoreRepository;
import it.unife.ingsw202324.EventGo.repositories.LinkOrganizzatoreRepository;
import it.unife.ingsw202324.EventGo.repositories.CarteOrganizzatoreRepository;
import it.unife.ingsw202324.EventGo.repositories.OrganizzazioneRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizzatoreService {

    // Repositories per i dati di Organizzatore, LinkOrganizzatore e CarteOrganizzatore
    private final OrganizzatoreRepository organizzatoreRepository;
    private final LinkOrganizzatoreRepository linkOrganizzatoreRepository;
    private final CarteOrganizzatoreRepository carteOrganizzatoreRepository;

    @Autowired
    public OrganizzatoreService(OrganizzatoreRepository organizzatoreRepository,
                                LinkOrganizzatoreRepository linkOrganizzatoreRepository,
                                CarteOrganizzatoreRepository carteOrganizzatoreRepository) {
        this.organizzatoreRepository = organizzatoreRepository;
        this.linkOrganizzatoreRepository = linkOrganizzatoreRepository;
        this.carteOrganizzatoreRepository = carteOrganizzatoreRepository;
    }

    @Transactional
    public void updateOrganizzatore(Long id, Organizzatore aggiornamenti) {
        Organizzatore organizzatore = organizzatoreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Organizzatore non trovato"));

        // Aggiorna i campi dell'Organizzatore
        if (aggiornamenti.getNome() != null) organizzatore.setNome(aggiornamenti.getNome());
        if (aggiornamenti.getCognome() != null) organizzatore.setCognome(aggiornamenti.getCognome());
        if (aggiornamenti.getMail() != null) organizzatore.setMail(aggiornamenti.getMail());
        if (aggiornamenti.getDataNascita() != null) organizzatore.setDataNascita(aggiornamenti.getDataNascita());
        if (aggiornamenti.getCodFiscale() != null) organizzatore.setCodFiscale(aggiornamenti.getCodFiscale());
        if (aggiornamenti.getUsername() != null) organizzatore.setUsername(aggiornamenti.getUsername());
        if (aggiornamenti.getTelefono() != null) organizzatore.setTelefono(aggiornamenti.getTelefono());
        if (aggiornamenti.getStato() != null) organizzatore.setStato(aggiornamenti.getStato());
        if (aggiornamenti.getProvincia() != null) organizzatore.setProvincia(aggiornamenti.getProvincia());
        if (aggiornamenti.getCittà() != null) organizzatore.setCittà(aggiornamenti.getCittà());
        if (aggiornamenti.getCap() != null) organizzatore.setCap(aggiornamenti.getCap());
        if (aggiornamenti.getVia() != null) organizzatore.setVia(aggiornamenti.getVia());
        if (aggiornamenti.getNumCivico() != null) organizzatore.setNumCivico(aggiornamenti.getNumCivico());
        if (aggiornamenti.getIban() != null) organizzatore.setIban(aggiornamenti.getIban());

        // Aggiorna le carte dell'organizzatore
        if (aggiornamenti.getCarte() != null) {
            for (CarteOrganizzatore carta : aggiornamenti.getCarte()) {
                if (carta.getId() != null) {
                    CarteOrganizzatore cartaEsistente = carteOrganizzatoreRepository.findById(carta.getId())
                            .orElseThrow(() -> new RuntimeException("Carta non trovata"));
                    // Aggiorna i dettagli della carta esistente
                    if (carta.getNumero() != null) cartaEsistente.setNumero(carta.getNumero());
                    if (carta.getDataScadenza() != null) cartaEsistente.setDataScadenza(carta.getDataScadenza());
                    if (carta.getCvv() != null) cartaEsistente.setCvv(carta.getCvv());
                    if (carta.getNome() != null) cartaEsistente.setNome(carta.getNome());
                    if (carta.getCognome() != null) cartaEsistente.setCognome(carta.getCognome());
                    carteOrganizzatoreRepository.save(cartaEsistente);
                }
            }
        }

        // Aggiorna i link social dell'organizzatore
        if (aggiornamenti.getLink() != null) {
            for (LinkOrganizzatore link : aggiornamenti.getLink()) {
                if (link.getId() != null) {
                    LinkOrganizzatore linkEsistente = linkOrganizzatoreRepository.findById(link.getId())
                            .orElseThrow(() -> new RuntimeException("Link social non trovato"));
                    // Aggiorna i dettagli del link social esistente
                    if (link.getUrl() != null) linkEsistente.setUrl(link.getUrl());
                    linkOrganizzatoreRepository.save(linkEsistente);
                }
            }
        }

        // Salva l'organizzatore aggiornato
        organizzatoreRepository.save(organizzatore);
    }

}
