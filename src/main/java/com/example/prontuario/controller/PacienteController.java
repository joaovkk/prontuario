package com.example.prontuario.controller;

import com.example.prontuario.exceptions.PacienteNotFoundException;
import com.example.prontuario.model.Paciente;
import com.example.prontuario.service.PacienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;


    @GetMapping
    public List<Paciente> listAllPacientes(){
        return pacienteService.listAllPacientes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findPacienteById(@PathVariable Long id){
        try{
            Paciente paciente = pacienteService.findPacienteById(id);
            return ResponseEntity.ok(paciente);
        }
        catch(PacienteNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePaciente(@PathVariable Long id, @RequestBody @Valid Paciente pacienteAtualizado) {
        try {
            Paciente paciente = pacienteService.updatePaciente(id, pacienteAtualizado);
            return ResponseEntity.ok(paciente);
        } catch (PacienteNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


    @PostMapping

    public Paciente createPaciente(@RequestBody @Valid Paciente paciente){
        return pacienteService.savePaciente(paciente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePacienteById(@PathVariable Long id){
        try{
            pacienteService.deletePacienteById(id);
            return ResponseEntity.noContent().build();
        }
        catch(PacienteNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }
}
