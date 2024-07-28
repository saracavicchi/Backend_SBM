package it.unife.ingsw202324.EventGo.services;

import it.unife.ingsw202324.EventGo.models.LinkOrganizzatore;
import it.unife.ingsw202324.EventGo.models.Organizzatore;
import it.unife.ingsw202324.EventGo.repositories.LinkOrganizzatoreRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Transactional
@Service
public class LinkOrganizzatoreService {

    private final LinkOrganizzatoreRepository linkOrganizzatoreRepository;

    @Autowired
    public LinkOrganizzatoreService(LinkOrganizzatoreRepository linkOrganizzatoreRepository) {
        this.linkOrganizzatoreRepository = linkOrganizzatoreRepository;
    }


    public void aggiungiLinkOrganizzatore(Organizzatore organizzatore, String nomeSocial, String url) {
        if (url.isEmpty()) {
            return;
        }

        // Aggiungi il nuovo link
        LinkOrganizzatore link = new LinkOrganizzatore();
        link.setOrganizzatore(organizzatore);
        link.setNomeSocial(nomeSocial);
        link.setUrl(url);
        organizzatore.getLink().add(link);
    }

    public void modificaLinkOrganizzatore(Organizzatore organizzatore, String nomeSocial, String url) {

        Optional<LinkOrganizzatore> link = linkOrganizzatoreRepository.findByNomeSocialAndOrganizzazioneId(nomeSocial, organizzatore.getId());

        if (link.isPresent()) {
            if (url.isEmpty()) {
                // Rimuove il link se l'URL è vuoto
                organizzatore.getLink().remove(link.get());
                linkOrganizzatoreRepository.delete(link.get());
            } else {
                // Aggiorna il link esistente
                link.get().setUrl(url);
                link.get().setOrganizzatore(organizzatore);
                linkOrganizzatoreRepository.save(link.get());
            }
        } else if (!url.isEmpty()) {
            // Aggiunge un nuovo link se non esiste e l'URL non è vuoto
            aggiungiLinkOrganizzatore(organizzatore, nomeSocial, url);
        }

    }
}
