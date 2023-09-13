package com.phorest.exception;

public class PhorestIOException extends RuntimeException {

    public PhorestIOException(String message, Throwable exception){
        super(message, exception);
    }
}
