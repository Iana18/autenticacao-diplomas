package com.meuprojetotcc.autenticacao_diplomas.model.user;
public enum Role {
    ADMIN,                   // Superusuário do sistema
    MINISTERIO,              // Ministério da Educação
    SECRETARIA_MINISTERIO,   // Secretaria subordinada ao ministério
    INSTITUICAO,             // Instituição de ensino
    EMISSOR,                 // Usuário emissor dentro da instituição
    OUTRO,                   // Outro usuário da instituição
    ESTUDANTE,               // Estudante cadastrado
    USER                     //usuarios publicos
}