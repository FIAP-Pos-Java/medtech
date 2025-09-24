package br.com.medtech.ms_medtech.dtos.logins;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record AtualizarLoginDTO(
        UUID id,
        @NotBlank(message = "o email é obrigatório")
        @Email(message = "o email inválido")
        String email,
        @NotBlank(message = "a senha é obrigatória")
        @Size(min = 5, message = "a senha deve ter no minimo 5 caracteres")
        String password
) {
}
