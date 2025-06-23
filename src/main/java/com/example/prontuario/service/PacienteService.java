package com.example.prontuario.service;

import com.example.prontuario.exceptions.PacienteNotFoundException;
import com.example.prontuario.model.Paciente;
import com.example.prontuario.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;



    public List<Paciente> listAllPacientes(){
        return pacienteRepository.findAll();
    }

    public Paciente findPacienteById(Long id) {
        return pacienteRepository.findById(id)
                .orElseThrow(() -> new PacienteNotFoundException("Paciente com id " + id + " não encontrado."));
    }

    public Paciente updatePaciente(Long id, Paciente pacienteAtualizado) {
        Paciente pacienteExistente = findPacienteById(id);

        pacienteExistente.setNome(pacienteAtualizado.getNome());
        pacienteExistente.setDataNascimento(pacienteAtualizado.getDataNascimento());
        pacienteExistente.setCpf(pacienteAtualizado.getCpf());
        pacienteExistente.setTelefone(pacienteAtualizado.getTelefone());
        pacienteExistente.setGenero(pacienteAtualizado.getGenero());
        pacienteExistente.setTipoSanguineo(pacienteAtualizado.getTipoSanguineo());
        pacienteExistente.setPeso(pacienteAtualizado.getPeso());
        pacienteExistente.setAltura(pacienteAtualizado.getAltura());
        pacienteExistente.setLimitacao(pacienteAtualizado.getLimitacao());
        pacienteExistente.setHistoricoMedico(pacienteAtualizado.getHistoricoMedico());
        pacienteExistente.setAlergia(pacienteAtualizado.getAlergia());

        return pacienteRepository.save(pacienteExistente);
    }



    public Paciente savePaciente(Paciente paciente){
        return pacienteRepository.save(paciente);
    }

    public void deletePacienteById(Long id){
        if(!pacienteRepository.existsById(id)){
            throw new PacienteNotFoundException("Paciente com id" + id + " não encontrado.");
        }
        pacienteRepository.deleteById(id);
    }


}
