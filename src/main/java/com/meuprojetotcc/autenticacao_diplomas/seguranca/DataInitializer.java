package com.meuprojetotcc.autenticacao_diplomas.seguranca;

import com.meuprojetotcc.autenticacao_diplomas.model.user.User;
import com.meuprojetotcc.autenticacao_diplomas.model.user.Role;
import com.meuprojetotcc.autenticacao_diplomas.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByEmail("admin@exemplo.com").isEmpty()) {
                User admin = new User();
                admin.setNome("Admin");
                admin.setEmail("admin@exemplo.com");
                admin.setSenha(passwordEncoder.encode("admin123"));  // senha criptografada
                admin.setRole(Role.ADMIN);
                userRepository.save(admin);
                System.out.println("Admin criado: admin@exemplo.com / senha: admin123");

            }else {
                System.out.println("Admin Já existe.");
            }
        };
    }
}
