package br.com.medtech.ms_medtech.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_medico")
public final class Medico extends Usuario{
    private String crm;
    private String especialidade;
}
