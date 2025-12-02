package com.meuprojetotcc.autenticacao_diplomas.model.validacao;

import com.meuprojetotcc.autenticacao_diplomas.model.diploma.Diploma;
import com.meuprojetotcc.autenticacao_diplomas.model.user.User;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "validacao")
public class Validacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "diploma_id")
    private Diploma diploma;

    @ManyToOne(optional = false)
    @JoinColumn(name = "verificador_id")
    private User verificador;

    @Column(nullable = false)
    private LocalDateTime dataVerificacao;

    public Validacao() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Diploma getDiploma() { return diploma; }
    public void setDiploma(Diploma diploma) { this.diploma = diploma; }

    public User getVerificador() { return verificador; }
    public void setVerificador(User verificador) { this.verificador = verificador; }

    public LocalDateTime getDataVerificacao() { return dataVerificacao; }
    public void setDataVerificacao(LocalDateTime dataVerificacao) { this.dataVerificacao = dataVerificacao; }
}
