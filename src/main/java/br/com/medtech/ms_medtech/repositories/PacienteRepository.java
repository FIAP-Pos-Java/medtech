package br.com.medtech.ms_medtech.repositories;

import br.com.medtech.ms_medtech.entities.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, UUID> {
    Optional<Paciente> findByLogin_Id(UUID loginId);
}