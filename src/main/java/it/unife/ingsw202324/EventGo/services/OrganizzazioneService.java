package it.unife.ingsw202324.EventGo.services;

import it.unife.ingsw202324.EventGo.exceptions.DuplicatedEntityException;
import it.unife.ingsw202324.EventGo.models.Organizzatore;
import it.unife.ingsw202324.EventGo.models.Organizzazione;
import it.unife.ingsw202324.EventGo.repositories.OrganizzatoreRepository;
import it.unife.ingsw202324.EventGo.repositories.OrganizzazioneRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

/**
 * Service per la gestione delle organizzazioni.
 * Fornisce metodi per creare, modificare, recuperare ed eliminare le organizzazioni e i loro organizzatori associati.
 */
@Transactional
@Service
public class OrganizzazioneService {

    private final OrganizzazioneRepository organizzazioneRepository;
    private final OrganizzatoreRepository organizzatoreRepository;
    private final LinkOrganizzazioneService linkOrganizzazioneService;
    private final ImageService imageService;

    /**
     * Costruttore per l'iniezione dei servizi necessari.
     *
     * @param organizzazioneRepository il repository per la gestione delle organizzazioni
     * @param organizzatoreRepository il repository per la gestione degli organizzatori
     * @param linkOrganizzazioneService il servizio per la gestione dei link delle organizzazioni
     * @param imageService il servizio per la gestione delle immagini delle organizzazioni
     */
    @Autowired
    public OrganizzazioneService(OrganizzazioneRepository organizzazioneRepository,
                                 OrganizzatoreRepository organizzatoreRepository,
                                 LinkOrganizzazioneService linkOrganizzazioneService,
                                 ImageService imageService) {
        this.organizzazioneRepository = organizzazioneRepository;
        this.organizzatoreRepository = organizzatoreRepository;
        this.linkOrganizzazioneService = linkOrganizzazioneService;
        this.imageService = imageService;
    }


    /**
     * Recupera le informazioni di un'organizzazione.
     *
     * @param idOrganizzazione l'ID dell'organizzazione
     * @return l'organizzazione corrispondente all'ID fornito, se presente, altrimenti null
     */
    public Organizzazione getOrganizzazione(Long idOrganizzazione) {
        return organizzazioneRepository.findById(idOrganizzazione).orElse(null);
    }


    /**
     * Pulisce i campi di un'organizzazione da stringhe vuote, impostandoli a null.
     *
     * @param organizzazione l'organizzazione da pulire
     * @return l'organizzazione pulita
     */
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


    /**
     * Salva un'organizzazione nel repository, verificando eventuali duplicati.
     *
     * @param organizzazione l'organizzazione da salvare
     * @param id l'ID dell'organizzazione, se presente
     * @param idAdmin l'ID dell'amministratore dell'organizzazione
     * @return l'organizzazione salvata
     * @throws DuplicatedEntityException se sono presenti duplicati per email, telefono o IBAN
     */
    public Organizzazione salvaOrganizzazione(Organizzazione organizzazione, Optional<Long> id, Long idAdmin) {

        // Verifica se l'email è già in uso da un'altra organizzazione
        if (organizzazioneRepository.existsByMail(organizzazione.getMail(), id.isPresent() ? id : Optional.empty())) {
            throw new DuplicatedEntityException("Indirizzo email già in uso");
        }

        // Verifica se il numero di telefono è già in uso da un'altra organizzazione
        if (organizzazione.getTelefono() != null && organizzazioneRepository.existsByTelefono(organizzazione.getTelefono(), id.isPresent() ? id : Optional.empty())) {
            throw new DuplicatedEntityException("Numero di telefono già in uso");
        }

        // Verifica se l'IBAN è già in uso da un'altra organizzazione
        if (organizzazione.getIban() != null && organizzazioneRepository.existsByIban(organizzazione.getIban(), id.isPresent() ? id : Optional.empty())) {
            throw new DuplicatedEntityException("IBAN già in uso");
        }

        // Recupera l'amministratore dell'organizzazione
        Organizzatore admin = organizzatoreRepository.findById(idAdmin).orElse(null);
        if (admin == null) {
            throw new RuntimeException("Organizzatore non trovato");
        }
        if (id.isPresent()) {
            organizzazione.setAdmin(admin);
        }
        admin.setOrganizzazione(organizzazione);

        // Salva l'organizzazione nel repository
        return organizzazioneRepository.save(organizzazione);

    }


    /**
     * Crea o modifica un'organizzazione, gestendo i link social e l'immagine associata.
     *
     * @param organizzazione l'organizzazione da creare o modificare
     * @param id l'ID dell'organizzazione, se presente
     * @param idAdmin l'ID dell'amministratore dell'organizzazione
     * @param foto l'immagine dell'organizzazione
     * @param sito l'URL del sito web dell'organizzazione
     * @param instagram l'URL del profilo Instagram dell'organizzazione
     * @param facebook l'URL del profilo Facebook dell'organizzazione
     * @param twitter l'URL del profilo Twitter dell'organizzazione
     * @param linkedin l'URL del profilo LinkedIn dell'organizzazione
     * @param urlFoto l'URL della foto esistente dell'organizzazione
     * @param deletedPhoto flag che indica se la foto deve essere cancellata
     * @return l'organizzazione creata o modificata
     * @throws RuntimeException se si verifica un errore durante la creazione o modifica dell'organizzazione
     */
    public Organizzazione creaMofificaOrganizzazione(Organizzazione organizzazione,
                                                     Optional<Long> id,
                                                     Long idAdmin,
                                                     Optional<MultipartFile> foto,
                                                     String sito,
                                                     String instagram,
                                                     String facebook,
                                                     String twitter,
                                                     String linkedin,
                                                     Optional<String> urlFoto,
                                                     Optional<String> deletedPhoto) throws RuntimeException {

        // Sanitize the organization data to avoid empty strings
        organizzazione = sanitizeOrganizzazione(organizzazione);

        // Se l'ID non è presente, imposta l'amministratore per la nuova organizzazione
        if (id.isEmpty()) {
            Organizzatore admin = new Organizzatore();
            admin.setId(idAdmin);
            organizzazione.setAdmin(admin);
        }

        // Modifica i link social associati all'organizzazione
        linkOrganizzazioneService.modificaLinkOrganizzazione(organizzazione, "Sito", sito);
        linkOrganizzazioneService.modificaLinkOrganizzazione(organizzazione, "Instagram", instagram);
        linkOrganizzazioneService.modificaLinkOrganizzazione(organizzazione, "Facebook", facebook);
        linkOrganizzazioneService.modificaLinkOrganizzazione(organizzazione, "Twitter", twitter);
        linkOrganizzazioneService.modificaLinkOrganizzazione(organizzazione, "Linkedin", linkedin);

        // Salva o rimuove la foto dell'organizzazione
        String newUrlFoto = imageService.saveImage("organizzazione", foto.orElse(null), urlFoto, deletedPhoto.orElse(null));
        organizzazione.setUrlFoto(newUrlFoto);

        // Salva l'organizzazione nel repository
        return salvaOrganizzazione(organizzazione, id, idAdmin);

    }


    /**
     * Rimuove un organizzatore dall'organizzazione a cui è associato.
     *
     * @param idOrganizzatore l'ID dell'organizzatore da rimuovere
     */
    public void deleteOrganizzatore(Long idOrganizzatore) {

        // Recupera l'organizzatore dal repository
        Organizzatore organizzatore = organizzatoreRepository.findById(idOrganizzatore).orElse(null);
        if (organizzatore == null) {
            throw new RuntimeException("Organizzatore non trovato");
        }

        // Imposta l'organizzazione dell'organizzatore a null e salva le modifiche
        organizzatore.setOrganizzazione(null);
        organizzatoreRepository.save(organizzatore);

    }


    /**
     * Elimina un'organizzazione dal repository.
     *
     * @param id l'ID dell'organizzazione da eliminare
     */
    public void deleteOrganizzazione(Long id) {

        // Recupera l'organizzazione dal repository
        Organizzazione organizzazione = organizzazioneRepository.findById(id).orElse(null);
        if (organizzazione == null) {
            throw new RuntimeException("Organizzazione non trovata");
        }
        // Elimina l'organizzazione dal repository
        organizzazioneRepository.delete(organizzazione);
    }

    /**
     * Aggiunge un organizzatore a un'organizzazione.
     *
     * @param email l'email dell'organizzatore da aggiungere
     * @param idOrganizzazione l'ID dell'organizzazione a cui aggiungere l'organizzatore
     */
    public void addOrganizzatore(String email, Long idOrganizzazione) {

        // Recupera l'organizzatore dal repository utilizzando l'email
        Organizzatore organizzatore = organizzatoreRepository.findByMail(email);
        if (organizzatore == null) {
            throw new IllegalArgumentException("Organizzatore inesistente");
        }

        // Verifica se l'organizzatore è già associato a un'altra organizzazione
        if (organizzatore.getOrganizzazione() != null) {
            throw new DuplicatedEntityException("L'organizzatore fa già parte di un'organizzazione");
        }

        // Recupera l'organizzazione dal repository
        Organizzazione organizzazione = organizzazioneRepository.findById(idOrganizzazione).orElse(null);
        if (organizzazione == null) {
            throw new RuntimeException("Organizzazione non trovata");
        }

        // Associa l'organizzatore all'organizzazione e salva le modifiche
        organizzatore.setOrganizzazione(organizzazione);
        organizzatoreRepository.save(organizzatore);

    }
}
