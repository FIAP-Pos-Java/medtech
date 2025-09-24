package br.com.medtech.ms_medtech.repositories;

import br.com.medtech.ms_medtech.entities.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LoginRepository extends JpaRepository<Login, UUID> {
    List<Login> findByEmail(String email);
}