package com.example.prontuario.service;

import com.example.prontuario.model.Paciente;
import com.example.prontuario.repository.PacienteRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;


    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public List<Paciente> listAllPacientes(){
        return pacienteRepository.findAll();
    }

    public Optional<Paciente> findPacienteById(Long id){
        return pacienteRepository.findById(id);
    }

    public Paciente savePaciente(Paciente paciente){
        return pacienteRepository.save(paciente);
    }

    public void deletePacienteById(Long id){
        pacienteRepository.deleteById(id);
    }


}
