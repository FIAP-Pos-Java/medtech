package br.com.medtech.ms_medtech.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "tb_enfermeiro")
public final class Enfermeiro extends Usuario{
    private String coren;

    public Enfermeiro() {}

    public Enfermeiro(UUID id, String nome, LocalDate dataNascimento, LocalDateTime dataCadastro, String telefone, boolean enabled, Login login, Role role, String coren) {
        super(id, nome, dataNascimento, dataCadastro, telefone, enabled, login, role);
        this.coren = coren;
    }
}
