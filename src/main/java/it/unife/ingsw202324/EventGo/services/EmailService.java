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

@Transactional
@Service
public class EmailService {

    private final static String ORGANIZATION_ADD_SUBJECT = "Aggiunta a organizzazione EventGo";
    private final static String ORGANIZATION_REM_SUBJECT = "Rimozione da organizzazione EventGo";

    private final JavaMailSender mailSender;

    private final OrganizzazioneRepository organizzazioneRepository;
    private final OrganizzatoreRepository organizzatoreRepository;

    @Autowired
    public EmailService(JavaMailSender mailSender, OrganizzazioneRepository organizzazioneRepository, OrganizzatoreRepository organizzatoreRepository) {
        this.mailSender = mailSender;
        this.organizzazioneRepository = organizzazioneRepository;
        this.organizzatoreRepository = organizzatoreRepository;
    }

    public void sendAddEmail(String recipient, Long idOrganizzazione) {

        Organizzazione organizzazione = organizzazioneRepository.findById(idOrganizzazione).orElse(null);

        if (organizzazione == null) {
            throw new IllegalArgumentException("Organizzazione inesistente");
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipient);
        message.setSubject(ORGANIZATION_ADD_SUBJECT);

        String text = "Ciao,\n\nSei stato aggiunto all'organizzazione " + organizzazione.getNome() + " su EventGo.\n"
                + "Ora puoi accedere alla pagina dell'organizzazione dalla tua area personale.\n\n"
                + "Grazie per averci scelto!\n\n"
                + "Lo staff di EventGo";

        message.setText(text);
        mailSender.send(message);

    }

    public void sendRemEmail(Long idOrganizzatore, Long idOrganizzazione) {

        Organizzatore organizzatore = organizzatoreRepository.findById(idOrganizzatore).orElse(null);
        Organizzazione organizzazione = organizzazioneRepository.findById(idOrganizzazione).orElse(null);

        if (organizzatore == null || organizzazione == null) {
            throw new IllegalArgumentException("Organizzatore o organizzazione inesistente");
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(organizzatore.getMail());
        message.setSubject(ORGANIZATION_REM_SUBJECT);

        String text = "Ciao,\n\nSei stato rimosso dall'organizzazione " + organizzazione.getNome() + " su EventGo.\n"
                + "Non potrai più accedere alla pagina dell'organizzazione dalla tua area personale.\n\n"
                + "Grazie per averci scelto!\n\n"
                + "Lo staff di EventGo";

        message.setText(text);
        mailSender.send(message);

    }

}
