package com.meuprojetotcc.autenticacao_diplomas.model.user;

import com.meuprojetotcc.autenticacao_diplomas.model.user.Role;


public class UserResponseDto {
    private Long id;
    private String nome;
    private String apelido;
    private String email;

    private Role role;

    public UserResponseDto() {}

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.nome = user.getNome();
        this.apelido = user.getApelido();
        this.email = user.getEmail();
        this.role = user.getRole();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getApelido() { return apelido; }
    public void setApelido(String apelido) { this.apelido = apelido; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


}
