package com.meuprojetotcc.autenticacao_diplomas.model.notificacao;

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

}
