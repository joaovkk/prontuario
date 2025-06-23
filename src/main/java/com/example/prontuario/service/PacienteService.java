package com.example.prontuario.service;

import com.example.prontuario.exceptions.PacienteNotFoundException;
import com.example.prontuario.exceptions.UniqueCPFException;
import com.example.prontuario.model.Paciente;
import com.example.prontuario.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    private void validarPaciente(Paciente paciente) {
        if (paciente.getDataNascimento() != null && paciente.getDataNascimento().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Data de nascimento não pode ser no futuro.");
        }

        Optional<Paciente> existente = pacienteRepository.findByCpf(paciente.getCpf());
        if (existente.isPresent() && !existente.get().getId().equals(paciente.getId())) {
            throw new UniqueCPFException("CPF já cadastrado");
        }
    }

    public List<Paciente> listAllPacientes(){
        return pacienteRepository.findAll();
    }

    public Paciente findPacienteById(Long id) {
        return pacienteRepository.findById(id)
                .orElseThrow(() -> new PacienteNotFoundException("Paciente com id " + id + " não encontrado."));
    }

    public List<Paciente> buscarPacientesPorNome(String nome) {
        return pacienteRepository.findByNomeContainingIgnoreCase(nome);
    }

    public Paciente updatePaciente(Long id, Paciente pacienteAtualizado) {
        Paciente pacienteExistente = findPacienteById(id);

        pacienteAtualizado.setId(id);
        return savePaciente(pacienteAtualizado);
    }


    public Paciente savePaciente(Paciente paciente){
        validarPaciente(paciente);
        return pacienteRepository.save(paciente);
    }

    public void deletePacienteById(Long id){
        if(!pacienteRepository.existsById(id)){
            throw new PacienteNotFoundException("Paciente com id" + id + " não encontrado.");
        }
        pacienteRepository.deleteById(id);
    }


}
