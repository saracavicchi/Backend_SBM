package it.unife.ingsw202324.EventGo.services;

import it.unife.ingsw202324.EventGo.models.CarteOrganizzatore;
import it.unife.ingsw202324.EventGo.models.Organizzatore;
import it.unife.ingsw202324.EventGo.repositories.CarteOrganizzatoreRepository;
import jakarta.transaction.Transactional;
import org.hibernate.annotations.NaturalId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Transactional
@Service
public class CarteOrganizzatoreService {

    private final CarteOrganizzatoreRepository carteOrganizzatoreRepository;

    @Autowired
    public CarteOrganizzatoreService(CarteOrganizzatoreRepository carteOrganizzatoreRepository) {
        this.carteOrganizzatoreRepository = carteOrganizzatoreRepository;
    }


    public void modificaCartaOrganizzatore(Organizzatore organizzatore, CarteOrganizzatore carta) {

        if (carta.getId() != null) {


            CarteOrganizzatore cartaEsistente = carteOrganizzatoreRepository.findById(carta.getId()).orElse(null);

            if (cartaEsistente == null) {
                throw new RuntimeException("Carta non trovata");
            }

            if (carta.getNumero().isEmpty()) {
                organizzatore.getCarte().remove(cartaEsistente);
                carteOrganizzatoreRepository.delete(cartaEsistente);
            } else {
                cartaEsistente.setNumero(carta.getNumero());
                cartaEsistente.setDataScadenza(carta.getDataScadenza());
                cartaEsistente.setCvv(carta.getCvv());
                cartaEsistente.setNome(carta.getNome());
                cartaEsistente.setCognome(carta.getCognome());
                cartaEsistente.setOrganizzatore(organizzatore);
                carteOrganizzatoreRepository.save(cartaEsistente);
            }

        } else if (!carta.getNumero().isEmpty()) {
            organizzatore.getCarte().add(carta);
        }

    }
}
