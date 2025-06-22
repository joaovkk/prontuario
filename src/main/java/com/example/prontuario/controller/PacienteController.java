package com.example.prontuario.controller;

import com.example.prontuario.model.Paciente;
import com.example.prontuario.service.PacienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    private final PacienteService pacienteService;

    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @GetMapping
    public List<Paciente> listAllPacientes(){
        return pacienteService.listAllPacientes();
    }

    @GetMapping("/{id}")
    //@PathVariable pega o id do endpoint
    public ResponseEntity<Paciente> findPacienteById(@PathVariable Long id){
        return pacienteService.findPacienteById(id)
                //se a entidade existir retorna HTTP 200 ok com o paciente no body json
                .map(ResponseEntity::ok)
                // se não existir retorna HTTP 404 not found
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    //@RequestBody converte o json da requisição e transforma em um objeto
    public Paciente createPaciente(@RequestBody Paciente paciente){
        return pacienteService.savePaciente(paciente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePacienteById(@PathVariable Long id){
        pacienteService.deletePacienteById(id);
        return ResponseEntity.noContent().build();
    }
}
