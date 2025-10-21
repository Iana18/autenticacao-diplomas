package com.meuprojetotcc.autenticacao_diplomas.model.diploma;



import java.time.LocalDate;
import java.time.LocalDateTime;

public class DiplomaResponseDTO {

        private Long id;
        private String estudanteNome;
        private String cursoNome;
        private String instituicaoNome;
        private String criadoPorNome;

        private String tipoDiploma;
        private Double notaFinal;
        private int cargaHoraria;
        private String numeroDiploma;
        private String registroMinisterio;
        private String grauAcademico;
        private LocalDate dataConclusao;

        private LocalDateTime dataEmissao;
        private LocalDateTime dataRevogacao;
        private String status;
        private String hashBlockchain;
        private String enderecoTransacao;

        // ===== GETTERS E SETTERS =====
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getEstudanteNome() { return estudanteNome; }
        public void setEstudanteNome(String estudanteNome) { this.estudanteNome = estudanteNome; }

        public String getCursoNome() { return cursoNome; }
        public void setCursoNome(String cursoNome) { this.cursoNome = cursoNome; }

        public String getInstituicaoNome() { return instituicaoNome; }
        public void setInstituicaoNome(String instituicaoNome) { this.instituicaoNome = instituicaoNome; }

        public String getCriadoPorNome() { return criadoPorNome; }
        public void setCriadoPorNome(String criadoPorNome) { this.criadoPorNome = criadoPorNome; }

        public String getTipoDiploma() { return tipoDiploma; }
        public void setTipoDiploma(String tipoDiploma) { this.tipoDiploma = tipoDiploma; }

        public Double getNotaFinal() { return notaFinal; }
        public void setNotaFinal(Double notaFinal) { this.notaFinal = notaFinal; }

        public int getCargaHoraria() { return cargaHoraria; }
        public void setCargaHoraria(int cargaHoraria) { this.cargaHoraria = cargaHoraria; }

        public String getNumeroDiploma() { return numeroDiploma; }
        public void setNumeroDiploma(String numeroDiploma) { this.numeroDiploma = numeroDiploma; }

        public String getRegistroMinisterio() { return registroMinisterio; }
        public void setRegistroMinisterio(String registroMinisterio) { this.registroMinisterio = registroMinisterio; }

        public String getGrauAcademico() { return grauAcademico; }
        public void setGrauAcademico(String grauAcademico) { this.grauAcademico = grauAcademico; }

        public LocalDate getDataConclusao() { return dataConclusao; }
        public void setDataConclusao(LocalDate dataConclusao) { this.dataConclusao = dataConclusao; }

        public LocalDateTime getDataEmissao() { return dataEmissao; }
        public void setDataEmissao(LocalDateTime dataEmissao) { this.dataEmissao = dataEmissao; }

        public LocalDateTime getDataRevogacao() { return dataRevogacao; }
        public void setDataRevogacao(LocalDateTime dataRevogacao) { this.dataRevogacao = dataRevogacao; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        public String getHashBlockchain() { return hashBlockchain; }
        public void setHashBlockchain(String hashBlockchain) { this.hashBlockchain = hashBlockchain; }

        public String getEnderecoTransacao() { return enderecoTransacao; }
        public void setEnderecoTransacao(String enderecoTransacao) { this.enderecoTransacao = enderecoTransacao; }


}

