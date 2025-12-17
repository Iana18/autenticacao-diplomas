package com.meuprojetotcc.autenticacao_diplomas.model.diploma;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.meuprojetotcc.autenticacao_diplomas.model.Curso.Curso;
import com.meuprojetotcc.autenticacao_diplomas.model.Estudante.Estudante;
import com.meuprojetotcc.autenticacao_diplomas.model.Instituicao.Instituicao;
import com.meuprojetotcc.autenticacao_diplomas.model.user.User;
import com.meuprojetotcc.autenticacao_diplomas.model.DocumentoAcademico;
import com.meuprojetotcc.autenticacao_diplomas.model.certificado.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

@Entity
public class Diploma extends DocumentoAcademico {

    private String tipoDiploma;
    private Double notaFinal;
    private int cargaHoraria;
    // Diploma.java
    @Column(unique = true, nullable = false)
    private String numeroDiploma;
    private String registroMinisterio;
    private GrauAcademico grauAcademico;
    private LocalDateTime dataConclusao;



    @Override
    @JsonBackReference
    public Estudante getEstudante() {
        return super.getEstudante();
    }

    public Diploma() {}

    public Diploma(Estudante estudante, Curso curso, Instituicao instituicao, User criadoPor,
                   String tipoDiploma, Double notaFinal, int cargaHoraria, GrauAcademico grauAcademico,
                   String numeroDiploma, String registroMinisterio, LocalDateTime dataConclusao) {
        this.setEstudante(estudante);
        this.setCurso(curso);
        this.setInstituicao(instituicao);
        this.setCriadoPor(criadoPor);
        this.setStatus(Status.ATIVO);
        this.setDataEmissao(LocalDateTime.now());

        this.tipoDiploma = tipoDiploma;
        this.notaFinal = notaFinal;
        this.cargaHoraria = cargaHoraria;
        this.grauAcademico = grauAcademico;
        this.numeroDiploma = numeroDiploma;
        this.registroMinisterio = registroMinisterio;
        this.dataConclusao = dataConclusao;
    }




    // ===== GETTERS E SETTERS =====

    public String getTipoDiploma() {
        return tipoDiploma;
    }

    public void setTipoDiploma(String tipoDiploma) {
        this.tipoDiploma = tipoDiploma;
    }

    public Double getNotaFinal() {
        return notaFinal;
    }

    public void setNotaFinal(Double notaFinal) {
        this.notaFinal = notaFinal;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public String getNumeroDiploma() {
        return numeroDiploma;
    }

    public void setNumeroDiploma(String numeroDiploma) {
        this.numeroDiploma = numeroDiploma;
    }

    public String getRegistroMinisterio() {
        return registroMinisterio;
    }

    public void setRegistroMinisterio(String registroMinisterio) {
        this.registroMinisterio = registroMinisterio;
    }

    public GrauAcademico getGrauAcademico() {
        return grauAcademico;
    }

    public void setGrauAcademico(GrauAcademico grauAcademico) {
        this.grauAcademico = grauAcademico;
    }

    public LocalDateTime getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(LocalDateTime dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

    // Métodos revogar e reemitir já vêm de DocumentoAcademico
}
