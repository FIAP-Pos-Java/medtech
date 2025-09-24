package br.com.medtech.ms_medtech.entities;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Role {
    MEDICO,
    ENFERMEIRO,
    PACIENTE;

    @JsonCreator
    public static Role fromString(String role) {
        return Role.valueOf(role.toUpperCase());
    }
}