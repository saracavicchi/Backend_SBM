package it.unife.ingsw202324.EventGo.services;

import it.unife.ingsw202324.EventGo.exceptions.DuplicatedEntityException;
import it.unife.ingsw202324.EventGo.models.CarteOrganizzatore;
import it.unife.ingsw202324.EventGo.models.Organizzatore;
import it.unife.ingsw202324.EventGo.repositories.OrganizzatoreRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.Optional;

/**
 * Service per la gestione degli organizzatori.
 * Fornisce metodi per recuperare, modificare e verificare l'esistenza di organizzatori e delle loro informazioni.
 */
@Transactional
@Service
public class OrganizzatoreService {

    private final OrganizzatoreRepository organizzatoreRepository;
    private final LinkOrganizzatoreService linkOrganizzatoreService;
    private final CarteOrganizzatoreService carteOrganizzatoreService;
    private final ImageService imageService;


    /**
     * Costruttore per l'iniezione dei servizi necessari.
     *
     * @param organizzatoreRepository il repository per la gestione degli organizzatori
     * @param linkOrganizzatoreService il servizio per la gestione dei link degli organizzatori
     * @param carteOrganizzatoreService il servizio per la gestione delle carte di credito degli organizzatori
     * @param imageService il servizio per la gestione delle immagini degli organizzatori
     */
    @Autowired
    public OrganizzatoreService(OrganizzatoreRepository organizzatoreRepository,
                                LinkOrganizzatoreService linkOrganizzatoreService,
                                CarteOrganizzatoreService carteOrganizzatoreService,
                                ImageService imageService) {
        this.organizzatoreRepository = organizzatoreRepository;
        this.linkOrganizzatoreService = linkOrganizzatoreService;
        this.carteOrganizzatoreService = carteOrganizzatoreService;
        this.imageService = imageService;
    }


    /**
     * Verifica se un organizzatore ha un'organizzazione associata.
     *
     * @param idOrganizzatore l'ID dell'organizzatore
     * @return l'ID dell'organizzazione associata, se presente, altrimenti null
     */
    public Long hasOrganizzazione(Long idOrganizzatore) {

        return organizzatoreRepository.findById(idOrganizzatore)
                .map(organizzatore ->
                        Objects.nonNull(organizzatore.getOrganizzazione())
                                ? organizzatore.getOrganizzazione().getId()
                                : null
                )
                .orElse(null);
    }

    /**
     * Recupera le informazioni di un organizzatore.
     *
     * @param idOrganizzatore l'ID dell'organizzatore
     * @return l'organizzatore corrispondente all'ID fornito, se presente, altrimenti null
     */
    public Organizzatore getOrganizzatore(Long idOrganizzatore) {
        return organizzatoreRepository.findById(idOrganizzatore).orElse(null);
    }

    /**
     * Modifica le informazioni di un organizzatore.
     *
     * @param organizzatore l'organizzatore da modificare
     * @param foto l'immagine dell'organizzatore
     * @param sito l'URL del sito web dell'organizzatore
     * @param instagram l'URL del profilo Instagram dell'organizzatore
     * @param facebook l'URL del profilo Facebook dell'organizzatore
     * @param twitter l'URL del profilo Twitter dell'organizzatore
     * @param linkedin l'URL del profilo LinkedIn dell'organizzatore
     * @param urlFoto l'URL della foto esistente dell'organizzatore
     * @param deletedPhoto flag che indica se la foto deve essere cancellata
     * @param carta1 la prima carta di credito dell'organizzatore
     * @param carta2 la seconda carta di credito dell'organizzatore
     * @param carta3 la terza carta di credito dell'organizzatore
     * @return l'organizzatore modificato e salvato
     */
    public Organizzatore modificaOrganizzatore(Organizzatore organizzatore, Optional<MultipartFile> foto, String sito, String instagram, String facebook, String twitter, String linkedin, Optional<String> urlFoto, Optional<String> deletedPhoto, CarteOrganizzatore carta1, CarteOrganizzatore carta2, CarteOrganizzatore carta3) {

        // Sanitizza l'organizzatore per evitare stringhe vuote
        organizzatore = sanitizeOrganizzatore(organizzatore);

        // Aggiorgna i link social
        linkOrganizzatoreService.modificaLinkOrganizzatore(organizzatore, "Sito", sito);
        linkOrganizzatoreService.modificaLinkOrganizzatore(organizzatore, "Instagram", instagram);
        linkOrganizzatoreService.modificaLinkOrganizzatore(organizzatore, "Facebook", facebook);
        linkOrganizzatoreService.modificaLinkOrganizzatore(organizzatore, "Twitter", twitter);
        linkOrganizzatoreService.modificaLinkOrganizzatore(organizzatore, "Linkedin", linkedin);

        // Aggiorna le carte di credito
        carteOrganizzatoreService.modificaCartaOrganizzatore(organizzatore, carta1);
        carteOrganizzatoreService.modificaCartaOrganizzatore(organizzatore, carta2);
        carteOrganizzatoreService.modificaCartaOrganizzatore(organizzatore, carta3);

        // Salva o rimuove l'immage dell'organizzatore
        String newUrlFoto = imageService.saveImage("organizzatore", foto.orElse(null), urlFoto, deletedPhoto.orElse(null));
        organizzatore.setUrlFoto(newUrlFoto);

        // Salva l'organizzatore nel repository
        return salvaOrganizzatore(organizzatore);

    }

    /**
     * Pulisce i campi di un organizzatore da stringhe vuote, impostandoli a null.
     *
     * @param organizzatore l'organizzatore da pulire
     * @return l'organizzatore pulito
     */
    private Organizzatore sanitizeOrganizzatore(Organizzatore organizzatore) {

        if (organizzatore.getNome() != null && organizzatore.getNome().isEmpty()) {
            organizzatore.setNome(null);
        }
        if (organizzatore.getCognome() != null && organizzatore.getCognome().isEmpty()) {
            organizzatore.setCognome(null);
        }
        if (organizzatore.getUsername() != null && organizzatore.getUsername().isEmpty()) {
            organizzatore.setUsername(null);
        }
        if (organizzatore.getMail() != null && organizzatore.getMail().isEmpty()) {
            organizzatore.setMail(null);
        }
        if (organizzatore.getTelefono() != null && organizzatore.getTelefono().isEmpty()) {
            organizzatore.setTelefono(null);
        }
        if (organizzatore.getCodFiscale() != null && organizzatore.getCodFiscale().isEmpty()) {
            organizzatore.setCodFiscale(null);
        }
        if (organizzatore.getBio() != null && organizzatore.getBio().isEmpty()) {
            organizzatore.setBio(null);
        }
        if (organizzatore.getPartitaIva() != null && organizzatore.getPartitaIva().isEmpty()) {
            organizzatore.setPartitaIva(null);
        }
        if (organizzatore.getStato() != null && organizzatore.getStato().isEmpty()) {
            organizzatore.setStato(null);
        }
        if (organizzatore.getProvincia() != null && organizzatore.getProvincia().isEmpty()) {
            organizzatore.setProvincia(null);
        }
        if (organizzatore.getCittà() != null && organizzatore.getCittà().isEmpty()) {
            organizzatore.setCittà(null);
        }
        if (organizzatore.getCap() != null && organizzatore.getCap().isEmpty()) {
            organizzatore.setCap(null);
        }
        if (organizzatore.getVia() != null && organizzatore.getVia().isEmpty()) {
            organizzatore.setVia(null);
        }
        if (organizzatore.getNumCivico() != null && organizzatore.getNumCivico().isEmpty()) {
            organizzatore.setNumCivico(null);
        }
        if (organizzatore.getIban() != null && organizzatore.getIban().isEmpty()) {
            organizzatore.setIban(null);
        }

        return organizzatore;

    }

    /**
     * Salva un organizzatore nel repository, verificando eventuali duplicati.
     *
     * @param organizzatore l'organizzatore da salvare
     * @return l'organizzatore salvato
     * @throws DuplicatedEntityException se sono presenti duplicati per email, telefono, IBAN, codice fiscale, username o partita IVA
     */
    private Organizzatore salvaOrganizzatore(Organizzatore organizzatore) {

        // Verifica se l'email è già in uso da un altro organizzatore
        if (organizzatoreRepository.existsByMail(organizzatore.getMail(), organizzatore.getId())) {
            throw new DuplicatedEntityException("Indirizzo email già in uso");
        }

        // Verifica se il numero di telefono è già in uso da un altro organizzatore
        if (organizzatore.getTelefono() != null && organizzatoreRepository.existsByTelefono(organizzatore.getTelefono(), organizzatore.getId())) {
            throw new DuplicatedEntityException("Numero di telefono già in uso");
        }

        // Verifica se l'IBAN è già in uso da un altro organizzatore
        if (organizzatore.getIban() != null && organizzatoreRepository.existsByIban(organizzatore.getIban(), organizzatore.getId())) {
            throw new DuplicatedEntityException("IBAN già in uso");
        }

        // Verifica se il codice fiscale è già in uso da un altro organizzatore
        if (organizzatore.getCodFiscale() != null && organizzatoreRepository.existsByCodFiscale(organizzatore.getCodFiscale(), organizzatore.getId())) {
            throw new DuplicatedEntityException("Codice fiscale già in uso");
        }

        // Verifica se lo username è già in uso da un altro organizzatore
        if (organizzatore.getUsername() != null && organizzatoreRepository.existsByUsername(organizzatore.getUsername(), organizzatore.getId())) {
            throw new DuplicatedEntityException("Username già in uso");
        }

        // Verifica se la partita IVA è già in uso da un altro organizzatore
        if (organizzatore.getPartitaIva() != null && organizzatoreRepository.existsByPartitaIva(organizzatore.getPartitaIva(), organizzatore.getId())) {
            throw new DuplicatedEntityException("Partita IVA già in uso");
        }

        // Salva l'organizzatore nel repository
        return organizzatoreRepository.save(organizzatore);
    }

}
