package com.meuprojetotcc.autenticacao_diplomas.model.certificado;


import com.meuprojetotcc.autenticacao_diplomas.model.Estudante.Estudante;

import java.time.LocalDateTime;
public class CertificadoDTO {

    private Long estudanteId;
    private Long cursoId;
    private Long instituicaoId;
    private String enderecoTransacao;
    private String hashBlockchain;
    private Status status;  // <-- adicione este campo

    private Long criadoPorId;


    // GETTERS E SETTERS


    public Long getCriadoPorId() {
        return criadoPorId;
    }

    public void setCriadoPorId(Long criadoPorId) {
        this.criadoPorId = criadoPorId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


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

    public String getEnderecoTransacao() {
        return enderecoTransacao;
    }

    public void setEnderecoTransacao(String enderecoTransacao) {
        this.enderecoTransacao = enderecoTransacao;
    }

    public String getHashBlockchain() {
        return hashBlockchain;
    }

    public void setHashBlockchain(String hashBlockchain) {
        this.hashBlockchain = hashBlockchain;
    }
}