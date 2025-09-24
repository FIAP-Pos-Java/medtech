package br.com.medtech.ms_medtech.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_paciente")
public final class Paciente extends Usuario{
}
