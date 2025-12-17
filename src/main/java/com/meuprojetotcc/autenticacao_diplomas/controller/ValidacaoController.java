/*package com.meuprojetotcc.autenticacao_diplomas.controller;

import com.meuprojetotcc.autenticacao_diplomas.model.HistoricoAdulteracao;
import com.meuprojetotcc.autenticacao_diplomas.model.diploma.Diploma;
import com.meuprojetotcc.autenticacao_diplomas.repository.DiplomaRepository;
import com.meuprojetotcc.autenticacao_diplomas.service.HistoricoAdulteracaoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/validacao")
@CrossOrigin(origins = "*")
public class ValidacaoController {

    private final DiplomaRepository diplomaRepository;
    private final HistoricoAdulteracaoService historicoService;

    public ValidacaoController(DiplomaRepository diplomaRepository,
                               HistoricoAdulteracaoService historicoService) {
        this.diplomaRepository = diplomaRepository;
        this.historicoService = historicoService;
    }

    @GetMapping("/diploma")
    public ValidacaoResponse validarDiploma(@RequestParam String numeroDiploma) {

        Diploma diploma = diplomaRepository.findByNumeroDiploma(numeroDiploma)
                .orElseThrow(() -> new RuntimeException("Diploma não encontrado"));

        boolean valido = historicoService.isDiplomaValido(diploma);
        List<HistoricoAdulteracao> alteracoes = historicoService.getAlteracoes(diploma);

        return new ValidacaoResponse(valido, diploma, alteracoes);
    }

    // DTO para retorno
    public static class ValidacaoResponse {
        private boolean valido;
        private Diploma dados;
        private List<AlteracaoDTO> alteracoes;

        public ValidacaoResponse(boolean valido, Diploma dados, List<HistoricoAdulteracao> alteracoes) {
            this.valido = valido;
            this.dados = dados;
            this.alteracoes = alteracoes.stream()
                    .map(a -> new AlteracaoDTO(a.getCampoAlterado(), a.getValorAntigo(), a.getValorNovo(), a.getDataAlteracao()))
                    .collect(Collectors.toList());
        }

        // getters
        public boolean isValido() { return valido; }
        public Diploma getDados() { return dados; }
        public List<AlteracaoDTO> getAlteracoes() { return alteracoes; }
    }

    public static class AlteracaoDTO {
        private String campo;
        private String valorAntigo;
        private String valorNovo;
        private String dataAlteracao;

        public AlteracaoDTO(String campo, String valorAntigo, String valorNovo, java.time.LocalDateTime data) {
            this.campo = campo;
            this.valorAntigo = valorAntigo;
            this.valorNovo = valorNovo;
            this.dataAlteracao = data.toString();
        }

        // getters
        public String getCampo() { return campo; }
        public String getValorAntigo() { return valorAntigo; }
        public String getValorNovo() { return valorNovo; }
        public String getDataAlteracao() { return dataAlteracao; }
    }
}
*/