package com.meuprojetotcc.autenticacao_diplomas.controller;

import com.meuprojetotcc.autenticacao_diplomas.model.certificado.Certificado;
import com.meuprojetotcc.autenticacao_diplomas.model.certificado.CertificadoDTO;
import com.meuprojetotcc.autenticacao_diplomas.model.user.User;
import com.meuprojetotcc.autenticacao_diplomas.service.CertificadoService;
import com.meuprojetotcc.autenticacao_diplomas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UserController {

    private final UserService userService;
    @Autowired
    private CertificadoService certificadoService;

    public UserController(UserService userService) {

        this.userService= userService;

    }

    @PostMapping
    public ResponseEntity<User> criar(@RequestBody User user) {
        User salvo = userService.salvar(user);
        return ResponseEntity.ok(salvo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> buscarPorId(@PathVariable Long id) {
        return userService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/certificados/registrar")
    public ResponseEntity<Certificado> registrar(@RequestBody CertificadoDTO dto) {
        try {
            Certificado novoCertificado = certificadoService.registrarCertificado(dto);
            return ResponseEntity.ok(novoCertificado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping
    public List<User> listarTodos() {
        return userService.listarTodos();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        userService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
