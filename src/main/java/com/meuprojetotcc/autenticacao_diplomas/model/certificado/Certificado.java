package com.meuprojetotcc.autenticacao_diplomas.model.certificado;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.meuprojetotcc.autenticacao_diplomas.model.Curso.Curso;
import com.meuprojetotcc.autenticacao_diplomas.model.Estudante.Estudante;
import com.meuprojetotcc.autenticacao_diplomas.model.Instituicao.Instituicao;
import com.meuprojetotcc.autenticacao_diplomas.model.user.User;
import com.meuprojetotcc.autenticacao_diplomas.model.DocumentoAcademico;
import jakarta.persistence.Entity;

@Entity
public class Certificado extends DocumentoAcademico {

    // Campos específicos de certificado, se houver
    private String tipoParticipacao; // workshop, curso, palestra

    private int cargaHoraria;



    @Override
    @JsonBackReference
    public Estudante getEstudante() {
        return super.getEstudante();
    }

    public Certificado() {
        // Construtor padrão
    }

    public Certificado(Estudante estudante, Curso curso, Instituicao instituicao, User criadoPor,
                       String tipoParticipacao, int cargaHoraria) {
        this.setEstudante(estudante);
        this.setCurso(curso);
        this.setInstituicao(instituicao);
        this.setCriadoPor(criadoPor);
        this.setStatus(com.meuprojetotcc.autenticacao_diplomas.model.certificado.Status.ATIVO);
        this.setDataEmissao(java.time.LocalDateTime.now());
        this.tipoParticipacao = tipoParticipacao;
        this.cargaHoraria = cargaHoraria; // Agora recebe do parâmetro
    }


    // ===== GETTERS E SETTERS =====

    public String getTipoParticipacao() {
        return tipoParticipacao;
    }

    public void setTipoParticipacao(String tipoParticipacao) {
        this.tipoParticipacao = tipoParticipacao;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }
    // Métodos de revogação e reemissão já vêm da classe pai DocumentoAcademico
}
