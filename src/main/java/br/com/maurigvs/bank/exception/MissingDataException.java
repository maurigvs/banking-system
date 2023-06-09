package br.com.maurigvs.bank.exception;

public class MissingDataException extends RuntimeException {

    public MissingDataException() {
        super("Arguments can not be null");
    }
}
