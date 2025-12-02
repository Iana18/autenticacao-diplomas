package com.meuprojetotcc.autenticacao_diplomas.model.notificacao;

import com.meuprojetotcc.autenticacao_diplomas.model.Estudante.Estudante;
import com.meuprojetotcc.autenticacao_diplomas.model.user.User;
import jakarta.persistence.*;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
public class Notificacao {

        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne(optional = false)
        @JoinColumn(name = "user_id")
        private User usuario;

        @ManyToOne(optional = false)
        @JoinColumn(name = "estudante_id")
        private Estudante estudante;

        private String mensagem;

        private LocalDateTime dataCriacao;

        private boolean lida;

        public Long getId() {
                return id;
        }

        public User getUsuario() {
                return usuario;
        }

        public void setUsuario(User usuario) {
                this.usuario = usuario;
        }

        public String getMensagem() {
                return mensagem;
        }

        public void setMensagem(String mensagem) {
                this.mensagem = mensagem;
        }

        public LocalDateTime getDataCriacao() {
                return dataCriacao;
        }

        public void setDataCriacao(LocalDateTime dataCriacao) {
                this.dataCriacao = dataCriacao;
        }

        public boolean isLida() {
                return lida;
        }

        public void setLida(boolean lida) {
                this.lida = lida;
        }

        public void setId(Long id) {
                this.id = id;
        }
}

