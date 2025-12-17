package com.meuprojetotcc.autenticacao_diplomas.model;

import com.meuprojetotcc.autenticacao_diplomas.model.Estudante.Estudante;
import com.meuprojetotcc.autenticacao_diplomas.model.Curso.Curso;
import com.meuprojetotcc.autenticacao_diplomas.model.Instituicao.Instituicao;
import com.meuprojetotcc.autenticacao_diplomas.model.diploma.Diploma;
import com.meuprojetotcc.autenticacao_diplomas.model.user.User;
import com.meuprojetotcc.autenticacao_diplomas.model.certificado.Status;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@MappedSuperclass
public abstract class DocumentoAcademico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "estudante_id")
    private Estudante estudante;

    @ManyToOne(optional = false)
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @ManyToOne(optional = false)
    @JoinColumn(name = "instituicao_id")
    private Instituicao instituicao;

    @ManyToOne(optional = false)
    @JoinColumn(name = "criado_por_id")
    private User criadoPor;

    @Column(nullable = false)
    private LocalDateTime dataEmissao = LocalDateTime.now();

    private LocalDateTime dataRevogacao;  // Null se ativo

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.ATIVO;

    private String hashBlockchain;
    private String enderecoTransacao;

    @Lob
    private byte[] assinaturaInstituicao;
    @Lob//
    private byte[] carimboInstituicao;    // carimbo digital ou CID

    // Getters e setters

    // ===== CONSTRUTOR =====
    public DocumentoAcademico() {}

    // ===== MÉTODOS =====

    /**
     * Revoga o documento.
     */
    public void revogar() {
        this.status = Status.REVOGADO;
        this.dataRevogacao = LocalDateTime.now();
    }

    /**
     * Reemite o documento (reativa).
     */
    public void reemitir() {
        this.status = Status.ATIVO;
        this.dataRevogacao = null;
        this.dataEmissao = LocalDateTime.now();
    }

    /**
     * Gera hash do documento baseado em campos importantes.
     * Pode ser usado para registrar na blockchain.
     */
    public void gerarHashBlockchain() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String dados = this.getEstudante().getNumeroMatricula()
                    + this.getEstudante().getNomeCompleto()
                    + this.getCurso().getNome()
                    + this.getInstituicao().getNome()
                    + this.getDataEmissao().toString()
                    + (this.getCarimboInstituicao() != null ? new String(this.getCarimboInstituicao()) : "")
                    + (this.getAssinaturaInstituicao() != null ? new String(this.getAssinaturaInstituicao()) : "");

            // Campos do Diploma
            if (this instanceof Diploma) {
                Diploma d = (Diploma) this;
                dados += d.getTipoDiploma()
                        + d.getNotaFinal()
                        + d.getCargaHoraria()
                        + d.getGrauAcademico()
                        + d.getNumeroDiploma()
                        + (d.getDataConclusao() != null ? d.getDataConclusao().toString() : "");
            }

            byte[] hash = digest.digest(dados.getBytes());
            this.setHashBlockchain(bytesToHex(hash));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao gerar hash: " + e.getMessage());
        }
    }



    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    // ===== GETTERS E SETTERS =====

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Estudante getEstudante() {
        return estudante;
    }

    public void setEstudante(Estudante estudante) {
        this.estudante = estudante;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Instituicao getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(Instituicao instituicao) {
        this.instituicao = instituicao;
    }

    public User getCriadoPor() {
        return criadoPor;
    }

    public void setCriadoPor(User criadoPor) {
        this.criadoPor = criadoPor;
    }

    public LocalDateTime getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(LocalDateTime dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public LocalDateTime getDataRevogacao() {
        return dataRevogacao;
    }

    public void setDataRevogacao(LocalDateTime dataRevogacao) {
        this.dataRevogacao = dataRevogacao;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

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

    public byte[] getAssinaturaInstituicao() { return assinaturaInstituicao; }
    public void setAssinaturaInstituicao(byte[] assinaturaInstituicao) { this.assinaturaInstituicao = assinaturaInstituicao; }

    public byte[] getCarimboInstituicao() { return carimboInstituicao; }
    public void setCarimboInstituicao(byte[] carimboInstituicao) { this.carimboInstituicao = carimboInstituicao; }

}
