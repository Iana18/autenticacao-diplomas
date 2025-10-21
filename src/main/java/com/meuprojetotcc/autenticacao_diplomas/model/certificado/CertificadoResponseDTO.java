package com.meuprojetotcc.autenticacao_diplomas.model.certificado;

import lombok.*;

import java.time.LocalDateTime;

@Data
public class CertificadoResponseDTO {
    private Long id;

    private String estudanteNome;
    private String cursoNome;
    private String instituicaoNome;
    private String criadoPorNome;

    private String tipoParticipacao;
    private int cargaHoraria;

    private LocalDateTime dataEmissao;
    private LocalDateTime dataRevogacao;

    private String status;
    private String hashBlockchain;
    private String enderecoTransacao;

    // ===== GETTERS E SETTERS =====

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEstudanteNome() {
        return estudanteNome;
    }

    public void setEstudanteNome(String estudanteNome) {
        this.estudanteNome = estudanteNome;
    }

    public String getCursoNome() {
        return cursoNome;
    }

    public void setCursoNome(String cursoNome) {
        this.cursoNome = cursoNome;
    }

    public String getInstituicaoNome() {
        return instituicaoNome;
    }

    public void setInstituicaoNome(String instituicaoNome) {
        this.instituicaoNome = instituicaoNome;
    }

    public String getCriadoPorNome() {
        return criadoPorNome;
    }

    public void setCriadoPorNome(String criadoPorNome) {
        this.criadoPorNome = criadoPorNome;
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

    public LocalDateTime getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(LocalDateTime dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public LocalDateTime getDataRevogacao() {
        return dataRevogacao;
    }

    public void setDataRevogacao(LocalDateTime dataRevogacao) {
        this.dataRevogacao = dataRevogacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
}