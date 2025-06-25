package com.meuprojetotcc.autenticacao_diplomas.seguranca;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // Chave secreta usada para assinar e verificar o token JWT
    private final Key chaveSecreta = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Tempo de validade do token em milissegundos (1 dia)
    private final long validadeTokenMs = 24 * 60 * 60 * 1000;

    // Gera um token JWT para o usuário (por exemplo, ao fazer login com sucesso)
    public String generateToken(String username) {
        Date agora = new Date();
        Date validade = new Date(agora.getTime() + validadeTokenMs);

        return Jwts.builder()
                .setSubject(username)           // Define o "dono" do token
                .setIssuedAt(agora)            // Data de emissão
                .setExpiration(validade)       // Data de expiração
                .signWith(chaveSecreta)        // Assina com a chave secreta
                .compact();
    }

    // Extrai o nome do usuário (subject) de um token JWT
    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(chaveSecreta)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Verifica se o token JWT é válido (estrutura, assinatura, expiração, etc)
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(chaveSecreta)
                    .build()
                    .parseClaimsJws(token);  // Se não lançar exceção, é válido
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false; // Token inválido ou mal formado
        }
    }
}
