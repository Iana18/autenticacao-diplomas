package com.meuprojetotcc.autenticacao_diplomas.model.Estudante;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.meuprojetotcc.autenticacao_diplomas.model.Curso.Curso;
import com.meuprojetotcc.autenticacao_diplomas.model.Instituicao.Instituicao;
import com.meuprojetotcc.autenticacao_diplomas.model.certificado.Certificado;
import com.meuprojetotcc.autenticacao_diplomas.model.diploma.Diploma;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "estudantes")
public class Estudante implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeCompleto;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = true)
    private String senha;

    @Column(name = "numero_matricula", unique = true, nullable = false)
    private String numeroMatricula;

    private LocalDate dataNascimento;

    private String genero;


    // Ativação
    @Column(name = "ativo")
    private boolean ativo = false;

    @Column(name = "token_ativacao", unique = true)
    private String tokenAtivacao;

    private LocalDateTime criadoEm = LocalDateTime.now();

    // 🔹 Relacionamento com Curso
    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;

    // 🔹 Relacionamento com Instituição
    @ManyToOne
    @JoinColumn(name = "instituicao_id")
    private Instituicao instituicao;

    // 🔹 Diplomas e Certificados
    @OneToMany(mappedBy = "estudante", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Diploma> diplomas = new ArrayList<>();

    @OneToMany(mappedBy = "estudante", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Certificado> certificados = new ArrayList<>();

    // === CONSTRUTORES ===
    public Estudante() {}

    public Estudante(String nomeCompleto, String email, String senha, String numeroMatricula,
                     LocalDate dataNascimento, String genero, Curso curso, Instituicao instituicao) {
        this.nomeCompleto = nomeCompleto;
        this.email = email;
        this.senha = senha;
        this.numeroMatricula = numeroMatricula;
        this.dataNascimento = dataNascimento;
        this.genero = genero;
        this.curso = curso;
        this.instituicao = instituicao;
        this.tokenAtivacao = UUID.randomUUID().toString();
        this.ativo = false;
        this.criadoEm = LocalDateTime.now();
    }

    // === GETTERS E SETTERS ===
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNomeCompleto() { return nomeCompleto; }
    public void setNomeCompleto(String nomeCompleto) { this.nomeCompleto = nomeCompleto; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getNumeroMatricula() { return numeroMatricula; }
    public void setNumeroMatricula(String numeroMatricula) { this.numeroMatricula = numeroMatricula; }

    public LocalDate getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public Curso getCurso() { return curso; }
    public void setCurso(Curso curso) { this.curso = curso; }

    public Instituicao getInstituicao() { return instituicao; }
    public void setInstituicao(Instituicao instituicao) { this.instituicao = instituicao; }

    public List<Diploma> getDiplomas() { return diplomas; }
    public void setDiplomas(List<Diploma> diplomas) { this.diplomas = diplomas; }

    public List<Certificado> getCertificados() { return certificados; }
    public void setCertificados(List<Certificado> certificados) { this.certificados = certificados; }


    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getTokenAtivacao() {
        return tokenAtivacao;
    }

    public void setTokenAtivacao(String tokenAtivacao) {
        this.tokenAtivacao = tokenAtivacao;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
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
    public String getUsername() { return this.numeroMatricula; }

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
