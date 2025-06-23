package com.example.prontuario.controller;

import com.example.prontuario.exceptions.PacienteNotFoundException;
import com.example.prontuario.exceptions.UniqueCPFException;
import com.example.prontuario.model.Paciente;
import com.example.prontuario.service.PacienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
    public ResponseEntity<List<Paciente>> listAllPacientes() {
        List<Paciente> pacientes = pacienteService.listAllPacientes();
        if (pacientes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(pacienteService.listAllPacientes());

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

    @GetMapping("/buscar")
    public ResponseEntity<?> buscarPacientesPorNome(@RequestParam String nome) {
        List<Paciente> pacientes = pacienteService.buscarPacientesPorNome(nome);
        if (pacientes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhum paciente encontrado com o nome: " + nome);
        }
        return ResponseEntity.ok(pacientes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePaciente(@PathVariable Long id, @Valid @RequestBody Paciente pacienteAtualizado) {
        try {
            Paciente paciente = pacienteService.updatePaciente(id, pacienteAtualizado);
            return ResponseEntity.ok(paciente);
        } catch (PacienteNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (UniqueCPFException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createPaciente(@Valid @RequestBody Paciente paciente) {
        try {
            Paciente novoPaciente = pacienteService.savePaciente(paciente);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoPaciente);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (UniqueCPFException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
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
