package com.maxoliver.desafiojavajrstefanini.exceptions;

public class EntityInUseException extends RuntimeException{
    public EntityInUseException(String message) {
        super(message);
    }
}
