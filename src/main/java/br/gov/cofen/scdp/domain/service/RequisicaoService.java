package br.gov.cofen.scdp.domain.service;

import br.gov.cofen.scdp.api.dto.RequisicaoDetalheDTO;
import br.gov.cofen.scdp.api.dto.RequisicaoDiariaDTO;
import br.gov.cofen.scdp.api.dto.RequisicaoPassagemDTO;
import br.gov.cofen.scdp.api.dto.RequisicaoResumoDTO;
import br.gov.cofen.scdp.domain.entity.RequisicaoDiariaEntity;
import br.gov.cofen.scdp.domain.entity.RequisicaoEntity;
import br.gov.cofen.scdp.domain.entity.RequisicaoPassagemEntity;
import br.gov.cofen.scdp.domain.entity.UsuarioEntity;
import br.gov.cofen.scdp.domain.repository.RequisicaoDiariaRepository;
import br.gov.cofen.scdp.domain.repository.RequisicaoPassagemRepository;
import br.gov.cofen.scdp.domain.repository.RequisicaoRepository;
import br.gov.cofen.scdp.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RequisicaoService {

    private final UsuarioRepository usuarioRepository;
    private final RequisicaoRepository requisicaoRepository;
    private final RequisicaoDiariaRepository diariaRepository;
    private final RequisicaoPassagemRepository passagemRepository;

    public List<RequisicaoResumoDTO> listarRequisicoesPorCpf(String cpf) {

        UsuarioEntity usuario = usuarioRepository.findByCpf(cpf)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        List<RequisicaoEntity> requisicoes =
                requisicaoRepository.findByUsuarioId(usuario.getId());

        return requisicoes.stream()
                .filter(req -> req.getStatus() != 3)
                .map(req -> {

                    Optional<RequisicaoDiariaEntity> diaria =
                            diariaRepository.findByRequisicaoId(req.getId());

                    Optional<RequisicaoPassagemEntity> passagem =
                            passagemRepository.findByRequisicaoId(req.getId());

                    if (diaria.isPresent()) {

                        long situacaoDiaria = diaria.get().getSituacao().getId();

                        // excluir se situacao 7, 8 ou 15
                        if (situacaoDiaria == 7 || situacaoDiaria == 8 || situacaoDiaria == 15) {
                            return null; // exclui
                        }

                        return RequisicaoResumoDTO.builder()
                                .chave(diaria.get().getChave())
                                .tipo("DIARIA")
                                .status(diaria.get().getSituacao().getDescricao()) // status REAL da diária
                                .build();

                    } else if (passagem.isPresent()) {

                        long situacaoPassagem = passagem.get().getSituacao().getId();

                        // excluir se situacao 6 ou 7
                        if (situacaoPassagem == 6 || situacaoPassagem == 7) {
                            return null; // exclui
                        }

                        return RequisicaoResumoDTO.builder()
                                .chave(passagem.get().getChave())
                                .tipo("PASSAGEM")
                                .status(passagem.get().getSituacao().getDescricao()) // status REAL da passagem
                                .build();
                    }

                    // Nenhuma diária ou passagem encontrada: descarta
                    return null;

                })
                .filter(Objects::nonNull) // remove excluídas
                .toList();
    }

    public RequisicaoDetalheDTO detalhes(Long id) {

        RequisicaoEntity req = requisicaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Requisição não encontrada"));

        Optional<RequisicaoDiariaEntity> diaria = diariaRepository.findByRequisicaoId(id);
        Optional<RequisicaoPassagemEntity> passagem = passagemRepository.findByRequisicaoId(id);

        String tipo;
        Object dados;
        String chaveCompleta;

        if (diaria.isPresent()) {
            tipo = "DIARIA";
            dados = mapDiaria(diaria.get());
            chaveCompleta = diaria.get().getChave();

        } else if (passagem.isPresent()) {
            tipo = "PASSAGEM";
            dados = mapPassagem(passagem.get());
            chaveCompleta = passagem.get().getChave();

        } else {
            throw new RuntimeException("Requisição sem tipo associado");
        }

        return RequisicaoDetalheDTO.builder()
                .id(req.getId())
                .tipo(tipo)
                .chaveCompleta(chaveCompleta)
                .dados(dados)
                .build();
    }

    private RequisicaoDiariaDTO mapDiaria(RequisicaoDiariaEntity e) {
        return RequisicaoDiariaDTO.builder()
                .requisicao(e.getRequisicao().getId().toString())
                .situacao(e.getSituacao().getDescricao())
                .chave(e.getChave())
                .dataInicio(e.getDataInicio())
                .dataFim(e.getDataFim())
                .descricao(e.getDescricao())
                .build();
    }

    private RequisicaoPassagemDTO mapPassagem(RequisicaoPassagemEntity e) {
        return RequisicaoPassagemDTO.builder()
                .requisicao(e.getRequisicao().getId().toString())
                .situacao(e.getSituacao().getDescricao())
                .chave(e.getChave())
                .dataViagem(e.getDataViagem())
                .motivo(e.getMotivo())
                .build();
    }


}
