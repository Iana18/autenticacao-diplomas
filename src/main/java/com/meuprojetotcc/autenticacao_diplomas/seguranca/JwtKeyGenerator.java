package com.meuprojetotcc.autenticacao_diplomas.seguranca;

import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;

public class JwtKeyGenerator {
    public static void main(String[] args) {
        var key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // gera uma chave segura
        String base64Key = Encoders.BASE64.encode(key.getEncoded());
        System.out.println(base64Key); // copie essa saída
    }
}