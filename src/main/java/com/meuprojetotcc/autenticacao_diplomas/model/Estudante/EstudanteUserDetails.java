/*package com.meuprojetotcc.autenticacao_diplomas.model.Estudante;

import com.meuprojetotcc.autenticacao_diplomas.model.Estudante.Estudante;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class EstudanteUserDetails implements UserDetails {

    private final Estudante estudante;

    public EstudanteUserDetails(Estudante estudante) {
        this.estudante = estudante;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_ESTUDANTE"));
    }

    @Override
    public String getPassword() {
        // A senha do estudante — aqui, você deve usar um campo correto de senha
        // Se o número de matrícula for usado como senha, retorne isso (não recomendado)
        // Ideal é ter um campo específico para senha
        return estudante.getSenha();
    }

    @Override
    public String getUsername() {
        // Para o estudante, username será o número de matrícula (conforme login)
        return estudante.getNumeroMatricula();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Ajuste conforme sua regra de negócio
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Ajuste conforme sua regra de negócio
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Ajuste conforme sua regra de negócio
    }

    @Override
    public boolean isEnabled() {
        return true; // Ajuste conforme sua regra de negócio
    }
}

*/