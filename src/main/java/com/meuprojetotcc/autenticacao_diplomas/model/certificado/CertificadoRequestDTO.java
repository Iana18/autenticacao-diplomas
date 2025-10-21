package com.meuprojetotcc.autenticacao_diplomas.model.certificado;

public class CertificadoRequestDTO {

    private Long estudanteId;
    private Long cursoId;
    private Long instituicaoId;
    private Long criadoPorId;

    private String tipoParticipacao;  // Ex: "Workshop", "Palestra"
    private int cargaHoraria;

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

    public String getTipoParticipacao() {
        return tipoParticipacao;
    }

    public void setTipoParticipacao(String tipoParticipacao) {
        this.tipoParticipacao = tipoParticipacao;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }
}
