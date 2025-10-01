package br.com.medtech.ms_medtech.dtos.medicos;

import br.com.medtech.ms_medtech.dtos.pacientes.MostrarLoginToPacienteDTO;
import br.com.medtech.ms_medtech.entities.Role;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record MostrarTodosMedicosDTO(
        UUID id,
        String nome,
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate dataNascimento,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime dataCadastro,
        String telefone,
        String crm,
        String especialidade,
        MostrarLoginToPacienteDTO login,
        Role role
) {
}