package com.meuprojetotcc.autenticacao_diplomas.model.Estudante;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.meuprojetotcc.autenticacao_diplomas.model.certificado.Certificado;
import com.meuprojetotcc.autenticacao_diplomas.model.diploma.Diploma;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "estudantes")
public class Estudante implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   /* @NotBlank(message = "Nome completo é obrigatório")
    @Column(nullable = false)*/
    private String nomeCompleto;
   /* @Email(message = "Email inválido")
    @NotBlank(message = "Email é obrigatório")*/
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(name = "numero_matricula", unique = true, nullable = false)
    private String numeroMatricula;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;
    @OneToMany(mappedBy = "estudante", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Diploma> diplomas = new ArrayList<>();

    @OneToMany(mappedBy = "estudante", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Certificado> certificados = new ArrayList<>();
    private String genero;


    // === CONSTRUTORES ===

    public Estudante() { super();}

    public Estudante(String nomeCompleto, String email, String senha, String numeroMatricula, LocalDate dataNascimento, String genero) {
        this.nomeCompleto = nomeCompleto;
        this.email = email;
        this.numeroMatricula = numeroMatricula;
        this.dataNascimento = dataNascimento;
        this.genero = genero;
        this.senha= senha;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Diploma> getDiplomas() {
        return diplomas;
    }

    public void setDiplomas(List<Diploma> diplomas) {
        this.diplomas = diplomas;
    }

    public List<Certificado> getCertificados() {
        return certificados;
    }

    public void setCertificados(List<Certificado> certificados) {
        this.certificados = certificados;
    }


    // === Implementação UserDetails ===

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_ESTUDANTE"));
    }

    @Override
    @JsonIgnore
    public String getPassword() { return this.senha; }

    @Override
    @JsonIgnore
    public String getUsername() { return this.numeroMatricula; } // login pelo número de matrícula

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() { return true; }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() { return true; }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    @JsonIgnore
    public boolean isEnabled() { return true; }

}
