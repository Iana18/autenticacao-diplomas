package com.meuprojetotcc.autenticacao_diplomas.model.user;

public class UserDto {
 private String nome;
 private String apelido;
 private String email;
 private String senha;
 private Role role;

 public UserDto() {}

 public UserDto(String nome, String apelido, String email, String senha, Role role) {
  this.nome = nome;
  this.apelido = apelido;
  this.email = email;
  this.senha = senha;
  this.role = role;
 }

 // Getters e Setters
 public String getNome() {
  return nome;
 }

 public void setNome(String nome) {
  this.nome = nome;
 }

 public String getApelido() {
  return apelido;
 }

 public void setApelido(String apelido) {
  this.apelido = apelido;
 }

 public String getEmail() {
  return email;
 }

 public void setEmail(String email) {
  this.email = email;
 }

 public String getSenha() {
  return senha;
 }

 public void setSenha(String senha) {
  this.senha = senha;
 }

 public Role getRole() {
  return role;
 }

 public void setRole(Role role) {
  this.role = role;
 }
}
