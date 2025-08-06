package com.meuprojetotcc.autenticacao_diplomas.model.certificado;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CertificadoResponseDTO {
    private Long id;
    private String estudanteNome;
    private String cursoNome;
    private String instituicaoNome;
    private String criadoPorNome;
    private LocalDateTime dataEmissao;
    private LocalDateTime dataRevogacao;
    private String status;
    private String hashBlockchain;
    private String enderecoTransacao;
}