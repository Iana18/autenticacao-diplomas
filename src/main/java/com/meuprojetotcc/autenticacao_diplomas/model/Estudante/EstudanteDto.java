package com.meuprojetotcc.autenticacao_diplomas.model.Estudante;

import java.time.LocalDate;

public class EstudanteDto {

    private Long id;
    private String nomeCompleto;
    private String email;
    private String senha;
    private String numeroMatricula;
    private LocalDate dataNascimento;
    private String genero;

    // Em vez de objetos Curso e Instituicao, usamos apenas os nomes
    private String nomeCurso;
    private String nomeInstituicao;

    // === Getters e Setters ===
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

    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
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

    public String getNomeCurso() {
        return nomeCurso;
    }
    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }

    public String getNomeInstituicao() {
        return nomeInstituicao;
    }
    public void setNomeInstituicao(String nomeInstituicao) {
        this.nomeInstituicao = nomeInstituicao;
    }
}
