package com.meuprojetotcc.autenticacao_diplomas.model.verificacao;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


public class VerificacaoDto {

    private Long id;
    private Long certificadoId;
    private Long verificadorId;
    private LocalDateTime dataVerificacao;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCertificadoId() {
        return certificadoId;
    }

    public void setCertificadoId(Long certificadoId) {
        this.certificadoId = certificadoId;
    }

    public Long getVerificadorId() {
        return verificadorId;
    }

    public void setVerificadorId(Long verificadorId) {
        this.verificadorId = verificadorId;
    }

    public LocalDateTime getDataVerificacao() {
        return dataVerificacao;
    }

    public void setDataVerificacao(LocalDateTime dataVerificacao) {
        this.dataVerificacao = dataVerificacao;
    }
}
