package it.unife.ingsw202324.EventGo.exceptions;

/**
 * Eccezione personalizzata che viene lanciata quando si tenta di creare
 * o aggiungere un'entità che esiste già nel sistema.
 */
public class DuplicatedEntityException extends RuntimeException{

    /**
     * Costruisce una nuova eccezione DuplicatedEntityException con il messaggio specificato.
     *
     * @param message Il messaggio di dettaglio dell'eccezione.
     */
    public DuplicatedEntityException(String message) {
        super(message);
    }
}
