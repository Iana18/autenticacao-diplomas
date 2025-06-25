package com.meuprojetotcc.autenticacao_diplomas.service;

import com.meuprojetotcc.autenticacao_diplomas.model.certificado.Certificado;
import com.meuprojetotcc.autenticacao_diplomas.model.user.User;
import com.meuprojetotcc.autenticacao_diplomas.model.verificacao.Verificacao;
import com.meuprojetotcc.autenticacao_diplomas.model.verificacao.VerificacaoDto;
import com.meuprojetotcc.autenticacao_diplomas.repository.CertificadoRepository;
import com.meuprojetotcc.autenticacao_diplomas.repository.UserRepository;
import com.meuprojetotcc.autenticacao_diplomas.repository.VerificacaoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VerficacaoService{

    private final VerificacaoRepository verificacaoRepository;
    private final CertificadoRepository certificadoRepository;
    private final UserRepository userRepository;

    public VerficacaoService(VerificacaoRepository repo,
                              CertificadoRepository certificadoRepo,
                              UserRepository userRepo) {
        this.verificacaoRepository = repo;
        this.certificadoRepository = certificadoRepo;
        this.userRepository = userRepo;
    }

    public Verificacao registrarVerificacao(VerificacaoDto dto) {
        Optional<Certificado> certificado = certificadoRepository.findById(dto.getCertificadoId());
        Optional<User> verificador = userRepository.findById(dto.getVerificadorId());

        if (certificado.isEmpty() || verificador.isEmpty()) {
            throw new RuntimeException("Certificado ou verificador inválido.");
        }

        Verificacao v = new Verificacao();
        v.setCertificado(certificado.get());
        v.setVerificador(verificador.get());
        v.setDataVerificacao(LocalDateTime.now());

        return verificacaoRepository.save(v);
    }

    public List<Verificacao> buscarPorCertificado(Long certificadoId) {
        return verificacaoRepository.findByCertificadoId(certificadoId); // ✅ AGORA ESTÁ CORRETO
    }

    public List<Verificacao> listarTodas() {
        return verificacaoRepository.findAll();
    }
}
