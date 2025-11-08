package com.meuprojetotcc.autenticacao_diplomas.model.Estudante;


public class AtivacaoRequestDto {
        private String senha;
        private String senhaAtual;
        private String senhaNova;

        public String getSenhaNova() {
                return senhaNova;
        }

        public void setSenhaNova(String senhaNova) {
                this.senhaNova = senhaNova;
        }

        public String getSenhaAtual() {
                return senhaAtual;
        }

        public void setSenhaAtual(String senhaAtual) {
                this.senhaAtual = senhaAtual;
        }

        public String getSenha() { return senha; }
        public void setSenha(String senha) { this.senha = senha; }
}