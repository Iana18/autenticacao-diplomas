package com.meuprojetotcc.autenticacao_diplomas.model.certificado;


import com.meuprojetotcc.autenticacao_diplomas.model.Estudante.Estudante;

import java.time.LocalDateTime;

public class CertificadoDTO {

    private Long id;
    private Long estudanteId;
    private Long cursoId;
    private Long instituicaoId;
    private Long criadoPorId;

    private LocalDateTime dataEmissao;
    private LocalDateTime dataRevogacao;
    private Status status;
    private String enderecoTransacao;
    private String hashBlockchain;
        // === GETTERS E SETTERS ===

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }



        /*public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

*/

        public String getHashBlockchain() {
            return hashBlockchain;
        }

        public void setHashBlockchain(String hashBlockchain) {
            this.hashBlockchain = hashBlockchain;
        }

        public String getEnderecoTransacao() {
            return enderecoTransacao;
        }

        public void setEnderecoTransacao(String enderecoTransacao) {
            this.enderecoTransacao = enderecoTransacao;
        }




    public Long getEstudanteId() {
        return estudanteId;
    }

    public void setEstudanteId(Long estudanteId) {
        this.estudanteId = estudanteId;
    }

    public Long getCursoId() {
            return cursoId;
        }

        public void setCursoId(Long cursoId) {
            this.cursoId = cursoId;
        }

        public Long getInstituicaoId() {
            return instituicaoId;
        }

        public void setInstituicaoId(Long instituicaoId) {
            this.instituicaoId = instituicaoId;
        }

        public Long getCriadoPorId() {
            return criadoPorId;
        }

        public void setCriadoPorId(Long criadoPorId) {
            this.criadoPorId = criadoPorId;
        }

    public Long getUserId() {

            return criadoPorId;

    }
}

