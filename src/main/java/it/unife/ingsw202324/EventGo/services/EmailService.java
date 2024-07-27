package it.unife.ingsw202324.EventGo.services;

import it.unife.ingsw202324.EventGo.models.Organizzatore;
import it.unife.ingsw202324.EventGo.models.Organizzazione;
import it.unife.ingsw202324.EventGo.repositories.OrganizzatoreRepository;
import it.unife.ingsw202324.EventGo.repositories.OrganizzazioneRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Servizio per la gestione delle email relative alle organizzazioni e agli organizzatori.
 * Fornisce metodi per inviare email di aggiunta e rimozione da un'organizzazione.
 */
@Transactional
@Service
public class EmailService {

    // Oggetto e-mail per l'aggiunta a un'organizzazione
    private final static String ORGANIZATION_ADD_SUBJECT = "Aggiunta a organizzazione EventGo";
    // Oggetto e-mail per la rimozione da un'organizzazione
    private final static String ORGANIZATION_REM_SUBJECT = "Rimozione da organizzazione EventGo";

    // Oggetto per l'invio delle email
    private final JavaMailSender mailSender;

    // Repository per l'accesso ai dati delle organizzazioni e degli organizzatori
    private final OrganizzazioneRepository organizzazioneRepository;
    private final OrganizzatoreRepository organizzatoreRepository;

    /**
     * Costruttore per l'iniezione delle dipendenze necessarie.
     *
     * @param mailSender il componente per l'invio delle email.
     * @param organizzazioneRepository il repository per la gestione delle organizzazioni.
     * @param organizzatoreRepository il repository per la gestione degli organizzatori.
     */
    @Autowired
    public EmailService(JavaMailSender mailSender, OrganizzazioneRepository organizzazioneRepository, OrganizzatoreRepository organizzatoreRepository) {
        this.mailSender = mailSender;
        this.organizzazioneRepository = organizzazioneRepository;
        this.organizzatoreRepository = organizzatoreRepository;
    }

    /**
     * Invia un'email per notificare l'aggiunta di un organizzatore a un'organizzazione.
     *
     * @param recipient l'indirizzo email del destinatario.
     * @param idOrganizzazione l'ID dell'organizzazione a cui l'organizzatore è stato aggiunto.
     * @throws IllegalArgumentException se l'organizzazione non esiste.
     */
    public void sendAddEmail(String recipient, Long idOrganizzazione) {

        // Recupera l'organizzazione dal repository, lancia un'eccezione se non esiste
        Organizzazione organizzazione = organizzazioneRepository.findById(idOrganizzazione).orElse(null);

        if (organizzazione == null) {
            throw new IllegalArgumentException("Organizzazione inesistente");
        }

        // Crea e configura il messaggio email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipient);
        message.setSubject(ORGANIZATION_ADD_SUBJECT);

        // Corpo del messaggio email
        String text = "Ciao,\n\nSei stato aggiunto all'organizzazione " + organizzazione.getNome() + " su EventGo.\n"
                + "Ora puoi accedere alla pagina dell'organizzazione dalla tua area personale.\n\n"
                + "Grazie per averci scelto!\n\n"
                + "Lo staff di EventGo";

        message.setText(text);

        // Invia l'email
        mailSender.send(message);

    }

    /**
     * Invia un'email per notificare la rimozione di un organizzatore da un'organizzazione.
     *
     * @param idOrganizzatore l'ID dell'organizzatore rimosso.
     * @param idOrganizzazione l'ID dell'organizzazione da cui l'organizzatore è stato rimosso.
     * @throws IllegalArgumentException se l'organizzatore o l'organizzazione non esistono.
     */
    public void sendRemEmail(Long idOrganizzatore, Long idOrganizzazione) {

        // Recupera l'organizzatore e l'organizzazione dai repository, lancia un'eccezione se non esistono
        Organizzatore organizzatore = organizzatoreRepository.findById(idOrganizzatore).orElse(null);
        Organizzazione organizzazione = organizzazioneRepository.findById(idOrganizzazione).orElse(null);

        if (organizzatore == null || organizzazione == null) {
            throw new IllegalArgumentException("Organizzatore o organizzazione inesistente");
        }

        // Crea e configura il messaggio email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(organizzatore.getMail());
        message.setSubject(ORGANIZATION_REM_SUBJECT);

        // Corpo del messaggio email
        String text = "Ciao,\n\nSei stato rimosso dall'organizzazione " + organizzazione.getNome() + " su EventGo.\n"
                + "Non potrai più accedere alla pagina dell'organizzazione dalla tua area personale.\n\n"
                + "Grazie per averci scelto!\n\n"
                + "Lo staff di EventGo";

        message.setText(text);

        // Invia l'email
        mailSender.send(message);

    }

}
