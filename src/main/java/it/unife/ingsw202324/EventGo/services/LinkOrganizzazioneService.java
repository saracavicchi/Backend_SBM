package it.unife.ingsw202324.EventGo.services;

import it.unife.ingsw202324.EventGo.models.LinkOrganizzazione;
import it.unife.ingsw202324.EventGo.models.Organizzazione;
import it.unife.ingsw202324.EventGo.repositories.LinkOrganizzazioneRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service per la gestione dei link social delle organizzazioni.
 * Fornisce metodi per aggiungere e modificare i link social associati alle organizzazioni.
 */
@Transactional
@Service
public class LinkOrganizzazioneService {

    private final LinkOrganizzazioneRepository linkOrganizzazioneRepository;

    /**
     * Costruttore per l'iniezione del repository LinkOrganizzazioneRepository.
     *
     * @param linkOrganizzazioneRepository il repository per la gestione dei link social delle organizzazioni
     */
    @Autowired
    public LinkOrganizzazioneService(LinkOrganizzazioneRepository linkOrganizzazioneRepository) {
        this.linkOrganizzazioneRepository = linkOrganizzazioneRepository;
    }

    /**
     * Aggiunge un nuovo link social per un'organizzazione.
     * Se l'URL è vuoto, il metodo non fa nulla.
     *
     * @param organizzazione l'organizzazione a cui aggiungere il link
     * @param nomeSocial il nome del social network
     * @param url l'URL del profilo social
     */
    public void aggiungiLinkOrganizzazione(Organizzazione organizzazione, String nomeSocial, String url) {
        // Se l'URL è vuoto, non fa nulla
        if (url.isEmpty()) {
            return;
        }

        // Crea un nuovo oggetto LinkOrganizzazione e imposta i suoi valori
        LinkOrganizzazione link = new LinkOrganizzazione();
        link.setOrganizzazione(organizzazione);
        link.setNomeSocial(nomeSocial);
        link.setUrl(url);
        // Aggiunge il nuovo link all'organizzazione
        organizzazione.getLink().add(link);
    }

    /**
     * Modifica un link social esistente per un'organizzazione.
     * Se il link esiste, aggiorna il suo URL o lo rimuove se l'URL è vuoto.
     * Se il link non esiste e l'URL non è vuoto, aggiunge un nuovo link.
     *
     * @param organizzazione l'organizzazione a cui modificare il link
     * @param nomeSocial il nome del social network
     * @param url l'URL del profilo social
     */
    public void modificaLinkOrganizzazione(Organizzazione organizzazione, String nomeSocial, String url) {

        // Trova il link esistente basato sul nome del social e l'ID dell'organizzazione
        Optional<LinkOrganizzazione> link = linkOrganizzazioneRepository.findByNomeSocialAndOrganizzazioneId(nomeSocial, organizzazione.getId());

        if (link.isPresent()) {
            // Se il link esiste e l'URL è vuoto, rimuove il link
            if (url.isEmpty()) {
                organizzazione.getLink().remove(link.get());
                linkOrganizzazioneRepository.delete(link.get());
            } else {
                // Se il link esiste e l'URL non è vuoto, aggiorna l'URL del link
                link.get().setUrl(url);
                link.get().setOrganizzazione(organizzazione);
                linkOrganizzazioneRepository.save(link.get());
            }
        } else if (!url.isEmpty()) {
            // Se il link non esiste e l'URL non è vuoto, aggiunge un nuovo link
            aggiungiLinkOrganizzazione(organizzazione, nomeSocial, url);
        }
    }
}