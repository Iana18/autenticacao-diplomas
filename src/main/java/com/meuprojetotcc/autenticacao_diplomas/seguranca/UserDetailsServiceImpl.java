package com.meuprojetotcc.autenticacao_diplomas.seguranca;

import com.meuprojetotcc.autenticacao_diplomas.model.Estudante.EstudanteUserDetails;
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Tenta buscar usuário comum pelo email
        return userRepository.findByEmail(username)
                .map(user -> (UserDetails) user)  // User já implementa UserDetails
                .orElseGet(() -> {
                    // Se não achou, tenta buscar estudante pelo número de matrícula
                    return estudanteRepository.findByNumeroMatricula(username)
                            .map(EstudanteUserDetails::new) // Adaptador para UserDetails
                            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));
                });
    }
}
