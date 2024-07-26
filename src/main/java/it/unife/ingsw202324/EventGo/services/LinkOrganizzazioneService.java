package it.unife.ingsw202324.EventGo.services;

import it.unife.ingsw202324.EventGo.models.LinkOrganizzazione;
import it.unife.ingsw202324.EventGo.models.Organizzazione;
import it.unife.ingsw202324.EventGo.repositories.LinkOrganizzazioneRepository;
import it.unife.ingsw202324.EventGo.repositories.OrganizzazioneRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Servizio per la gestione dei link delle organizzazioni.
 */
@Transactional
@Service
public class LinkOrganizzazioneService {

    private final LinkOrganizzazioneRepository linkOrganizzazioneRepository;
    private final OrganizzazioneRepository organizzazioneRepository;

    /**
     * Costruttore per l'iniezione delle dipendenze.
     *
     * @param linkOrganizzazioneRepository repository per i link delle organizzazioni
     * @param organizzazioneRepository repository per le organizzazioni
     */
    @Autowired
    public LinkOrganizzazioneService(LinkOrganizzazioneRepository linkOrganizzazioneRepository, OrganizzazioneRepository organizzazioneRepository) {
        this.linkOrganizzazioneRepository = linkOrganizzazioneRepository;
        this.organizzazioneRepository = organizzazioneRepository;
    }

    /**
     * Aggiunge un nuovo link all'organizzazione specificata.
     *
     * @param organizzazione l'organizzazione a cui aggiungere il link
     * @param nomeSocial il nome del social network
     * @param url l'URL del social network
     */
    public void aggiungiLinkOrganizzazione(Organizzazione organizzazione, String nomeSocial, String url) {
        if (url.isEmpty()) {
            return;
        }

        // Aggiungi il nuovo link
        LinkOrganizzazione link = new LinkOrganizzazione();
        link.setOrganizzazione(organizzazione);
        link.setNomeSocial(nomeSocial);
        link.setUrl(url);
        organizzazione.getLink().add(link);
    }

    /**
     * Modifica un link esistente o ne aggiunge uno nuovo se non esiste.
     *
     * @param organizzazione l'organizzazione a cui appartiene il link
     * @param nomeSocial il nome del social network
     * @param url il nuovo URL del social network
     */
    public void modificaLinkOrganizzazione(Organizzazione organizzazione, String nomeSocial, String url) {
        Optional<LinkOrganizzazione> link = linkOrganizzazioneRepository.findByNomeSocialAndOrganizzazioneId(nomeSocial, organizzazione.getId());

        if (link.isPresent()) {
            if (url.isEmpty()) {
                // Rimuove il link se l'URL è vuoto
                organizzazione.getLink().remove(link.get());
                linkOrganizzazioneRepository.delete(link.get());
            } else {
                // Aggiorna il link esistente
                link.get().setUrl(url);
                link.get().setOrganizzazione(organizzazione);
                linkOrganizzazioneRepository.save(link.get());
            }
        } else if (!url.isEmpty()) {
            // Aggiunge un nuovo link se non esiste e l'URL non è vuoto
            aggiungiLinkOrganizzazione(organizzazione, nomeSocial, url);
        }
    }
}