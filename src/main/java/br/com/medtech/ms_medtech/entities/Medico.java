package br.com.medtech.ms_medtech.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "tb_medico")
public final class Medico extends Usuario {
    private String crm;
    private String especialidade;

    public Medico() {}

    public Medico(UUID id, String nome, LocalDate dataNascimento, LocalDateTime dataCadastro, String telefone, boolean enabled, Login login, Role role, String crm, String especialidade) {
        super(id, nome, dataNascimento, dataCadastro, telefone, enabled, login, role);
        this.crm = crm;
        this.especialidade = especialidade;
    }
}
