package br.com.medtech.ms_medtech.exceptions;

public class LoginCadastradoException extends RuntimeException {
    public LoginCadastradoException(String message) {
        super(message);
    }
}