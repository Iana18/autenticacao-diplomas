package com.meuprojetotcc.autenticacao_diplomas.model.diploma;

import com.meuprojetotcc.autenticacao_diplomas.model.Curso.Curso;
import com.meuprojetotcc.autenticacao_diplomas.model.Estudante.Estudante;
import com.meuprojetotcc.autenticacao_diplomas.model.Instituicao.Instituicao;
import com.meuprojetotcc.autenticacao_diplomas.model.user.User;

import java.time.LocalDate;

public class DiplomaRequestDTO {

    private Estudante estudante;
    private Curso curso;
    private Instituicao instituicao;
    private User criadoPor;

    private String tipoDiploma;
    private Double notaFinal;
    private int cargaHoraria;
    private String numeroDiploma;
    private String registroMinisterio;
    private GrauAcademico grauAcademico; // pode ser ENUM: BACHAREL, LICENCIATURA, MESTRE, DOUTOR
    private LocalDate dataConclusao;
    private String assinaturaInstituicao; // assinatura digital da IES
    private String carimboInstituicao;    // carimbo digital ou CID

    // ===== GETTERS E SETTERS =====


    public DiplomaRequestDTO() {}

    public String getCarimboInstituicao() {
        return carimboInstituicao;
    }

    public void setCarimboInstituicao(String carimboInstituicao) {
        this.carimboInstituicao = carimboInstituicao;
    }

    public String getAssinaturaInstituicao() {
        return assinaturaInstituicao;
    }

    public void setAssinaturaInstituicao(String assinaturaInstituicao) {
        this.assinaturaInstituicao = assinaturaInstituicao;
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

    public LocalDate getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(LocalDate dataConclusao) {
        this.dataConclusao = dataConclusao;
    }
}
