package br.com.medtech.ms_medtech.repositories;

import br.com.medtech.ms_medtech.entities.Medico;
import br.com.medtech.ms_medtech.entities.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, UUID> {
    Optional<Medico> findByLogin_Id(UUID loginId);
}