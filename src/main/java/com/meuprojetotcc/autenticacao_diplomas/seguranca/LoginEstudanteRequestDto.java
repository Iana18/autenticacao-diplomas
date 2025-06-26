package com.meuprojetotcc.autenticacao_diplomas.seguranca;

import jakarta.persistence.Column;

public class LoginEstudanteRequestDto {
    private String nomeCompleto;

    private String numeroMatricula;

    public String getNumeroMatricula() {
        return numeroMatricula;
    }

    public void setNumeroMatricula(String numeroMatricula) {
        this.numeroMatricula = numeroMatricula;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }
}
