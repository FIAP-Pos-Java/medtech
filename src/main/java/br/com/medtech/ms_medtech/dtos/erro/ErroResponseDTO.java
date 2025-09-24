package br.com.medtech.ms_medtech.dtos.erro;

import java.time.LocalDateTime;

public record ErroResponseDTO(int status, String message, LocalDateTime timestamp) {
}