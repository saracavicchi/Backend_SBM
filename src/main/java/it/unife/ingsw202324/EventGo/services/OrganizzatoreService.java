package it.unife.ingsw202324.EventGo.services;

import it.unife.ingsw202324.EventGo.exceptions.DuplicatedEntityException;
import it.unife.ingsw202324.EventGo.models.CarteOrganizzatore;
import it.unife.ingsw202324.EventGo.models.Organizzatore;
import it.unife.ingsw202324.EventGo.repositories.OrganizzatoreRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * Servizio per la gestione delle operazioni relative agli organizzatori.
 */
@Transactional
@Service
public class OrganizzatoreService {

    // Repository per l'accesso ai dati degli organizzatori
    private final OrganizzatoreRepository organizzatoreRepository;

    private final LinkOrganizzatoreService linkOrganizzatoreService;

    private final CarteOrganizzatoreService carteOrganizzatoreService;

    // Directory di upload foto, ricavata da application.properties
    @Value("${app.upload.dir}")
    private String uploadDir;

    /**
     * Costruttore del servizio con iniezione delle dipendenze.
     *
     * @param organizzatoreRepository repository per la gestione degli organizzatori
     */
    @Autowired
    public OrganizzatoreService(OrganizzatoreRepository organizzatoreRepository,
                                LinkOrganizzatoreService linkOrganizzatoreService,
                                CarteOrganizzatoreService carteOrganizzatoreService) {
        this.organizzatoreRepository = organizzatoreRepository;
        this.linkOrganizzatoreService = linkOrganizzatoreService;
        this.carteOrganizzatoreService = carteOrganizzatoreService;
    }

    /**
     * Verifica se un organizzatore appartiene a un'organizzazione.
     *
     * @param idOrganizzatore l'ID dell'organizzatore
     * @return l'ID dell'organizzazione a cui appartiene l'organizzatore, o null se non appartiene a nessuna
     */
    public Long hasOrganizzazione(Long idOrganizzatore) {
        // Trova l'organizzatore per ID e verifica se appartiene a un'organizzazione
        return organizzatoreRepository.findById(idOrganizzatore)
                .map(organizzatore ->
                        // Verifica se l'organizzatore ha un'organizzazione associata
                        Objects.nonNull(organizzatore.getOrganizzazione())
                                ? organizzatore.getOrganizzazione().getId()
                                : null
                )
                // Ritorna null se l'organizzatore non esiste
                .orElse(null);
    }

    public Organizzatore getOrganizzatore(Long idOrganizzatore) {
        return organizzatoreRepository.findById(idOrganizzatore).orElse(null);
    }

    public Organizzatore modificaOrganizzatore(Organizzatore organizzatore, Optional<MultipartFile> foto, String sito, String instagram, String facebook, String twitter, String linkedin, Optional<String> urlFoto, Optional<String> deletedPhoto, CarteOrganizzatore carta1, CarteOrganizzatore carta2, CarteOrganizzatore carta3) {

        organizzatore = sanitizeOrganizzatore(organizzatore);

        linkOrganizzatoreService.modificaLinkOrganizzatore(organizzatore, "Sito", sito);
        linkOrganizzatoreService.modificaLinkOrganizzatore(organizzatore, "Instagram", instagram);
        linkOrganizzatoreService.modificaLinkOrganizzatore(organizzatore, "Facebook", facebook);
        linkOrganizzatoreService.modificaLinkOrganizzatore(organizzatore, "Twitter", twitter);
        linkOrganizzatoreService.modificaLinkOrganizzatore(organizzatore, "Linkedin", linkedin);

        carteOrganizzatoreService.modificaCartaOrganizzatore(organizzatore, carta1);
        carteOrganizzatoreService.modificaCartaOrganizzatore(organizzatore, carta2);
        carteOrganizzatoreService.modificaCartaOrganizzatore(organizzatore, carta3);

        salvaFotoOrganizzatore(organizzatore, foto.orElse(null), urlFoto, deletedPhoto.orElse(null));

        return salvaOrganizzatore(organizzatore);

    }

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

    private Organizzatore salvaOrganizzatore(Organizzatore organizzatore) {
        // Verifica l'unicità di email, telefono e IBAN
        if (organizzatoreRepository.existsByMail(organizzatore.getMail(), organizzatore.getId())) {
            throw new DuplicatedEntityException("Indirizzo email già in uso");
        }

        if (organizzatore.getTelefono() != null && organizzatoreRepository.existsByTelefono(organizzatore.getTelefono(), organizzatore.getId())) {
            throw new DuplicatedEntityException("Numero di telefono già in uso");
        }

        if (organizzatore.getIban() != null && organizzatoreRepository.existsByIban(organizzatore.getIban(), organizzatore.getId())) {
            throw new DuplicatedEntityException("IBAN già in uso");
        }

        if (organizzatore.getCodFiscale() != null && organizzatoreRepository.existsByCodFiscale(organizzatore.getCodFiscale(), organizzatore.getId())) {
            throw new DuplicatedEntityException("Codice fiscale già in uso");
        }

        if (organizzatore.getUsername() != null && organizzatoreRepository.existsByUsername(organizzatore.getUsername(), organizzatore.getId())) {
            throw new DuplicatedEntityException("Username già in uso");
        }

        if (organizzatore.getPartitaIva() != null && organizzatoreRepository.existsByPartitaIva(organizzatore.getPartitaIva(), organizzatore.getId())) {
            throw new DuplicatedEntityException("Partita IVA già in uso");
        }


        // Salva l'organizzazione nel repository
        return organizzatoreRepository.save(organizzatore);
    }

    private void salvaFotoOrganizzatore(Organizzatore organizzatore, MultipartFile foto, Optional<String> oldFoto, String deletedPhoto) {

        String urlFoto = null;
        if (oldFoto.isPresent()) {
            urlFoto = oldFoto.get();
        }

        // Gestione del caricamento dell'immagine
        if (foto != null && !foto.isEmpty()) {

            try {

                // Genera un nome unico per il file dell'immagine
                String fileName = UUID.randomUUID().toString() + "_" + foto.getOriginalFilename();
                Path directory = Paths.get(uploadDir.replace("file:./", "")).resolve("organizzatoriImg");
                Files.createDirectories(directory);
                Path savePath = directory.resolve(fileName);

                // Salva il file nel percorso specificato
                try (InputStream inputStream = foto.getInputStream()) {
                    Files.copy(inputStream, savePath, StandardCopyOption.REPLACE_EXISTING);
                }
                organizzatore.setUrlFoto(fileName);

            } catch (IOException e) {
                throw new RuntimeException("Errore nel caricamento dell'immagine");
            }

        } else {
            if (deletedPhoto != null && deletedPhoto.equals("true")) {
                organizzatore.setUrlFoto(null);
            } else if (urlFoto != null) {
                organizzatore.setUrlFoto(urlFoto);
            }
        }
    }
}
