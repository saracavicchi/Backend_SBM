package it.unife.ingsw202324.EventGo.services;

import it.unife.ingsw202324.EventGo.exceptions.DuplicatedEntityException;
import it.unife.ingsw202324.EventGo.models.Organizzatore;
import it.unife.ingsw202324.EventGo.models.Organizzazione;
import it.unife.ingsw202324.EventGo.repositories.OrganizzatoreRepository;
import it.unife.ingsw202324.EventGo.repositories.OrganizzazioneRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


/**
 * Servizio per la gestione delle operazioni relative alle organizzazioni.
 */
@Transactional
@Service
public class OrganizzazioneService {

    // Repository per l'accesso ai dati delle organizzazioni
    private final OrganizzazioneRepository organizzazioneRepository;

    // Repository per l'accesso ai dati degli organizzatori
    private final OrganizzatoreRepository organizzatoreRepository;

    // Servizio per la gestione dei link delle organizzazioni
    private final LinkOrganizzazioneService linkOrganizzazioneService;

    // Directory di upload foto, letta dal file di configurazione
    @Value("${app.upload.dir}")
    private String uploadDir;

    /**
     * Costruttore del servizio con iniezione delle dipendenze.
     *
     * @param organizzazioneRepository repository per la gestione delle organizzazioni
     * @param organizzatoreRepository  repository per la gestione degli organizzatori
     * @param linkOrganizzazioneService servizio per la gestione dei link delle organizzazioni
     */
    @Autowired
    public OrganizzazioneService(OrganizzazioneRepository organizzazioneRepository,
                                 OrganizzatoreRepository organizzatoreRepository,
                                 LinkOrganizzazioneService linkOrganizzazioneService) {
        this.organizzazioneRepository = organizzazioneRepository;
        this.organizzatoreRepository = organizzatoreRepository;
        this.linkOrganizzazioneService = linkOrganizzazioneService;
    }

    /**
     * Recupera un'organizzazione dato il suo ID.
     *
     * @param idOrganizzazione l'ID dell'organizzazione
     * @return l'organizzazione trovata, o null se non esiste
     */
    public Organizzazione getOrganizzazione(Long idOrganizzazione) {
        return organizzazioneRepository.findById(idOrganizzazione).orElse(null);
    }

    /**
     * Pulisce i campi vuoti di un'istanza di Organizzazione.
     *
     * @param organizzazione l'istanza di Organizzazione da sanitizzare
     * @return l'istanza sanitizzata di Organizzazione
     */
    public Organizzazione sanitizeOrganizzazione(Organizzazione organizzazione) {

        // Verifica se il campo è vuoto e lo imposta a null

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
     * Salva un'organizzazione dopo aver effettuato le verifiche necessarie.
     *
     * @param organizzazione l'istanza di Organizzazione da salvare
     * @return l'istanza salvata di Organizzazione
     * @throws DuplicatedEntityException se un'entity con gli stessi dati esiste già
     */
    public Organizzazione salvaOrganizzazione(Organizzazione organizzazione, Optional<Long> id, Long idAdmin) {

        if (organizzazioneRepository.existsByMail(organizzazione.getMail(), id.isPresent() ? id : Optional.empty())) {
            throw new DuplicatedEntityException("Indirizzo email già in uso");
        }

        if (organizzazione.getTelefono() != null && organizzazioneRepository.existsByTelefono(organizzazione.getTelefono(), id.isPresent() ? id : Optional.empty())) {
            throw new DuplicatedEntityException("Numero di telefono già in uso");
        }

        if (organizzazione.getIban() != null && organizzazioneRepository.existsByIban(organizzazione.getIban(), id.isPresent() ? id : Optional.empty())) {
            throw new DuplicatedEntityException("IBAN già in uso");
        }

        Organizzatore admin = organizzatoreRepository.findById(idAdmin).orElse(null);
        if (admin == null) {
            throw new RuntimeException("Organizzatore non trovato");
        }
        if(id.isPresent()){
            organizzazione.setAdmin(admin);
        }
        admin.setOrganizzazione(organizzazione);


        return organizzazioneRepository.save(organizzazione);

    }

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

        // Sanitizza l'oggetto organizzazione (null al posto dei campi vuoti)
        organizzazione = sanitizeOrganizzazione(organizzazione);

        if(id.isEmpty()){
            System.out.println("Creazione");
            // Imposta l'admin dell'organizzazione
            Organizzatore admin = new Organizzatore();
            admin.setId(idAdmin);
            organizzazione.setAdmin(admin);
        }

        // Aggiunge i link social all'organizzazione
        linkOrganizzazioneService.modificaLinkOrganizzazione(organizzazione, "Sito", sito);
        linkOrganizzazioneService.modificaLinkOrganizzazione(organizzazione, "Instagram", instagram);
        linkOrganizzazioneService.modificaLinkOrganizzazione(organizzazione, "Facebook", facebook);
        linkOrganizzazioneService.modificaLinkOrganizzazione(organizzazione, "Twitter", twitter);
        linkOrganizzazioneService.modificaLinkOrganizzazione(organizzazione, "LinkedIn", linkedin);


        try{
            saveFotoOrganizzazione(organizzazione, foto.orElse(null), urlFoto, deletedPhoto.orElse(null));
        }catch(RuntimeException e){
           throw e;
        }


        // Salva l'organizzazione
        return salvaOrganizzazione(organizzazione, id, idAdmin);

    }


    public void saveFotoOrganizzazione(Organizzazione organizzazione,
                                       MultipartFile foto,
                                       Optional<String> oldFoto,
                                       String deletedPhoto) throws RuntimeException {
        String urlFoto = null;
        if(oldFoto.isPresent()){
            urlFoto = oldFoto.get();
        }

        // Gestione del caricamento dell'immagine
        if (foto != null && !foto.isEmpty()) {

            try {

                // Genera un nome unico per il file dell'immagine
                String fileName = UUID.randomUUID().toString() + "_" + foto.getOriginalFilename();
                Path directory = Paths.get(uploadDir.replace("file:./", "")).resolve("organizzazioniImg");
                Files.createDirectories(directory);
                Path savePath = directory.resolve(fileName);

                // Salva il file nel percorso specificato
                try (InputStream inputStream = foto.getInputStream()) {
                    Files.copy(inputStream, savePath, StandardCopyOption.REPLACE_EXISTING);
                }
                organizzazione.setUrlFoto(fileName);

            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Errore nel caricamento dell'immagine");
            }

        }else {
            if (deletedPhoto != null && deletedPhoto.equals("true")) {
                System.out.println("Eliminata" + deletedPhoto);
                organizzazione.setUrlFoto(null);
            } else if(urlFoto != null) {
                organizzazione.setUrlFoto(urlFoto);
            }
        }
    }

    /**
     * Rimuove l'appartenenza di un organizzatore a un'organizzazione.
     *
     * @param idOrganizzatore l'ID dell'organizzatore da rimuovere
     * @throws RuntimeException se l'organizzatore non viene trovato
     */
    public void deleteOrganizzatore(Long idOrganizzatore) {

        // Trova l'organizzatore per ID
        Organizzatore organizzatore = organizzatoreRepository.findById(idOrganizzatore).orElse(null);
        if (organizzatore == null) {
            throw new RuntimeException("Organizzatore non trovato");
        }

        // Setta a null l'organizzazione e salva l'organizzatore
        organizzatore.setOrganizzazione(null);
        organizzatoreRepository.save(organizzatore);

    }

    /**
     * Elimina un'organizzazione dato il suo ID.
     *
     * @param id l'ID dell'organizzazione da eliminare
     * @throws RuntimeException se l'organizzazione non viene trovata
     */
    public void deleteOrganizzazione(Long id) {
        // Trova l'organizzazione per ID
        Organizzazione organizzazione = organizzazioneRepository.findById(id).orElse(null);
        if (organizzazione == null) {
            throw new RuntimeException("Organizzazione non trovata");
        }
        // Elimina l'organizzazione (e i dati collegati)
        organizzazioneRepository.delete(organizzazione);
    }

    /**
     * Aggiunge un organizzatore a un'organizzazione.
     *
     * @param email             l'email dell'organizzatore da aggiungere
     * @param idOrganizzazione  l'ID dell'organizzazione a cui aggiungere l'organizzatore
     * @throws IllegalArgumentException se l'organizzatore non esiste
     * @throws DuplicatedEntityException se l'organizzatore appartiene già a un'organizzazione
     * @throws RuntimeException se l'organizzazione non viene trovata
     */
    public void addOrganizzatore(String email, Long idOrganizzazione) {

        // Trova l'organizzatore per email
        Organizzatore organizzatore = organizzatoreRepository.findByMail(email);
        if (organizzatore == null) {
            throw new IllegalArgumentException("Organizzatore inesistente");
        }

        // Verifica se l'organizzatore appartiene già a un'organizzazione
        if (organizzatore.getOrganizzazione() != null) {
            throw new DuplicatedEntityException("L'organizzatore fa già parte di un'organizzazione");
        }

        // Trova l'organizzazione per ID
        Organizzazione organizzazione = organizzazioneRepository.findById(idOrganizzazione).orElse(null);
        if (organizzazione == null) {
            throw new RuntimeException("Organizzazione non trovata");
        }

        // Setta l'organizzazione e aggiorna l'organizzatore
        organizzatore.setOrganizzazione(organizzazione);
        organizzatoreRepository.save(organizzatore);

    }
}
