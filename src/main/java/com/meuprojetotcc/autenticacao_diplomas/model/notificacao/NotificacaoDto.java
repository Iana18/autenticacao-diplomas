package com.meuprojetotcc.autenticacao_diplomas.model.notificacao;

import com.meuprojetotcc.autenticacao_diplomas.model.user.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class NotificacaoDto {
    private Long id;
    private Long usuarioId;
    private String mensagem;
    private LocalDateTime dataCriacao;
    private boolean lida;
    private Long destinatarioUserId;
    private Long UsuarioRemetenteId;

    private Long estudanteId;



    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public String getMensagem() { return mensagem; }
    public void setMensagem(String mensagem) { this.mensagem = mensagem; }

    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }

    public boolean isLida() { return lida; }
    public void setLida(boolean lida) { this.lida = lida; }

    public Long getDestinatarioUserId() {
        return destinatarioUserId;
    }

    public void setDestinatarioUserId(Long destinatarioUserId) {
        this.destinatarioUserId = destinatarioUserId;
    }

    public Long getEstudanteId() {
        return estudanteId;
    }

    public void setEstudanteId(Long estudanteId) {
        this.estudanteId = estudanteId;
    }

    public Long getUsuarioRemetenteId() {
        return UsuarioRemetenteId;
    }

    public void setUsuarioRemetenteId(Long usuarioRemetenteId) {
        UsuarioRemetenteId = usuarioRemetenteId;
    }
}
