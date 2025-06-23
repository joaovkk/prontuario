package com.example.prontuario.exceptions;

public class PacienteNotFoundException extends RuntimeException {
    public PacienteNotFoundException(String mensagem){
        super(mensagem);
    }
}
