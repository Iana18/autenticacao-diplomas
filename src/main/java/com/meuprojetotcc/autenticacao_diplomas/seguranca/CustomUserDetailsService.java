package com.meuprojetotcc.autenticacao_diplomas.seguranca;

import com.meuprojetotcc.autenticacao_diplomas.repository.EstudanteRepository;
import com.meuprojetotcc.autenticacao_diplomas.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EstudanteRepository estudanteRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Tenta achar como User (login por email)
        var user = userRepository.findByEmail(username);
        if (user.isPresent()) return user.get();

        // Se não achar, tenta como Estudante (login por matrícula)
        var estudante = estudanteRepository.findByNumeroMatricula(username);
        if (estudante.isPresent()) return estudante.get();

        throw new UsernameNotFoundException("Usuário ou Estudante não encontrado");
    }

}