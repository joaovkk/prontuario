package com.example.prontuario.repository;

import com.example.prontuario.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente,Long> {

    boolean existsByCpf(String cpf);

    Optional<Paciente> findByCpf(String cpf);

    List<Paciente> findByNomeContainingIgnoreCase(String nome);
}
