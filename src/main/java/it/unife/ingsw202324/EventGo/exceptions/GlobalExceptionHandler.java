package it.unife.ingsw202324.EventGo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * Gestore globale delle eccezioni per l'applicazione.
 * Fornisce un metodo per gestire le eccezioni di caricamento file troppo grandi.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Gestisce l'eccezione quando la dimensione massima del file di caricamento è superata.
     *
     * @param e l'eccezione MaxUploadSizeExceededException
     * @return ResponseEntity con il messaggio di errore e lo stato HTTP 413 Payload Too Large
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<String> handleMaxSizeException(MaxUploadSizeExceededException e) {
        return new ResponseEntity<>("L'immagine è troppo grande (max 2MB)", HttpStatus.PAYLOAD_TOO_LARGE);
    }

}
