package com.meuprojetotcc.autenticacao_diplomas.model.verificacao;

import java.time.LocalDateTime;

public class VerificacaoDto {

    private Long id;
    private Long verificadorId;
    private Long diplomaId;
    private LocalDateTime dataVerificacao;

    public VerificacaoDto() {}

    public VerificacaoDto(Long id, Long verificadorId, Long diplomaId, LocalDateTime dataVerificacao) {
        this.id = id;
        this.verificadorId = verificadorId;
        this.diplomaId = diplomaId;
        this.dataVerificacao = dataVerificacao;
    }

    // Getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getVerificadorId() { return verificadorId; }
    public void setVerificadorId(Long verificadorId) { this.verificadorId = verificadorId; }

    public Long getDiplomaId() { return diplomaId; }
    public void setDiplomaId(Long diplomaId) { this.diplomaId = diplomaId; }

    public LocalDateTime getDataVerificacao() { return dataVerificacao; }
    public void setDataVerificacao(LocalDateTime dataVerificacao) { this.dataVerificacao = dataVerificacao; }
}
