package br.com.medtech.ms_medtech.exceptions;

public class LoginNaoEncontradoException extends RuntimeException {
    public LoginNaoEncontradoException(String message) {
        super(message);
    }
}
