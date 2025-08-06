package com.meuprojetotcc.autenticacao_diplomas.seguranca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)  // para usar @PreAuthorize e outros
public class SecurityConfig {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // desabilita CSRF para APIs REST; se usar forms, reavalie isso
                .csrf(csrf -> csrf.disable())

                // Define quais endpoints são públicos e quais precisam de autenticação
                .authorizeHttpRequests(auth -> auth
                        //.requestMatchers("/api/auth/login", "/api/auth/register").permitAll()  // endpoints públicos de autenticação
                        // exemplo de proteção por roles
                       // .requestMatchers("/admin/**").hasRole("ADMIN")
                       // .requestMatchers("/estudante/**").hasAnyRole("EMISSOR", "ADMIN")
                       // .requestMatchers(HttpMethod.GET, "/certificado/**").hasAnyRole("ESTUDANTE", "EMISSOR", "ADMIN")
                      //  .requestMatchers("/certificado/meus").hasRole("ESTUDANTE")
                      //  .anyRequest().authenticated()
                        .anyRequest().permitAll()
                )

                // Configura a aplicação para não criar sessão (stateless) porque usará JWT
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // Adiciona o filtro JWT antes do filtro padrão de autenticação
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // bcrypt para hashing de senha
    }
}
