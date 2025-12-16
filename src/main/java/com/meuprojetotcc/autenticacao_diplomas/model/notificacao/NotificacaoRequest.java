package com.meuprojetotcc.autenticacao_diplomas.model.notificacao;

import java.util.List;

public class NotificacaoRequest {

    private Long remetenteId;             // Quem está enviando
    private String mensagem;              // Conteúdo da notificação
    private List<Long> usuariosIds;       // IDs de usuários destinatários (apenas Admin)
    private List<Long> estudantesIds; // IDs dos destinatários (usuarios ou estudantes)


    public Long getRemetenteId() {
        return remetenteId;
    }

    public void setRemetenteId(Long remetenteId) {
        this.remetenteId = remetenteId;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }



    public List<Long> getEstudantesIds() {
        return estudantesIds;
    }

    public void setEstudantesIds(List<Long> estudantesIds) {
        this.estudantesIds = estudantesIds;
    }

    public List<Long> getUsuariosIds() {
        return usuariosIds;
    }

    public void setUsuariosIds(List<Long> usuariosIds) {
        this.usuariosIds = usuariosIds;
    }
}
