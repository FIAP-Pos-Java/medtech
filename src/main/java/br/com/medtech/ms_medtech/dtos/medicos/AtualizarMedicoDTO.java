package br.com.medtech.ms_medtech.dtos.medicos;

import br.com.medtech.ms_medtech.entities.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record AtualizarMedicoDTO(
        UUID id,
        String nome,
        @JsonFormat(pattern = "dd/MM/yyyy") LocalDate dataNascimento,
        String telefone,
        String crm,
        String especialidade,
        @NotNull
        Role role
) {
}