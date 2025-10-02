package br.com.medtech.ms_medtech.dtos.enfermeiros;

import br.com.medtech.ms_medtech.entities.Login;
import br.com.medtech.ms_medtech.entities.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record CadastrarEnfermeiroDTO(
        UUID id,
        String nome,
        @JsonFormat(pattern = "dd/MM/yyyy") LocalDate dataNascimento,
        String telefone,
        String coren,
        @NotNull
        Login login,
        @NotNull
        Role role
) {
}