package com.meuprojetotcc.autenticacao_diplomas.seguranca;

public class AuthRequestDto {

    private String username; // email para User, matrícula para Estudante
    private String password;

    // Getters e setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
