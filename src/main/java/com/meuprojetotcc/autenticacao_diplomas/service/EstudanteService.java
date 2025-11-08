package com.meuprojetotcc.autenticacao_diplomas.service;

import com.meuprojetotcc.autenticacao_diplomas.model.Estudante.AtivacaoRequestDto;
import com.meuprojetotcc.autenticacao_diplomas.model.Estudante.Estudante;
import com.meuprojetotcc.autenticacao_diplomas.model.Estudante.EstudanteDto;
import com.meuprojetotcc.autenticacao_diplomas.repository.CursoRepository;
import com.meuprojetotcc.autenticacao_diplomas.repository.EstudanteRepository;
import com.meuprojetotcc.autenticacao_diplomas.repository.InstituicaoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EstudanteService {

    private final EstudanteRepository estudanteRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;
    private final CursoRepository cursoRepository;
    private final InstituicaoRepository instituicaoRepository;

    // Injeta a base URL do properties
    @Value("${app.base-url}")
    private String baseUrl;

    public EstudanteService(EstudanteRepository estudanteRepository,
                            PasswordEncoder passwordEncoder,
                            JavaMailSender mailSender, CursoRepository cursoRepository,
    InstituicaoRepository instituicaoRepository) {
        this.estudanteRepository = estudanteRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
        this.cursoRepository = cursoRepository;
        this.instituicaoRepository= instituicaoRepository;
    }

    // ================= Cadastro pelo estudante público =================
    public Estudante registrarEstudante(String nomeCompleto, String numeroMatricula, String senha) {
        Estudante estudanteExistente = estudanteRepository
                .findByNomeCompletoAndNumeroMatricula(nomeCompleto, numeroMatricula)
                .orElseThrow(() -> new RuntimeException("Dados não conferem com cadastro do emissor"));

        if (estudanteExistente.getSenha() != null && !estudanteExistente.getSenha().isEmpty()) {
            throw new RuntimeException("Estudante já possui conta cadastrada");
        }

        estudanteExistente.setSenha(passwordEncoder.encode(senha));
        estudanteExistente.setAtivo(true); // Ativado diretamente pelo estudante
        estudanteExistente.setTokenAtivacao(null);

        return estudanteRepository.save(estudanteExistente);
    }

    // ================= Cadastro pelo emissor =================
    public Estudante cadastrarEstudantePeloEmissor(EstudanteDto dto) {
        var curso = cursoRepository.findByNomeIgnoreCase(dto.getNomeCurso())
                .orElseThrow(() -> new RuntimeException("Curso não encontrado: " + dto.getNomeCurso()));

        var instituicao = instituicaoRepository.findByNomeIgnoreCase(dto.getNomeInstituicao())
                .orElseThrow(() -> new RuntimeException("Instituição não encontrada: " + dto.getNomeInstituicao()));

        Estudante estudante = new Estudante();
        estudante.setNomeCompleto(dto.getNomeCompleto());
        estudante.setEmail(dto.getEmail());
        estudante.setNumeroMatricula(dto.getNumeroMatricula());
        estudante.setDataNascimento(dto.getDataNascimento());
        estudante.setGenero(dto.getGenero());
        estudante.setCurso(curso);
        estudante.setInstituicao(instituicao);
        estudante.setAtivo(false);
        estudante.setSenha(null); // senha será definida após ativação
        estudante.setTokenAtivacao(UUID.randomUUID().toString());

        Estudante salvo = estudanteRepository.save(estudante);
        enviarEmailAtivacao(salvo);
        return salvo;
    }


    // Envio de e-mail com link de ativação
    private void enviarEmailAtivacao(Estudante estudante) {
        String token = estudante.getTokenAtivacao();
        String link = String.format("http://127.0.0.1:5501/ativacoa.html?token=%s", token);

        String mensagem = "Olá " + estudante.getNomeCompleto() + ",\n\n"
                + "Uma conta foi criada para você. Para ativar e definir sua senha, clique no link:\n"
                + link;

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(estudante.getEmail());
        mail.setSubject("Ativação de conta - Sistema de Diplomas");
        mail.setText(mensagem);

        mailSender.send(mail);
    }

    // ================= Ativar conta via token =================
    public boolean ativarConta(String token, String senha) {
        Optional<Estudante> opt = estudanteRepository.findByTokenAtivacao(token);
        if (opt.isEmpty()) return false;

        Estudante estudante = opt.get();
        if (Boolean.TRUE.equals(estudante.isAtivo())) return false;

        estudante.setSenha(passwordEncoder.encode(senha));
        estudante.setAtivo(true);
        estudante.setTokenAtivacao(null);

        estudanteRepository.save(estudante);
        return true;
    }

    // ================= CRUD básico =================
    public List<Estudante> buscarPorNome(String nome) {
        return estudanteRepository.findByNomeCompletoContainingIgnoreCase(nome);
    }

    public Optional<Estudante> buscarPorId(Long id) {
        return estudanteRepository.findById(id);
    }

    public boolean existsByNumeroMatricula(String numeroMatricula) {
        return estudanteRepository.existsByNumeroMatricula(numeroMatricula);
    }

    public boolean existsByEmail(String email) {
        return estudanteRepository.existsByEmail(email);
    }

    public List<Estudante> listarTodos() {
        try {
            List<Estudante> estudantes= estudanteRepository.findAll();
            return  estudantes;
        }catch (Exception ex){
            System.out.println(ex);
        }
       return  new ArrayList<>();
    }

    public Estudante atualizarEstudante(Long id, Estudante dadosAtualizados) {
        return estudanteRepository.findById(id).map(estudante -> {
            estudante.setNomeCompleto(dadosAtualizados.getNomeCompleto());
            estudante.setEmail(dadosAtualizados.getEmail());
            estudante.setNumeroMatricula(dadosAtualizados.getNumeroMatricula());
            estudante.setDataNascimento(dadosAtualizados.getDataNascimento());
            estudante.setGenero(dadosAtualizados.getGenero());

            if (dadosAtualizados.getSenha() != null && !dadosAtualizados.getSenha().isEmpty()) {
                estudante.setSenha(passwordEncoder.encode(dadosAtualizados.getSenha()));
            }

            return estudanteRepository.save(estudante);
        }).orElseThrow(() -> new RuntimeException("Estudante não encontrado"));
    }

    public void deletarEstudante(Long id) {
        estudanteRepository.deleteById(id);
    }

    // ================= Alterar senha =================
    public void alterarSenha(Long estudanteId, AtivacaoRequestDto dto) {
        Estudante estudante = estudanteRepository.findById(estudanteId)
                .orElseThrow(() -> new RuntimeException("Estudante não encontrado"));

        if (!passwordEncoder.matches(dto.getSenhaAtual(), estudante.getSenha())) {
            throw new RuntimeException("Senha atual incorreta");
        }

        estudante.setSenha(passwordEncoder.encode(dto.getSenhaNova()));
        estudanteRepository.save(estudante);
    }

    public Estudante buscarPorNumeroMatricula(String numeroMatricula) {
        return estudanteRepository.findByNumeroMatricula(numeroMatricula)
                .orElseThrow(() -> new RuntimeException("Estudante não encontrado com a matrícula: " + numeroMatricula));
    }

}
