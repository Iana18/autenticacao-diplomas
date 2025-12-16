package com.meuprojetotcc.autenticacao_diplomas.model.notificacao;

import java.util.List;

    public class CriarNotificacaoDTO {

        private Long usuarioId; // remetente
        private String mensagem;
        private List<Long> estudantesIds; // destinatários estudantes
        private List<Long> usuariosIds;   // destinatários usuários (emissor/admin)

        // Getters e Setters
        public Long getUsuarioId() { return usuarioId; }
        public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

        public String getMensagem() { return mensagem; }
        public void setMensagem(String mensagem) { this.mensagem = mensagem; }

        public List<Long> getEstudantesIds() { return estudantesIds; }
        public void setEstudantesIds(List<Long> estudantesIds) { this.estudantesIds = estudantesIds; }

        public List<Long> getUsuariosIds() { return usuariosIds; }
        public void setUsuariosIds(List<Long> usuariosIds) { this.usuariosIds = usuariosIds; }
    }

