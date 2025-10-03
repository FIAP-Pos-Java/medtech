package br.com.medtech.ms_medtech.repositories;

import br.com.medtech.ms_medtech.entities.Enfermeiro;
import br.com.medtech.ms_medtech.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EnfermeiroRepository extends JpaRepository<Enfermeiro, UUID> {
    Optional<Enfermeiro> findByLogin_Id(UUID loginId);
    Optional<Enfermeiro> findByLoginEmail(String email);
}