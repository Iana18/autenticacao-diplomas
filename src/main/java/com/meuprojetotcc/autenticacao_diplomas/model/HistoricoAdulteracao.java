package com.meuprojetotcc.autenticacao_diplomas.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "historico_adulteracao")
public class HistoricoAdulteracao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long diplomaId;
    private String campo;

    @Lob
    private String valorAntigo;

    @Lob
    private String valorNovo;

    private LocalDateTime detectadoEm = LocalDateTime.now();


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDiplomaId() {
        return diplomaId;
    }

    public void setDiplomaId(Long diplomaId) {
        this.diplomaId = diplomaId;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public String getValorAntigo() {
        return valorAntigo;
    }

    public void setValorAntigo(String valorAntigo) {
        this.valorAntigo = valorAntigo;
    }

    public String getValorNovo() {
        return valorNovo;
    }

    public void setValorNovo(String valorNovo) {
        this.valorNovo = valorNovo;
    }

    public LocalDateTime getDetectadoEm() {
        return detectadoEm;
    }

    public void setDetectadoEm(LocalDateTime detectadoEm) {
        this.detectadoEm = detectadoEm;
    }
}