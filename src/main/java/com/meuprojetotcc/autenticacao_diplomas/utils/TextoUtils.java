package com.meuprojetotcc.autenticacao_diplomas.utils;

import java.text.Normalizer;

public class TextoUtils {

    public static String normalizar(String texto) {
        if (texto == null) return null;

        // Remove acentos
        String semAcento = Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");

        // Transforma em minúsculas e remove espaços extras
        return semAcento.trim().toLowerCase();
    }
}
