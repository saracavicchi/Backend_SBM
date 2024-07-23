package it.unife.ingsw202324.EventGo.exceptions;

public class DuplicatedEntityException extends RuntimeException{
    public DuplicatedEntityException(String message) {
        super(message);
    }
}
