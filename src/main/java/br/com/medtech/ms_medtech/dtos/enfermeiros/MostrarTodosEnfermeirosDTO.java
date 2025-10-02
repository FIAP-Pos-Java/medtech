package br.com.medtech.ms_medtech.dtos.enfermeiros;

import br.com.medtech.ms_medtech.entities.Role;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record MostrarTodosEnfermeirosDTO(
        UUID id,
        String nome,
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate dataNascimento,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
        LocalDateTime dataCadastro,
        String telefone,
        String coren,
        MostrarLoginToEnfermeiroDTO login,
        Role role
) {
}