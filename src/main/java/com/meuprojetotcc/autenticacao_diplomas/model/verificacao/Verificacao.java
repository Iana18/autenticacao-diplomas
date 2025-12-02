package com.meuprojetotcc.autenticacao_diplomas.model.verificacao;

import com.meuprojetotcc.autenticacao_diplomas.model.certificado.Certificado;
import com.meuprojetotcc.autenticacao_diplomas.model.diploma.Diploma;
import com.meuprojetotcc.autenticacao_diplomas.model.user.User;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "verificacoes")
public class Verificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

  /*  @ManyToOne(optional = false)
    @JoinColumn(name = "certificado_id")
    private Certificado certificado;
*/
    @ManyToOne(optional = false)
    @JoinColumn(name = "verificador_id")
    private User verificador;
    @ManyToOne
    @JoinColumn(name = "diploma_id")
    private Diploma diploma;
    @Column(nullable = false)
    private LocalDateTime dataVerificacao;

    // Construtor padrão
    public Verificacao() {}

    // Construtor com parâmetros
    public Verificacao(Certificado certificado, User verificador, LocalDateTime dataVerificacao) {
        //this.certificado = certificado;
        this.verificador = verificador;
        this.dataVerificacao = dataVerificacao;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

   /* public Certificado getCertificado() {
        return certificado;
    }

    public void setCertificado(Certificado certificado) {
        this.certificado = certificado;
    }
*/
    public User getVerificador() {
        return verificador;
    }

    public void setVerificador(User verificador) {
        this.verificador = verificador;
    }

    public Diploma getDiploma() {
        return diploma;
    }

    public void setDiploma(Diploma diploma) {
        this.diploma = diploma;
    }

    public LocalDateTime getDataVerificacao() {
        return dataVerificacao;
    }

    public void setDataVerificacao(LocalDateTime dataVerificacao) {
        this.dataVerificacao = dataVerificacao;
    }
}
