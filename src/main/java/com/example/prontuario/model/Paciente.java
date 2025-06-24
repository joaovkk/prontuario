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
    @Size(min = 2,max = 100, message = "O nome do cliente deve conter no minimo 2 caracteres e no máximo 100.")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ ]+$", message = "O nome deve conter apenas letras e espaços, sem números ou caracteres especiais.")
    private String nome;

    @NotNull(message = "A data de nascimento é obrigatória.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

    @NotBlank(message = "O CPF é obrigatório.")
    @Size(min = 11, max = 11, message = "O CPF deve ter exatamente 11 dígitos.")
    @Pattern(regexp = "\\d{11}", message = "O CPF deve conter apenas números.")
    private String cpf;

    @NotBlank(message = "O telefone é obrigatório.")

    @Pattern(regexp = "\\d{11}", message = "O telefone deve conter apenas números e ter 11 digitos.")
    private String telefone;

    @NotBlank(message = "O gênero é obrigatório.")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ ]+$", message = "O genero deve conter apenas letras e espaços, sem números ou caracteres especiais.")
    private String genero;

    @NotBlank(message = "O tipo sanguíneo é obrigatório.")
    @Pattern(regexp = "^(A|B|AB|O)[+-]$", message = "O tipo sanguíneo deve ser um dos seguintes: A+, A-, B+, B-, AB+, AB-, O+ ou O-.")
    private String tipoSanguineo;

    @NotNull(message = "O peso é obrigatório.")

    @DecimalMin(value = "2.0", inclusive = true, message = "O peso deve ser no mínimo 2 kg.")
    @DecimalMax(value = "300.0", inclusive = true, message = "O peso deve ser no máximo 300 kg.")
    private Double peso;

    @NotNull(message = "A altura é obrigatória.")

    @DecimalMin(value = "0.30", inclusive = true, message = "A altura deve ser no mínimo 0.30 metros.")
    @DecimalMax(value = "2.50", inclusive = true, message = "A altura deve ser no máximo 2.50 metros.")
    private Double altura;

    @NotBlank(message = "A limitação é obrigatória.")
    @Size(max = 100, message = "A limitação deve conter no máximo 500 caracteres")
    private String limitacao;

    @NotBlank(message = "O histórico médico é obrigatório.")
    @Size(max = 500, message = "O histórico médico deve conter no máximo 500 caracteres")
    private String historicoMedico;

    @NotBlank(message = "A alergia é obrigatória.")
    @Size(max = 300, message = "Alergia deve conter no máximo 300 caracteres")
    private String alergia;
}
