package br.com.medtech.ms_medtech.dtos.pacientes;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.UUID;

public record MostrarLoginToPacienteDTO(
        UUID id,
        String email,
        String password,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime dataUltimoLogin
) {
}