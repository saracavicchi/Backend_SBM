package it.unife.ingsw202324.EventGo.services;

import it.unife.ingsw202324.EventGo.models.LinkOrganizzazione;
import it.unife.ingsw202324.EventGo.models.Organizzazione;
import it.unife.ingsw202324.EventGo.repositories.LinkOrganizzazioneRepository;
import it.unife.ingsw202324.EventGo.repositories.OrganizzazioneRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Transactional
@Service
public class LinkOrganizzazioneService {

    private final LinkOrganizzazioneRepository linkOrganizzazioneRepository;
    private final OrganizzazioneRepository organizzazioneRepository;

    @Autowired
    public LinkOrganizzazioneService(LinkOrganizzazioneRepository linkOrganizzazioneRepository, OrganizzazioneRepository organizzazioneRepository) {
        this.linkOrganizzazioneRepository = linkOrganizzazioneRepository;
        this.organizzazioneRepository = organizzazioneRepository;
    }

    @Transactional
    public void aggiungiLinkOrganizzazione(Organizzazione organizzazione, String nomeSocial, String url) {
        //System.out.println("add " + nomeSocial);
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

    @Transactional
    public void modificaLinkOrganizzazione(Organizzazione organizzazione, String nomeSocial, String url) {
        //System.out.println("edit " + nomeSocial);
        Optional<LinkOrganizzazione> link = linkOrganizzazioneRepository.findByNomeSocialAndOrganizzazioneId(nomeSocial, organizzazione.getId());

        if (link.isPresent()) {
            System.out.println(link.get().getUrl());

            if (url.isEmpty()) {
                organizzazione.getLink().remove(link.get());
                linkOrganizzazioneRepository.delete(link.get());
                System.out.println(link.get().getUrl() + " eliminato");
            } else {
                link.get().setUrl(url);
                link.get().setOrganizzazione(organizzazione);
                linkOrganizzazioneRepository.save(link.get());
            }
        } else if(!url.isEmpty()) {
            // Aggiungi il nuovo link
            aggiungiLinkOrganizzazione(organizzazione, nomeSocial, url);
        }


    }
}
