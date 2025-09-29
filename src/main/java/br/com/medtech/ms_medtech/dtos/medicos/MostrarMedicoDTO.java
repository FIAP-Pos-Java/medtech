package br.com.medtech.ms_medtech.dtos.pacientes;

import br.com.medtech.ms_medtech.entities.Role;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record MostrarPacienteDTO(
        UUID id,
        String nome,
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate dataNascimento,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime dataCadastro,
        String telefone,
        MostrarLoginToPacienteDTO login,
        Role role
) {
}