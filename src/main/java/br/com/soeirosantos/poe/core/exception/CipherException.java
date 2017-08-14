package br.com.soeirosantos.poe.core.exception;

public class CipherException extends RuntimeException {

    public CipherException(String message, Throwable e) {
        super(message, e);
    }

}
