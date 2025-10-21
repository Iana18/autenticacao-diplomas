package com.meuprojetotcc.autenticacao_diplomas.model.diploma;

import java.time.LocalDate;

public class DiplomaRequestDTO {

    private Long estudanteId;
    private Long cursoId;
    private Long instituicaoId;
    private Long criadoPorId;

    private String tipoDiploma;
    private Double notaFinal;
    private int cargaHoraria;
    private String numeroDiploma;
    private String registroMinisterio;
    private GrauAcademico grauAcademico; // pode ser ENUM: BACHAREL, LICENCIATURA, MESTRE, DOUTOR
    private LocalDate dataConclusao;

    // ===== GETTERS E SETTERS =====
    public Long getEstudanteId() {
        return estudanteId;
    }

    public void setEstudanteId(Long estudanteId) {
        this.estudanteId = estudanteId;
    }

    public Long getCursoId() {
        return cursoId;
    }

    public void setCursoId(Long cursoId) {
        this.cursoId = cursoId;
    }

    public Long getInstituicaoId() {
        return instituicaoId;
    }

    public void setInstituicaoId(Long instituicaoId) {
        this.instituicaoId = instituicaoId;
    }

    public Long getCriadoPorId() {
        return criadoPorId;
    }

    public void setCriadoPorId(Long criadoPorId) {
        this.criadoPorId = criadoPorId;
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
