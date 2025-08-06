package com.meuprojetotcc.autenticacao_diplomas.model.notificacao;

public class NotificacaoUpdateDTO {
    private String mensagem;
    private Boolean lida;

    public String getMensagem() {
        return mensagem;
    }
    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
    public Boolean getLida() {
        return lida;
    }
    public void setLida(Boolean lida) {
        this.lida = lida;
    }
}