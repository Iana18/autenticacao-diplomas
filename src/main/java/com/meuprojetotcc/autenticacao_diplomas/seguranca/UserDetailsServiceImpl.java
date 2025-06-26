package com.meuprojetotcc.autenticacao_diplomas.seguranca;

import com.meuprojetotcc.autenticacao_diplomas.model.user.User;
import com.meuprojetotcc.autenticacao_diplomas.model.Estudante.Estudante;
import com.meuprojetotcc.autenticacao_diplomas.repository.UserRepository;
import com.meuprojetotcc.autenticacao_diplomas.repository.EstudanteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EstudanteRepository estudanteRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Tenta buscar usuário normal (admin, emissor)
        return userRepository.findByEmail(email)
                .map(user -> {
                    var authority = new SimpleGrantedAuthority("ROLE_" + user.getRole().name());
                    return new org.springframework.security.core.userdetails.User(
                            user.getEmail(),
                            user.getSenha(),
                            Collections.singletonList(authority)
                    );
                })
                // Se não achou, tenta achar Estudante
                .or(() -> estudanteRepository.findByEmail(email)
                        .map(estudante -> new org.springframework.security.core.userdetails.User(
                                estudante.getEmail(),
                                estudante.getNumeroMatricula(), // se quiser pode mudar essa senha para a correta
                                Collections.singletonList(new SimpleGrantedAuthority("ROLE_ESTUDANTE"))
                        )))
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));
    }
}
