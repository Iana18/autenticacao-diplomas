package com.meuprojetotcc.autenticacao_diplomas.model.Estudante;



import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "estudantes")
public class Estudante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomeCompleto;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "numero_matricula", unique = true, nullable = false)
    private String numeroMatricula;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    private String genero;

    // === CONSTRUTORES ===

    public Estudante() { super();}

    public Estudante(String nomeCompleto, String email, String numeroMatricula, LocalDate dataNascimento, String genero) {
        this.nomeCompleto = nomeCompleto;
        this.email = email;
        this.numeroMatricula = numeroMatricula;
        this.dataNascimento = dataNascimento;
        this.genero = genero;
    }

    // === GETTERS E SETTERS ===

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumeroMatricula() {
        return numeroMatricula;
    }

    public void setNumeroMatricula(String numeroMatricula) {
        this.numeroMatricula = numeroMatricula;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
}
