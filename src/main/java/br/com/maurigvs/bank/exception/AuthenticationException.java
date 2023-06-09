package br.com.maurigvs.bank.exception;

public class AuthenticationException extends RuntimeException {

    public AuthenticationException() {
        super("Account validation failed.");
    }
}