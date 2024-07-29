package it.unife.ingsw202324.EventGo.services;

import it.unife.ingsw202324.EventGo.models.LinkOrganizzatore;
import it.unife.ingsw202324.EventGo.models.Organizzatore;
import it.unife.ingsw202324.EventGo.repositories.LinkOrganizzatoreRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service per la gestione dei link social degli organizzatori.
 * Fornisce metodi per aggiungere e modificare i link social associati agli organizzatori.
 */
@Transactional
@Service
public class LinkOrganizzatoreService {

    private final LinkOrganizzatoreRepository linkOrganizzatoreRepository;

    /**
     * Costruttore per l'iniezione del repository LinkOrganizzatoreRepository.
     *
     * @param linkOrganizzatoreRepository il repository per la gestione dei link social degli organizzatori
     */
    @Autowired
    public LinkOrganizzatoreService(LinkOrganizzatoreRepository linkOrganizzatoreRepository) {
        this.linkOrganizzatoreRepository = linkOrganizzatoreRepository;
    }


    /**
     * Aggiunge un nuovo link social per un organizzatore.
     * Se l'URL è vuoto, il metodo non fa nulla.
     *
     * @param organizzatore l'organizzatore a cui aggiungere il link
     * @param nomeSocial il nome del social network
     * @param url l'URL del profilo social
     */
    public void aggiungiLinkOrganizzatore(Organizzatore organizzatore, String nomeSocial, String url) {
        // Se l'URL è vuoto, non fare nulla
        if (url.isEmpty()) {
            return;
        }

        // Crea un nuovo oggetto LinkOrganizzatore e imposta i suoi valori
        LinkOrganizzatore link = new LinkOrganizzatore();
        link.setOrganizzatore(organizzatore);
        link.setNomeSocial(nomeSocial);
        link.setUrl(url);
        // Aggiungi il nuovo link all'organizzatore
        organizzatore.getLink().add(link);
    }

    /**
     * Modifica un link social esistente per un organizzatore.
     * Se il link esiste, aggiorna il suo URL o lo rimuove se l'URL è vuoto.
     * Se il link non esiste e l'URL non è vuoto, aggiunge un nuovo link.
     *
     * @param organizzatore l'organizzatore a cui modificare il link
     * @param nomeSocial il nome del social network
     * @param url l'URL del profilo social
     */
    public void modificaLinkOrganizzatore(Organizzatore organizzatore, String nomeSocial, String url) {

        // Trova il link esistente basato sul nome del social e l'ID dell'organizzatore
        Optional<LinkOrganizzatore> link = linkOrganizzatoreRepository.findByNomeSocialAndOrganizzatoreId(nomeSocial, organizzatore.getId());

        if (link.isPresent()) {
            // Se il link esiste e l'URL è vuoto, rimuovi il link
            if (url.isEmpty()) {
                organizzatore.getLink().remove(link.get());
                linkOrganizzatoreRepository.delete(link.get());
            } else {
                // Se il link esiste e l'URL non è vuoto, aggiorna l'URL del link
                link.get().setUrl(url);
                link.get().setOrganizzatore(organizzatore);
                linkOrganizzatoreRepository.save(link.get());
            }
        } else if (!url.isEmpty()) {
            // Se il link non esiste e l'URL non è vuoto, aggiungi un nuovo link
            aggiungiLinkOrganizzatore(organizzatore, nomeSocial, url);
        }

    }
}
