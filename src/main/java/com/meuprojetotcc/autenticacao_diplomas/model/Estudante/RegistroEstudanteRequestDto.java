package com.meuprojetotcc.autenticacao_diplomas.model.Estudante;

import java.time.LocalDate;

public class RegistroEstudanteRequestDto {
    private String nomeCompleto;
    private String email;
    private String numeroMatricula;
    private LocalDate dataNascimento;
    private String genero;
    private String senha;

    // Getters e setters
    public String getNomeCompleto() { return nomeCompleto; }
    public void setNomeCompleto(String nomeCompleto) { this.nomeCompleto = nomeCompleto; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getNumeroMatricula() { return numeroMatricula; }
    public void setNumeroMatricula(String numeroMatricula) { this.numeroMatricula = numeroMatricula; }
    public LocalDate getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }
    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}
