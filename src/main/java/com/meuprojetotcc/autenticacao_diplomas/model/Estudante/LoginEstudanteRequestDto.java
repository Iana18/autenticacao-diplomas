package com.meuprojetotcc.autenticacao_diplomas.model.Estudante;

public class LoginEstudanteRequestDto {
    private String numeroMatricula;
    private String senha;

    public String getNumeroMatricula() {
        return numeroMatricula;
    }

    public void setNumeroMatricula(String numeroMatricula) {
        this.numeroMatricula = numeroMatricula;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
