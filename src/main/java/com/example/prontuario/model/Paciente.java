package com.example.prontuario.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "Pacientes")

public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private LocalDate dataNascimento;

    private String cpf;

    private String telefone;

    private String genero;

    private String tipoSanguineo;

    private Double peso;

    private Double altura;

    private String limitacao;

    private String historicoMedico;

    private String alergia;

}
