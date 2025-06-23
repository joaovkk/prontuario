package com.example.prontuario.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "pacientes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório.")
    private String nome;

    @NotNull(message = "A data de nascimento é obrigatória.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

    @NotBlank(message = "O CPF é obrigatório.")
    @Size(min = 11, max = 11, message = "O CPF deve ter exatamente 11 dígitos.")
    private String cpf;

    @NotBlank(message = "O telefone é obrigatório.")
    @Size(min = 8, max = 15, message = "O telefone deve ter entre 8 e 15 caracteres.")
    private String telefone;

    @NotBlank(message = "O gênero é obrigatório.")
    private String genero;

    @NotBlank(message = "O tipo sanguíneo é obrigatório.")
    private String tipoSanguineo;

    @NotNull(message = "O peso é obrigatório.")
    private Double peso;

    @NotNull(message = "A altura é obrigatória.")

    private Double altura;

    @NotBlank(message = "A limitação é obrigatória.")
    private String limitacao;

    @NotBlank(message = "O histórico médico é obrigatório.")
    private String historicoMedico;

    @NotBlank(message = "A alergia é obrigatória.")
    private String alergia;
}
