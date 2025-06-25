package com.meuprojetotcc.autenticacao_diplomas.implemento;

import com.meuprojetotcc.autenticacao_diplomas.model.certificado.CertificadoDTO;
import com.meuprojetotcc.autenticacao_diplomas.model.Curso.Curso;
import com.meuprojetotcc.autenticacao_diplomas.model.Instituicao.Instituicao;
import com.meuprojetotcc.autenticacao_diplomas.model.certificado.Certificado;
import com.meuprojetotcc.autenticacao_diplomas.model.Estudante.Estudante;
import com.meuprojetotcc.autenticacao_diplomas.model.user.User;
import com.meuprojetotcc.autenticacao_diplomas.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
/*
@Service
public class CertificadoImplement {
    @Autowired
    private CertificadoRepository certificadoRepository;
    @Autowired
    private EstudanteRepository estudanteRepository;
    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private InstituicaoRepository instituicaoRepository;

    @Autowired
    private UserRepository userRepository;
    public Certificado salvar(CertificadoDTO dto) {
        Estudante estudante = estudanteRepository.findById(dto.getEstudanteId())
                .orElseThrow(() -> new RuntimeException("Estudante não encontrado"));

        Curso curso = cursoRepository.findById(dto.getCursoId())
                .orElseThrow(() -> new RuntimeException("Curso não encontrado"));

        Instituicao
                instituicao = instituicaoRepository.findById(dto.getInstituicaoId())
                .orElseThrow(() -> new RuntimeException("Instituição não encontrada"));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Certificado certificado = new Certificado(dto, estudante, curso, instituicao, user);

        return certificadoRepository.save(certificado);
    }


    public List<Certificado> VerPorAluno(String alunoNome){
        return certificadoRepository.findAll();
    }
    public List<Certificado> VerTodos(){
        return certificadoRepository.findAll();
    }

    public Certificado Tirar (Long id){
        Optional<Certificado> certificadoOptional = certificadoRepository.findById(id);
        if (certificadoOptional.isPresent()){
            Certificado certificado = certificadoOptional.get();
            certificado .setDataRevogar(LocalDateTime.now());
            certificado.setStatus("Inativo");
            return certificadoRepository.save(certificado);
        }
        return null;
    }

    public Certificado Emitir(Long id){
        Optional<Certificado> certificadoOptional = certificadoRepository.findById(id);
        if (certificadoOptional.isPresent()){
            Certificado certificado = certificadoOptional.get();
            certificado.setDataEmissao(LocalDateTime.now());
            certificado.setStatus("ativo");
            return certificadoRepository.save(certificado);
        }
        return null;
    }

}
*/