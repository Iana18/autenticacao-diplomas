package com.meuprojetotcc.autenticacao_diplomas.model.certificado;

import com.meuprojetotcc.autenticacao_diplomas.model.Curso.Curso;
import com.meuprojetotcc.autenticacao_diplomas.model.Estudante.Estudante;
import com.meuprojetotcc.autenticacao_diplomas.model.Instituicao.Instituicao;
import com.meuprojetotcc.autenticacao_diplomas.model.user.User;
import com.meuprojetotcc.autenticacao_diplomas.model.certificado.Status;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "diplomas")
public class Certificado {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "estudante_id")
    private Estudante estudante;

    @ManyToOne(optional = false)
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @ManyToOne(optional = false)
    @JoinColumn(name = "instituicao_id")
    private Instituicao instituicao;

    @ManyToOne(optional = false)
    @JoinColumn(name = "criado_por_id")
    private User criadoPor;



    @Column(nullable = false)
    private LocalDateTime dataEmissao;

    private LocalDateTime dataRevogacao;  // Null se ativo

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    private String enderecoTransacao;

    private String hashBlockchain;

    // === CONSTRUTORES ===

   /* public Certificado() {
        this.status = "ativo";
        this.dataEmissao = LocalDateTime.now();
    }
*/

    public Certificado() {
        // construtor vazio necessário para JPA e para usar new Certificado()
    }
    public Certificado(CertificadoDTO dados, Estudante estudante, Curso curso, Instituicao instituicao) {
        this.estudante = estudante;
        this.curso = curso;
        this.instituicao = instituicao;
      //  this.us = user;
        this.dataEmissao = LocalDateTime.now();
        //this.status = "ativo";
        this.hashBlockchain = dados.getHashBlockchain();
        this.enderecoTransacao = dados.getEnderecoTransacao();
    }




    // === GETTERS E SETTERS ===

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(LocalDateTime dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getDataRevogacao() {
        return  dataRevogacao ;
    }

    public void setDataRevogacao(LocalDateTime  dataRevogacao) {
        this. dataRevogacao =  dataRevogacao;
    }

    public String getHashBlockchain() {
        return hashBlockchain;
    }

    public void setHashBlockchain(String hashBlockchain) {
        this.hashBlockchain = hashBlockchain;
    }

    public String getEnderecoTransacao() {
        return enderecoTransacao;
    }

    public void setEnderecoTransacao(String enderecoTransacao) {
        this.enderecoTransacao = enderecoTransacao;
    }

    public Estudante getEstudante() {
        return estudante;
    }

    public void setEstudante(Estudante estudante) {
        this.estudante = estudante;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Instituicao getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(Instituicao instituicao) {
        this.instituicao = instituicao;
    }
    public User getCriadoPor() {
        return criadoPor;
    }

    public void setCriadoPor(User criadoPor) {
        this.criadoPor = criadoPor;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

